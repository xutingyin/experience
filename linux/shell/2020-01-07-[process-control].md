# 流程控制
正如名称所言，Shell 中的流程控制也如同多数的编程语言一样，对程序每一步执行步骤进行控制。

## 常用的流程控制语句：
### 一、if else
1、可以省略else的情况：
 
语法格式：
 
    if condition
    then
        command1 
        command2
        ...
        commandN 
    fi

2、if-else

语法格式：    

    if condition
    then
        command1 
        command2
        ...
        commandN
    else
        command
    fi
    
3、if elif else
  
语法格式：
     
    if condition1
    then
        command1
    elif condition2 
    then 
        command2
    else
        commandN
    fi
 注：
 
   ①then 要和if 配套使用，有一个if,就必须要有一个then
    
   ②fi要作为if的结束标识，和第一个if构成一个完整的结构。
   
举个栗子：
    
    #! /bin/bash
    num1=10
    num2=20
    
    if [ $num1 == $num2 ]
    then
        echo "$num1=$num2"
    elif [ $num1 -gt $num2 ]
    then
        echo "$num1 > $num2"
    elif [ $num1 -lt $num2 ]
    then
        echo "$num1 < $num2"
    else
         echo "No condition matched."
    fi
结果输出：
    
    10 < 20   
   
 ### 二、for 循环
 语法格式：
 
    for var in item1 item2 ... itemN
    do
        command1
        command2
        ...
        commandN
    done
注：

    ① in 后面是一个集合，至少有一个元素。    
    ② do ... done 和 for 配套使用。 

举个例子：
    
    for item in 1 2 3 4 5
    do
       echo "${item}"
    done
结果输出：

       1
       2
       3
       4
       5

### 三、while 循环
while循环用于不断的执行一系列命令，也可以从输入文件中读取数据；命令通常为测试条件。

语法格式：
        
        while condition
        do
            command
        done

举个栗子：
        
        int=1
        while(($int < 6))
        do
            echo "${int}"
            let "int++"     # 这里需要使用 let 命令进行自加运算，不然就出现了死循环
        done
  
结果输出：
    
    1
    2
    3
    4
    5
注：
  
  ①do...done 需要和while 配套使用
  ②循环要给终止条件，防止出现死循环

while读取输入

举个栗子：
       
       echo '"keyup [Ctrl+D] to exit."'
       echo 'Please input your favoriate website:'
       while read website
       do
           echo "yeah! ${website} is a good website!"
       done
### 四、无限循环
有时候我们需要使用无限循环的场景

常用的使用方式：
        
        while :
        do
            command
        done
        
        或者：
        
        while true
        do
            command
        done
        
        或者：
        
        for (( ; ; ))
        
###五、case 分支语句
类似其他的编程语言一样，当if-else 这样的分支很多的时候，我们可以选用case 来替代。

基本语法：
    
    case 值 in
    模式1)
        command1
        command2
        ...
        commandN
        ;;
    模式2）
        command1
        command2
        ...
        commandN
        ;;
    esac
    注：case esac 必须配套使用

举个栗子：

    echo "please input number between 1 and 4"
    read num
    case ${num} in
      1) echo 'you choose 1' ;;
      2) echo 'you choose 2' ;;
      3) echo 'you choose 3' ;;
      4) echo 'you choose 4' ;;
      *) echo 'you not input number between 1 and 4.' ;;
    esac

###六、跳出循环
    
    break : 终止整个循环
    continue ： 跳出本次循环
