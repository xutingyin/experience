# java 开发规范【基于阿里巴巴Java开发规范】

## 一、编程规约
### 1.1.命名风格
1、代码中的命名均不能以下划线和美元符号开始，也不能以此结尾。

反例：-name/$name/name-/name$

2、代码中的命名严禁使用拼音和英文混合的方式，更不允许直接使用中文的方式。

3、类名使用UpperCamelCase风格，但下列情形除外：DO/PO/DTO/VO/BO等

4、方法名、参数名、变量名均使用lowerCamelCase风格

如：getHttpMessage()/inputName/userName

5、常量名统一使用大写字母，单词之间使用下划线连接，力求表达清楚含义，不要嫌名字长

如：CACHE_EXPIRED_TIME

6、抽象类命名使用 Abstract 或 Base 开头； 异常类命名使用 Exception 结尾； 测试类
命名以它要测试的类的名称开始，以 Test 结尾。

7、类型与中括号紧挨相连来表示数组。

8、POJO 类中布尔类型的变量，都不要加 is 前缀，否则部分框架解析会引起序列化错误。

9、包名统一使用小写，点分隔符之间有且仅有一个自然语言的英语单词。并且包名统一使用单数形式。如果类名有复数形式可以使用复数。

10、杜绝安全不规范的缩写，避免望文生义。不要长，将每个类名、属性、方法完整表达清楚。

11、接口中的方法和属性不要添加任何修饰符（public也不要加），并加上有效的javadoc 注释。

12、枚举类名建议带上Enum后缀，枚举成员名称需要全大写，单词间用下划线隔开。

13、各层命名规范：
    A)Service/DAO层

	 1）获取单个对象的方法用get作为前缀
	
	 2）获取多个对象的方法用list作为前缀，复数形式结尾如：listObjects。
	
	 3）获取统计值的方法用count()
	
	 4)插入方法使用save/insert 作为前缀
	
	 5）删除方法用remove/delete作为前缀
	
	 6）修改方法使用update作为前缀
	
	B)领域命名规范
	
	 1）数据对象：xxxDO,xxx表示数据表名
	
	 2）数据传输对象，xxxDTO，xxx为业务相关的名称
	
	 3）展示对象：xxxVO，xxx一般为网页名称
	
	 4）POJO为DO/DTO/BO/VO的统称，禁止使用xxxPOJO命名 

### 1.2.常量定义

1、不允许使用任何魔法值（未经预先定义的常量）直接在代码中使用

如：String key = "Id#taoabo"+tableId;

2、在使用long或者Long 类型时，数值后要使用大写L,不能使用小写，容易和数字1混淆

3、不要使用一个常量类维护所有常量， 要按常量功能进行归类，分开维护。

说明： 大而全的常量类， 杂乱无章， 使用查找功能才能定位到修改的常量，不利于理解和维护。

4、如果变量值仅在一个固定范围内变化用 enum 类型来定义。

说明： 如果存在名称之外的延伸属性应使用 enum 类型，下面正例中的数字就是延伸信息，表示一年中的
第几个季节。

正例：
```
     public enum SeasonEnum {
    	   SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4);
    	    private int seq;
       SeasonEnum(int seq) {
    	   this.seq = seq;
    	}
    	public int getSeq() {
    	  return seq;
    	   }
    	}
```

### 1.3.代码格式
1、如果是大括号内为空，则简洁地写成{}即可，大括号中间无需换行和空格；如果是非
空代码块则：

	1） 左大括号前不换行。
	
	2） 左大括号后换行。
	
	3） 右大括号前换行。
	
	4） 右大括号后还有 else 等代码则不换行；表示终止的右大括号后必须换行。

2、左小括号和字符之间不出现空格；同样，右小括号和字符之间也不出现空格；而左
大括号前需要空格。

3、if/for/while/switch/do 等保留字与括号之间都必须加空格。

4、任何二目、 三目运算符的左右两边都需要加一个空格。

5、采用 4 个空格缩进，禁止使用 tab 字符。

```
正例： （涉及 1-5 点）
public static void main(String[] args) {
// 缩进 4 个空格
String say = "hello";
// 运算符的左右必须有一个空格
int flag = 0;
// 关键词 if 与括号之间必须有一个空格，括号内的 f 与左括号，0 与右括号不需要空格
if (flag == 0) {
System.out.println(say);
}
// 左大括号前加空格且不换行；左大括号后换行
if (flag == 1) {
System.out.println("world");
// 右大括号前换行，右大括号后有 else，不用换行
} else {
System.out.println("ok");
// 在右大括号后直接结束，则必须换行
}
}
```

6、注释的双斜线与注释内容之间有且仅有一个空格。

如：

// 这是示例注释，请注意在双斜线之后有一个空格

String param = new String();

7、方法参数在定义和传入时，多个参数逗号后边必须加空格。

8、IDE 的 text file encoding 设置为 UTF-8; IDE 中文件的换行符使用 Unix 格式，不
要使用 Windows 格式。
 
9、单个方法的总行数不超过 80 行。

10、不同逻辑、不同语义、不同业务的代码之间插入一个空行分隔开来以提升可读性。
 
### 1.4.OOP规范
1、避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析
成本，直接用类名来访问即可。

2、所有的覆写方法，必须加@Override 注解。

3、方法参数超过3个就封装为对象

4、不要使用过时的类和方法

5、外部正在调用或者二方库依赖的接口，不允许修改方法签名，避免对接口调用方产
生影响。接口过时必须加@Deprecated 注解，并清晰地说明采用的新接口或者新服务是什
么。

6、Object 的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用
equals。

如："test".equals(object); 而不是 object.equals("test");

7、所有整型包装类对象之间值的比较， 全部使用 equals 方法比较。

说明： 对于 Integer var = ? 在-128 至 127 范围内的赋值， Integer 对象是在 IntegerCache.cache 产
生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数
据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。

8、浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型统一改为BigDecimal 类型进行
equals 来判断。

9、定义数据对象 DO 类时，属性类型要与数据库字段类型相匹配。

如：数据库字段的 bigint 必须与类属性的 Long 类型相对应。如果 id 字段定义类型 bigint unsigned，实际类对象属性为 Integer，随着 id 越来越大，超过 Integer 的表示范围而溢出成为负数。

10、 为了防止精度损失， 禁止使用构造方法 BigDecimal(double)的方式把 double 值转
化为 BigDecimal 对象。

优先推荐：入参为 String 的构造方法，或使用 BigDecimal 的 valueOf 方法，此方法内部其实执行了
Double 的 toString，而 Double 的 toString 按 double 的实际能表达的精度对尾数进行了截断。

如： 

	BigDecimal recommend1 = new BigDecimal("0.1");
	BigDecimal recommend2 = BigDecimal.valueOf(0.1);

11、关于基本数据类型与包装数据类型的使用标准如下：

1） 【强制】所有的 POJO 类属性必须使用包装数据类型。

2） 【强制】RPC 方法的返回值和参数必须使用包装数据类型。

3） 【推荐】所有的局部变量使用基本数据类型

12、定义 DO/DTO/VO 等 POJO 类时，不要设定任何属性默认值。

13、序列化类新增属性时，请不要修改 serialVersionUID 字段，避免反序列失败；如果
完全不兼容升级，避免反序列化混乱，那么请修改 serialVersionUID 值。

14、构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在 init 方法中。

15、POJO 类必须写 toString 方法。使用 IDE 中的工具：source> generate toString
时，如果继承了另一个 POJO 类，注意在前面加一下 super.toString。

16、禁止在 POJO 类中，同时存在对应属性 xxx 的 isXxx()和 getXxx()方法。
说明：框架在调用属性 xxx 的提取方法时，并不能确定哪个方法一定是被优先调用到。
故：在设计是否存在的字段是，不要在前面加is，数据库中设置为含有is,POJO中不含，利用mybatis 的ResultMap 进行关联。

17、使用索引访问用 String 的 split 方法得到的数组时，需做最后一个分隔符后有无内
容的检查，否则会有抛 IndexOutOfBoundsException 的风险。

### 1.5.集合处理规范
1、关于 hashCode 和 equals 的处理，遵循如下规则：

1） 只要覆写 equals，就必须覆写 hashCode。

2） 因为 Set 存储的是不重复的对象，依据 hashCode 和 equals 进行判断，所以 Set 存储的对象必须覆 写这两个方法。

3） 如果自定义对象作为 Map 的键，那么必须覆写 hashCode 和 equals。

2、ArrayList 的 subList 结果不可强转成 ArrayList，否则会抛出 ClassCastException 异 常，即 java.util.RandomAccessSubList cannot be cast to java.util.ArrayList。

说明：subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList 而是 ArrayList 的一个视图，对
于 SubList 子列表的所有操作最终会反映到原列表上

3、使用 Map 的方法 keySet()/values()/entrySet()返回集合对象时，不可以对其进行添
加元素操作，否则会抛出 UnsupportedOperationException 异常。

4、Collections 类返回的对象，如：emptyList()/singletonList()等都是 immutable 
list，不可对其进行添加或者删除元素的操作。

5、在 subList 场景中，高度注意对原集合元素的增加或删除，均会导致子列表的遍
历、增加、删除产生 ConcurrentModificationException 异常。

6、使用集合转数组的方法，必须使用集合的 toArray(T[] array)，传入的是类型完全一
致、长度为 0 的空数组。

反例：直接使用 toArray 无参方法存在问题，此方法返回值只能是 Object[]类，若强转其它类型数组将出
现 ClassCastException 错误。

正例：

	List<String> list = new ArrayList<>(2);
	list.add("guan");
	list.add("bao");
	String[] array = list.toArray(new String[0]);

 说明：使用 toArray 带参方法，数组空间大小的 length： 
1） 等于 0，动态创建与 size 相同的数组，性能最好。

2） 大于 0 但小于 size，重新创建大小等于 size 的数组，增加 GC 负担。

3） 等于 size，在高并发情况下，数组创建完成之后，size 正在变大的情况下，负面影响与上相同。

4） 大于 size，空间浪费，且在 size 处插入 null 值，存在 NPE 隐患

7、在使用 Collection 接口任何实现类的 addAll()方法时，都要对输入的集合参数进行
NPE 判断。

说明：在 ArrayList#addAll 方法的第一行代码即 Object[] a = c.toArray(); 其中 c 为输入集合参数，如果
为 null，则直接抛出异常。

8、使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方
法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常。

9、泛型通配符<? extends T>来接收返回的数据，此写法的泛型集合不能使用 add 方 法，而<? super T>不能使用 get 方法，作为接口调用赋值时易出错。

10、在无泛型限制定义的集合赋值给泛型限制的集合时，在使用集合元素时，需要进行
instanceof 判断，避免抛出 ClassCastException 异常。

11、不要在 foreach 循环里进行元素的 remove/add 操作。remove 元素请使用
Iterator 方式，如果并发操作，需要对 Iterator 对象加锁。

12、集合初始化时，指定集合初始值大小。

说明：HashMap 使用 HashMap(int initialCapacity) 初始化。

正例：initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即 loader factor）默认
为 0.75，如果暂时无法确定初始值大小，请设置为 16（即默认值）。

反例：HashMap 需要放置 1024 个元素，由于没有设置容量初始大小，随着元素不断增加，容量 7 次被
迫扩大，resize 需要重建 hash 表，严重影响性能。

13、使用 entrySet 遍历 Map 类集合 KV，而不是 keySet 方式进行遍历。

说明：keySet 其实是遍历了 2 次，一次是转为 Iterator 对象，另一次是从 hashMap 中取出 key 所对应
的 value。而 entrySet 只是遍历了一次就把 key 和 value 都放到了 entry 中，效率更高。如果是 JDK8，
使用 Map.forEach 方法。

正例：values()返回的是 V 值集合，是一个 list 集合对象；keySet()返回的是 K 值集合，是一个 Set 集合
对象；entrySet()返回的是 K-V 值组合集合。	

### 1.6.并发处理
1、获取单例对象需要保证线程安全，其中的方法也要保证线程安全。
说明：资源驱动类、工具类、单例工厂类都需要注意。

2、创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。

3、线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。

说明：线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，解决资源不足的问
题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

4、线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这
样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。

说明：Executors 返回的线程池对象的弊端如下：
1） FixedThreadPool 和 SingleThreadPool：
允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。 

2） CachedThreadPool：
允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。

5、SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为
static，必须加锁，或者使用 DateUtils 工具类。
正例：注意线程安全，使用 DateUtils。亦推荐如下处理：

	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() { 
	   @Override 
	   protected DateFormat initialValue() { 
	   return new SimpleDateFormat("yyyy-MM-dd"); 
	} 
	};
 
说明：如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
DateTimeFormatter 代替 SimpleDateFormat，官方给出的解释：simple beautiful strong immutable 
thread-safe。

### 1.7 控制语句
1、在一个 switch 块内，每个 case 要么通过 continue/break/return 等来终止，要么
注释说明程序将继续执行到哪一个 case 为止；在一个 switch 块内，都必须包含一个
default 语句并且放在最后，即使它什么代码也没有。

说明：注意 break 是退出 switch 语句块，而 return 是退出方法体。

2、当 switch 括号内的变量类型为 String 并且此变量为外部参数时，必须先进行 null
  判断。

3、在 if/else/for/while/do 语句中必须使用大括号。

  说明：即使只有一行代码，避免采用单行的编码方式：if (condition) statements;
  
4、在高并发场景中，避免使用”等于”判断作为中断或退出的条件。

  说明：如果并发控制没有处理好，容易产生等值判断被“击穿”的情况，使用大于或小于的区间判断条件
  来代替 。
5、表达异常的分支时，少用 if-else 方式，这种方式可以改写成：

      if (condition) { 
       ... 
       return obj; 
      } 
      // 接着写 else 的业务逻辑代码;
       
  说明：如果非使用 if()...else if()...else...方式表达逻辑，避免后续代码维护困难，【强制】请勿超过 3 层。
  
  正例：超过 3 层的 if-else 的逻辑判断代码可以使用卫语句、策略模式、状态模式等来实现，其中卫语句
  即代码逻辑先考虑失败、异常、中断、退出等直接返回的情况，以方法多个出口的方式，解决代码中判断
  分支嵌套的问题，这是逆向思维的体现。
  示例如下：
      
      public void findBoyfriend(Man man) {
       if (man.isUgly()) {
       System.out.println("本姑娘是外貌协会的资深会员");
       return;
       }
       if (man.isPoor()) {
       System.out.println("贫贱夫妻百事哀");
       return;
       }
       if (man.isBadTemper()) {
       System.out.println("银河有多远，你就给我滚多远");
       return;
       }
      System.out.println("可以先交往一段时间看看");
      } 

## 五、编程规约
### 5.1.建表约束
1、表达是否概念的字段，使用is_xxx的方式命名，数据类型使用unsigned tinyint(1为是，0为否)

2、表名、字段名使用小写字母或者数字，但是禁止使用数字开头，禁止两个下划线之间只出现数字

正例：user_name,level3_name

反例：UserName,level_3_name

3、表名不要使用复数

4、禁用mysql中的关键字(保留字)，如desc、asc、range、select、detete 等。

5、主键索引使用pk_字段名；唯一索引使用 uk_字段名；普通索引使用idx_字段名。

6、小数类型为 decimal，禁止使用 float 和 double。
说明： 在存储的时候， float 和 double 都存在精度损失的问题，很可能在比较值的时候，得到不正确的
结果。如果存储的数据范围超过 decimal 的范围，建议将数据拆成整数和小数并分开存储

7、如果存储的字符串长度几乎相等，使用 char 定长字符串类型。

8、varchar 是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长
度大于此值，定义字段类型为 text，独立出来一张表，用主键来对应，避免影响其它字段索
引效率。

9、表必备三字段： id, create_time, update_time。

说明：其中 id 必为主键，类型为 bigint unsigned、单表时自增、步长为 1。 create_time, update_time
的类型均为 datetime 类型。

10、库名与应用一致、表的命名最好是遵循“业务名称_表的作用” 。

### 5.2.索引约束
1、业务上具有唯一特性的字段，即使是多个字段的组合，也必须建成唯一索引。
说明：不要以为唯一索引影响了 insert 速度，这个速度损耗可以忽略，但提高查找速度是明显的；另外，
即使在应用层做了非常完善的校验控制，只要没有唯一索引，根据墨菲定律，必然有脏数据产生。

2、超过三个表禁止 join。需要 join 的字段，数据类型必须绝对一致；多表关联查询
时，保证被关联的字段需要有索引。
说明：即使双表 join 也要注意表索引、SQL 性能。

3、在 varchar 字段上建立索引时，必须指定索引长度，没必要对全字段建立索引，根据
实际文本区分度决定索引长度即可。

4、页面搜索严禁左模糊或者全模糊，如果需要请走搜索引擎来解决。
说明：索引文件具有 B-Tree 的最左前缀匹配特性，如果左边的值未确定，那么无法使用此索引。

5、建组合索引的时候，区分度最高的在最左边。

正例：如果 where a=? and b=? ，如果 a 列的几乎接近于唯一值，那么只需要单建 idx_a 索引即可。
说明：存在非等号和等号混合时，在建索引时，请把等号条件的列前置。如：where c>? and d=? 那么
即使 c 的区分度更高，也必须把 d 放在索引的最前列，即索引 idx_d_c。

### 5.3.语句约束
1、不要使用 count(列名)或 count(常量)来替代 count(*)，count(*)是 SQL92 定义的
标准统计行数的语法，跟数据库无关，跟 NULL 和非 NULL 无关。
说明：count(*)会统计值为 NULL 的行，而 count(列名)不会统计此列为 NULL 值的行。

2、count(distinct col) 计算该列除 NULL 之外的不重复行数，注意 count(distinct 
col1, col2) 如果其中一列全为 NULL，那么即使另一列有不同的值，也返回为 0。

3、当某一列的值全是 NULL 时，count(col)的返回结果为 0，但 sum(col)的返回结果
为 NULL，因此使用 sum()时需注意 NPE 问题。

4、使用 ISNULL()来判断是否为 NULL 值。
说明：NULL 与任何值的直接比较都为 NULL。 1） NULL<>NULL 的返回结果是 NULL，而不是 false。 2） NULL=NULL 的返回结果是 NULL，而不是 true。 3） NULL<>1 的返回结果是 NULL，而不是 true。

5、代码中写分页查询逻辑时，若 count 为 0 应直接返回，避免执行后面的分页语句。

6、不得使用外键与级联，一切外键概念必须在应用层解决。
	
说明：以学生和成绩的关系为例，学生表中的 student_id 是主键，那么成绩表中的 student_id 则为外
键。如果更新学生表中的 student_id，同时触发成绩表中的 student_id 更新，即为级联更新。外键与级
联更新适用于单机低并发，不适合分布式、高并发集群；级联更新是强阻塞，存在数据库更新风暴的风
险；外键影响数据库的插入速度。

7、禁止使用存储过程，存储过程难以调试和扩展，更没有移植性。

8、数据订正（特别是删除、修改记录操作）时，要先 select，避免出现误删除，确认无
误才能执行更新语句。

9、in 操作能避免则避免，若实在避免不了，需要仔细评估 in 后边的集合元素数量，控
制在 1000 个之内。

10、TRUNCATE TABLE 比 DELETE 速度快，且使用的系统和事务日志资源少，但
TRUNCATE 无事务且不触发 trigger，有可能造成事故，故不建议在开发代码中使用此语句。
说明：TRUNCATE TABLE 在功能上与不带 WHERE 子句的 DELETE 语句相同。

### 5.4.ORM约束
1、在表查询中，一律不要使用 * 作为查询的字段列表，需要哪些字段必须明确写明。

说明：

1）增加查询分析器解析成本。 

2）增减字段容易与 resultMap 配置不一致。

3）无用字段增加网络消耗，尤其是 text 类型的字段

2、POJO 类的布尔属性不能加 is，而数据库字段必须加 is_，要求在 resultMap 中进行
字段与属性之间的映射。

说明：参见定义 POJO 类以及数据库字段定义规定，在<resultMap>中增加映射，是必须的。 在
MyBatis Generator 生成的代码中，需要进行对应的修改。

3、不要用 resultClass 当返回参数，即使所有类属性名与数据库字段一一对应，也需要
定义；反过来，每一个表也必然有一个 POJO 类与之对应。

说明：配置映射关系，使字段与 DO 类解耦，方便维护。

4、sql.xml 配置参数使用：#{}，#param# 不要使用${} 此种方式容易出现 SQL 注入。

5、不允许直接拿 HashMap 与 Hashtable 作为查询结果集的输出。

6、 更新数据表记录时，必须同时更新记录对应的 gmt_modified 字段值为当前时间。

7、不要写一个大而全的数据更新接口。 传入为 POJO 类，不管是不是自己的目标更新
字段，都进行 update table set c1=value1,c2=value2,c3=value3; 这是不对的。执行 SQL
时，不要更新无改动的字段，一是易出错；二是效率低；三是增加 binlog 存储。

8、@Transactional 事务不要滥用。事务会影响数据库的 QPS，另外使用事务的地方
需要考虑各方面的回滚方案，包括缓存回滚、搜索引擎回滚、消息补偿、统计修正等。