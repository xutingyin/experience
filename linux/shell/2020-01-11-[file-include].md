# 文件包含
在 Shell 中，我们也可以像其他语言一样，对外部文件进行引用。

语法格式：

    . filename     #注意： . 和文件名中间有空格
    source filename
    
举个栗子：
      写了两个文件
      
  url.sh
  
    #! /bin/bash
    mysite="www.xutingyin.cn"
    
  email.txt
  
    email="sunshinexuty@163.com"
   
 
  引用的文件：
  
    #! /bin/bash
    . url.sh
    source email.txt
    echo "我的网址：${mysite_url}"
    echo "我的邮箱：${email}"

执行结果：

        我的网址：www.xutingyin.cn
        我的邮箱：sunshinexuty@163.com

        