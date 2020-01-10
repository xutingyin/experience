# 输入输出重定向
常用的重定向命令如下：

    command > file	将输出重定向到 file。
    command < file	将输入重定向到 file。
    command >> file	将输出以追加的方式重定向到 file。
    n > file	将文件描述符为 n 的文件重定向到 file。
    n >> file	将文件描述符为 n 的文件以追加的方式重定向到 file。
    n >& m	将输出文件 m 和 n 合并。
    n <& m	将输入文件 m 和 n 合并。
    << tag	将开始标记 tag 和结束标记 tag 之间的内容作为输入。
  
输出重定向：
    
举个栗子：
    
     echo "This is my ubuntu" > io-redirect.txt
     
     注意：>表示每次将内容输出到指定文件里，会先清空文件里的内容。如果想进行追加。使用 >>
     
执行的结果：
 
    > cat  io-redirect.txt
    > This is my ubuntu
    
    会在该shell 文件的同级目录生成一个io-redict.txt 文件.
    里面的内容即 "This is my ubuntu"
 
输入重定向：
    可以用于文件输入。
 
举个栗子：
    
    wc -l < io-redirect.txt > out.txt

执行结果：
    
    > cat out.txt
    >1 
    
    这里，我们统计io-redirect.txt 中的行数，然后再将结果写入到out.txt 中。
    
常用的标准输入输出数字：

        标准输入文件(stdin) ：stdin的文件描述符为0，Unix程序默认从stdin读取数据。
        标准输出文件(stdout)：stdout的文件描述符为1，Unix程序默认向stdout输出数据。
        标准错误文件(stderr)：stderr的文件描述符为2，Unix程序会向stderr流中写入错误信息。     
    