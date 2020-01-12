# Effective Java 
## 一、引言
### 1、Java 引用类型和基础类型。
引用类型包括：接口、类、数组

基本类型包括常用的四类8种基本类型：

    整  型：byte、short、int、short 
    浮点型：float、double
    逻辑型: boolean
    字符型：char

## 二、创建对象和销毁对象
1、考虑用静态工厂方法代替构造器

2、遇到多个构造器参数时要考虑用构造器

3、用私有构造器或者枚举类强化Singleton属性
 
  私有构造器方式Singleton:
  
    public class Singleton{
        private static final Singleton INSTANCE;
        private Singleton(){...}
        public static Singleton getInstance(){
            return INSTANCE;
        }
        ....
    }
   
  枚举Singleton[目前最推荐的单例方式]
  
       public enum Singleton{
           INSTANCE;
           ....
       }
   
4、通过私有构造器强化不可实例化的能力【？不是太理解】

5、避免创建不必要的对象。

    能够重复引用的对象就重复引用。
    1、必要频繁的new 对象。当此操作被放在一个大循环或者频繁调用的方法中的时候，会创建很多的对象实例。
    导致堆内存无限增长，垃圾回收器回收不过来。
    
    2、严禁在for 循环中 进行调用DB 的操作，这样会严重的影响数据库的连接数，导致连接数用完，其他业务不能正常的使用。    

6、消除过期的对象引用。
    
    来个栗子：
    public class Stack() {
        private Object[] elements;
        private int size = 0;
        private static final int  DEFAULT_INITIAL_CAPACITY = 16;
        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }
        
        public void push(Object e) {
            ensureCapacity(); // 校验是否需要进行扩容
            elemnts[size++] = e;
        }
        
        public Object pop(){
            if(size == 0){
                throw new EmptyStackException();
            }
            return elements[--size];// 此处存在没有使用的引用，JVM不会对它进行回收，需要我们手动将引用的内存释放
            elements[size] = null;  // 手动释放未引用的内存
        }
        
        public void ensureCapacity(){
            if(elements.length == size){
                elments =Arrays.copyOf(elements.size * 2 + 1);
            }
        }
    }
    


## 三、所有对象通用的方法

## 四、类和接口

## 五、泛型

## 六、枚举和注解

## 七、方法

## 八、通用程序设计

## 九、异常

## 十、并发

## 十一、序列化