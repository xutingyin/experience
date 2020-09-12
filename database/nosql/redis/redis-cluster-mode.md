# Redis常见的集群模式
## 主从复制-[实际生产中基本不用]
![](images/redis-master-slave.png)

### 环境搭建：
#### 下载 Redis实例到本地
```shell script
wget  http://download.redis.io/releases/redis-5.0.8.tar.gz
```
##### 解压
```shell script
tar -zxvf redis-5.0.8.tar.gz
```
#### 设置 Redis 环境变量
【这一步不设置也可以，但就需要到redis安装目录下去执行命令】
```shell script
  vi /etc/profile
```
增加如下内容：
```shell
    export REDIS_HOME=/software/redis-5.0.8
    export PATH=$PATH:$REDIS_HOME/bin
```
#### 创建文件夹
```shell script
    mkdir /usr/local/redis-master-slave
    cd /usr/local/redis-master-slave
    mkdir {16379,16380,16381}
```
#### 复制 redis.conf 到各个文件夹下
```shell script
   cp /software/redis-5.0.8/redis.conf /usr/local/redis-master-slave/16379/
   cp /software/redis-5.0.8/redis.conf /usr/local/redis-master-slave/16380/
   cp /software/redis-5.0.8/redis.conf /usr/local/redis-master-slave/16381/
```

#### 分别编辑[16379,16380,16381]目录下的redis.conf
在文件末尾追加如下内容
##### master - 16379
```shell script
    bind 127.0.0.1
    port 16379
```
##### slave1 - 16380
```shell script
   bind 127.0.0.1
   port 16380
   dbfilename dump.rdb
   logfile "server_log.txt"
   slaveof 127.0.0.1 16379
   # 关闭集群模式 - 默认是开启的，这样会和主从模式冲突
   cluster-enabled no
   # 关闭只读模式，这样，slave也可以进行数据写入，但是这样做毫无意义，因为slave中写入的数据不会同步到其它slave中去，如果，master写入相同key的数据，此slave中的数据也会被覆盖
   #slave-read-only no
```
##### slave2 - 16381
```shell script
   bind 127.0.0.1
      port 16381
      dbfilename dump.rdb
      logfile "server_log.txt"
      slaveof 127.0.0.1 16379
      # 关闭集群模式 - 默认是开启的，这样会和主从模式冲突
      cluster-enabled no
```

#### 分别启动三个节点
```shell script
 redis-server /usr/local/redis-master-slave/16379/redis.conf &  # & 为后台启动
 redis-server /usr/local/redis-master-slave/16380/redis.conf &   
 redis-server /usr/local/redis-master-slave/16381/redis.conf &   
```
#### 验证主从模式
```shell script
    redis-cli -h localhost -p 16379
    localhost:16379> info replication
    # Replication
    role:master        # 可以看到该节点为master 节点
    connected_slaves:2 # 有两个从节点
    slave0:ip=127.0.0.1,port=16381,state=online,offset=4181,lag=0
    slave1:ip=127.0.0.1,port=16380,state=online,offset=4181,lag=1
    master_replid:4dcde3712f99ee6cc62c8eb2c169c9e302b78449
    master_replid2:0000000000000000000000000000000000000000
    master_repl_offset:4181
    second_repl_offset:-1
    repl_backlog_active:1
    repl_backlog_size:1048576
    repl_backlog_first_byte_offset:1
    repl_backlog_histlen:4181
```

```shell script
    redis-cli -h localhost -p 16380
    localhost:16380> info replication
    # Replication
    role:slave
    master_host:127.0.0.1
    master_port:16379
    master_link_status:up
    ......
```
```shell script
    redis-cli -h localhost -p 16381
    localhost:16381> info replication
    # Replication
    role:slave
    master_host:127.0.0.1
    master_port:16379
    master_link_status:up
    ......
```
#### 添加数据，验证主从
master 负责写和读

slave 只负责读
```shell script
localhost:16379> set test  redis
OK

localhost:16379> get test
"redis"

localhost:16380> get test
"redis"

localhost:16381> get test
"redis"

```
slave 进行写操作
```shell script
localhost:16380> set name ss
(error) READONLY You can't write against a read only replica.

```
可以看到，master 可以进行读写操作，slave 只能进行读操作。
当然：
slave也可以进行数据写入[在redis.conf中配置 slave-read-only no]，但是这样做毫无意义，因为slave中写入的数据不会同步到其它slave中去，如果，master写入相同key的数据，此slave中的数据也会被覆盖。

注：主从模式存在一个严重的问题：一旦master挂了，此时就必须进行人工手动干预，将其它的slave升级为master.

## 哨兵模式
为了解决主从复制模式中必须人工干预，进行手动将ｓｌａｖｅ升级为ｍａｓｔｅｒ的问题，出现了哨兵模式。
原理：系统中有一个哨兵，时刻都在监听着ｍａｓｔｅｒ的健康状况，当ｍａｓｔｅｒ出现异常的时候，就自动的在ｓｌａｖｅ中进行选举，最终选出新的ｍａｓｔｅｒ．
而原来的ｍａｓｔｅｒ如果恢复了，就作为ｓｌａｖｅ在系统中运行。
###　环境搭建
在主从模式的基础上，已经搭建好了一主两从．我们再增加一个哨兵即可
##### 新建哨兵目录
```shell script
 mkdir  -p /usr/local/redis-master-slave/sentinel/16000/tmp
```
##### 复制sentinel.conf
```shell script
 cp /software/redis-5.0.8/sentinel.conf /usr/local/redis-master-slave/sentinel/16000/
```
##### 编辑sentinel.conf,配置哨兵模式
将文件中的所有内容注释掉，在文件末尾追加如下内容
```shell script
 #　关闭保护模式
 protected-mode no
 #　端口号
 port １600０
 # 后台运行
 daemonize yes
 # 解除挂载信息目录
 dir /usr/local/redis-master-slave/sentinel/16000/tmp
 # 设置 主名称 ip地址 端口号 参入选举的哨兵数
 sentinel monitor mymaster 127.0.0.1 16379 1
 # sentinel心跳检测主3秒内无响应，视为挂掉，开始切换其他从为主
 sentinel down-after-milliseconds mymaster 3000
 # 每次最多可以有1个从同步主。一个从同步结束，另一个从开始同步。
 sentinel parallel-syncs mymaster 1
 # 主从切换超时时间
 sentinel failover-timeout mymaster 18000
```
##### 启动哨兵模式
```shell script
 redis-sentinel  /usr/local/redis-master-slave/sentinel/16000/sentinel.conf
```

##### 校验哨兵服务是否启动成功
```shell script
 [root@VM_0_17_centos 16000]# ps -ef |grep redis
 root      2200     1  0 14:35 ?        00:00:00 redis-sentinel *:16000 [sentinel]
 ......
```
##### 登录服务，检查哨兵状态
```shell script
[root@VM_0_17_centos 16000]# redis-cli -p 16000
127.0.0.1:16000> info sentinel
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=mymaster,status=ok,address=127.0.0.1:16379,slaves=2,sentinels=1
```
``` shell script
master0:name=mymaster,status=ok,address=127.0.0.1:16379,slaves=2,sentinels=1
从这里的信息我们可以得出，有一个哨兵［sentinels=1］，当前主服务正常［status=ok］，地址为127.0.0.1:16379，有两个从服务［slaves=2］
```
#### 验证master挂掉，哨兵自动选主
``` shell script
redis-cli -p 16379 shutdowｎ
[root@VM_0_17_centos 16000]# redis-cli -p 16000
127.0.0.1:16000> info sentinel
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=mymaster,status=ok,address=127.0.0.1:16380,slaves=2,sentinels=1
＃　我们现在可以看到，原来的slave1【16380】现在被选举为新的master
```
#### 服务恢复后，作为slave
```shell script
redis-server usr/local/redis-master-slave/16379/redis.conf &
[root@VM_0_17_centos 16000]# redis-cli -p 16379
127.0.0.1:16379> info replication
# Replication
role:slave  # 原来是作为master的，恢复了之后作为ｓlave
master_host:127.0.0.1
master_port:16380
master_link_status:up
......
```






















## 集群模式