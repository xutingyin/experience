# Java 程序员常犯的编码错误

**1、字符串对象的错误比较**
       
        String a = "ccc";
        String b = "ccc";
        System.out.println(a == b);      //true
        System.out.println(a.equals(b)); //true
    
        String c = new String("hello");
        String d = new String("hello");
        System.out.println(c == d );       //false
        System.out.println(c.equals(d));   //true  正确的比较方式

这里涉及Java 中两种内存分配的问题：

栈内存和堆内存：
当一段代码被定义一个变量时，就在栈内存中分配了内存。
堆内存中存放的是new 创建的对象和数组，每new 一次，就重新创建一个对象。
        
**2、在for 循环中删除 集合中的元素**