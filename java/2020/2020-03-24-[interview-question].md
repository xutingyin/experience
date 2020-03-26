# 每天一道面试题
## Java 部分
### java基础
#### 为什么HashMap的加载因子(DEFAULT_LOAD_FACTOR)默认为0.75?
    回答这个问题，个人觉得应该首先了解一下，什么是加载因子，它在HashMap 中的作用是什么？
    DEFAULT_LOAD_FACTOR 是代表哈希表中数据填满的程度的一个值；有如下规律：
    DEFAULT_LOAD_FACTOR 越大，空间利用率就越高，但是，hash冲突的概率就会越大，反之，hash冲突变小，但空间没有得到充分使用。
    
    那DEFAULT_LOAD_FACTOR 这个值到底应该取多少合适呢？我们查看HashMap 的源码可知，默认值为0.75(在0.5-1之间取的一个折中方案)
    参考：https://www.jianshu.com/p/64f6de3ffcc1
    
#### 为什么String是final的？
    效率和安全两个方面
    参考：https://www.cnblogs.com/lixin-link/p/11085029.html
    
###  volatile作用?
    1、保证可见性（主内存和工作内存）--一个变量如果被volatile修饰了，则Java可以确保所有线程看到这个变量的值是一致的；如果某个线程对这个变量进行了修改，那么其他线程可以立马看到这个更新值。
    2、防止指令重排序

### java多线程

### JVM

## Spring 部分


## Mybatis


## 数据库


## 设计模式


## 微服务
