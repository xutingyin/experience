# Shell 脚本
1、第一个Shell 脚本

    #! /bin/bash/
    echo "hello shell..."
    
虽然只有两行简单的代码，但涉及到的东西还是蛮多的，听我慢慢道来：

① #! 的作用是告诉操作系统，后面指定的程序即是用来解释此脚本文件的Shell程序。

当然，你可能要问了，像这样的解释Shell的程序业界有哪些呢?这就引出了我们的第二个关注点：

② /bin/bash/

业界中常见的Shell 解释器有如下：

Bourne Shell（/usr/bin/sh或/bin/sh）

Bourne Again Shell（/bin/bash）

C Shell（/usr/bin/csh）

K Shell（/usr/bin/ksh）

Shell for Root（/sbin/sh）

其中Bourne Again Shell 由于易用和免费，作为了很多Linux 发行版本内置的Shell 脚本解释器。所以，你知道为什么我们每个Shell脚本文件的开头，总是首先定义
\#!/bin/bash  这样命令了吧。

③ echo "hello shell..." 

echo 为linux 中比较常用的文本输出命令， echo 后面直接跟需要输出的内容，即可输出。当让，echo 还有更复杂的使用技巧，比如换行 -n,指定颜色输出等。