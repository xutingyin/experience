 #SQL 经典练习

 ##MYSQL 版本 ：8.0.12
 ##数据准备 
 
    DROP TABLE IF EXISTS `dept`;
    CREATE TABLE `dept`  (
      `DEPTNO` int(11) NOT NULL COMMENT '部门编号',
      `DNAME` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
      `LOC` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门地址',
      PRIMARY KEY (`DEPTNO`) USING BTREE,
      INDEX `DEPTNO`(`DEPTNO`) USING BTREE,
      INDEX `DEPTNO_2`(`DEPTNO`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Records of dept
    -- ----------------------------
    INSERT INTO `dept` VALUES (10, 'ACCOUNTING', 'NEW YORK');
    INSERT INTO `dept` VALUES (20, 'RESEARCH', 'DALLAS');
    INSERT INTO `dept` VALUES (30, 'SALES', 'CHICAGO');
    INSERT INTO `dept` VALUES (40, 'OPERATIONS', 'BOSTON');
    
    -- ----------------------------
    -- Table structure for emp
    -- ----------------------------
    DROP TABLE IF EXISTS `emp`;
    CREATE TABLE `emp`  (
      `EMPNO` int(11) NOT NULL COMMENT '员工编号',
      `ENAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
      `JOB` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工工作',
      `MGR` int(11) NULL DEFAULT NULL COMMENT '员工直属领导编号',
      `HIREDATE` date NULL DEFAULT NULL COMMENT '入职日期',
      `SAL` double NULL DEFAULT NULL COMMENT '工资',
      `COMM` double NULL DEFAULT NULL COMMENT '奖金',
      `DEPTNO` int(11) NULL DEFAULT NULL COMMENT '部门编号，对应DEP表的DEPNO',
      PRIMARY KEY (`EMPNO`) USING BTREE,
      INDEX `DEPTNO`(`DEPTNO`) USING BTREE,
      CONSTRAINT `emp_ibfk_1` FOREIGN KEY (`DEPTNO`) REFERENCES `dept` (`deptno`) ON DELETE RESTRICT ON UPDATE RESTRICT
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Records of emp
    -- ----------------------------
    INSERT INTO `emp` VALUES (7369, 'SMITH', 'CLERK', 7902, '1980-12-17', 800, NULL, 20);
    INSERT INTO `emp` VALUES (7499, 'ALLEN', 'SALESMAN', 7698, '1981-02-20', 1600, 300, 30);
    INSERT INTO `emp` VALUES (7521, 'WARD', 'SALESMAN', 7698, '1981-02-22', 1250, 500, 30);
    INSERT INTO `emp` VALUES (7566, 'JONES', 'MANAGER', 7839, '1981-04-02', 2975, NULL, 20);
    INSERT INTO `emp` VALUES (7654, 'MARTIN', 'SALESMAN', 7698, '1981-09-28', 1250, 1400, 30);
    INSERT INTO `emp` VALUES (7698, 'BLAKE', 'MANAGER', 7839, '1981-05-01', 2850, NULL, 30);
    INSERT INTO `emp` VALUES (7782, 'CLARK', 'MANAGER', 7839, '1981-06-09', 2450, NULL, 10);
    INSERT INTO `emp` VALUES (7788, 'SCOTT', 'ANALYST', 7566, '1987-07-03', 3000, NULL, 20);
    INSERT INTO `emp` VALUES (7839, 'KING', 'PRESIDENT', NULL, '1981-11-17', 5000, NULL, 10);
    INSERT INTO `emp` VALUES (7844, 'TURNER', 'SALESMAN', 7698, '1981-09-08', 1500, 0, 30);
    INSERT INTO `emp` VALUES (7876, 'ADAMS', 'CLERK', 7788, '1987-07-13', 1100, NULL, 20);
    INSERT INTO `emp` VALUES (7900, 'JAMES', 'CLERK', 7698, '1981-12-03', 950, NULL, 30);
    INSERT INTO `emp` VALUES (7902, 'FORD', 'ANALYST', 7566, '1981-12-03', 3000, NULL, 20);
    INSERT INTO `emp` VALUES (7934, 'MILLER', 'CLERK', 7782, '1981-01-23', 1300, NULL, 10);
    
    -- ----------------------------
    -- Table structure for salgrade
    -- ----------------------------
    DROP TABLE IF EXISTS `salgrade`;
    CREATE TABLE `salgrade`  (
      `GRADE` int(11) NULL DEFAULT NULL COMMENT '等级',
      `LOSAL` double NULL DEFAULT NULL COMMENT '最低工资',
      `HISAL` double NULL DEFAULT NULL COMMENT '最高工资'
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Records of salgrade
    -- ----------------------------
    INSERT INTO `salgrade` VALUES (1, 700, 1200);
    INSERT INTO `salgrade` VALUES (2, 1201, 1400);
    INSERT INTO `salgrade` VALUES (3, 1401, 2000);
    INSERT INTO `salgrade` VALUES (4, 2001, 3000);
    INSERT INTO `salgrade` VALUES (5, 3001, 9999);
    
     

     
## SQL 练习
参考：https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html 



-- 15、找出姓名中包含A的员工信息。
-- 庖丁解牛：LIKE 的的使用 或者 LOCATE()
	
	SELECT * from emp where ENAME LIKE '%A%';
	SELECT * from emp where LOCATE('A',ENAME) > 0;

-- 14、计算员工的日薪(按30天)。
-- 庖丁解牛 : 查询的时候支持四则运算的操作

	SELECT EMPNO, SAL/30 as '日薪' from emp;

-- 这小数太多了，保留两位,这里拓展集中mysql 中保留几位小数的方法， 这里保留2位
-- 方法1；TRUNCATE(X,D) 返回数字X，截断到D小数位。 如果D为0，结果没有小数点或小数部分。 D是负数，导致值X的小数点左边的D数字变为零。

-- 方法2：round(x,d) 用于数据的四舍五入,round(x)  ,其实就是round(x,0),也就是默认d为0；
-- 方法3：FORMAT（X,D）：强制保留D位小数，整数部分超过三位的时候以逗号分割，并且返回的结果是string类型的
-- 方法4：convert（value，type）;类型转换，相当于截取

	SELECT EMPNO, TRUNCATE(SAL/30,2) as '日薪1', round(SAL/30,2) as '日薪2',FORMAT(SAL/30,2) as '日薪3',convert(SAL/30,DECIMAL(10,2)) as '日薪4' from emp;

-- 13、返回员工的信息并按工作降序工资升序排列。
-- 庖丁解牛：ORDER BY DESC、ASC
	
	SELECT * from emp ORDER BY JOB DESC,SAL ASC


-- 12、返回员工的详细信息并按姓名排序。
-- 庖丁解牛： ORDER BY
	
	SELECT * from emp ORDER BY ENAME

-- 11、名字中不包含R字符的员工信息。
-- 庖丁解牛,两种方法：
-- 方法1：not like 
-- 方法2：LOCATE,如果包含，返回>0的数，否则返回0 
	
	SELECT * from emp where ENAME not like '%R%';
	SELECT * from emp where LOCATE('R',ENAME) = 0;


-- 10、找到名字长度为6个字符的员工信息。
-- 庖丁解牛：CHAR_LENGTH(str)字符函数的使用
-- 如果str 是5个包含2个字节的字符，那么LENGTH(str) 返回10
-- CHAR_LENGTH(str) 和 CHARACTER_LENGTH(str) 返回 5

	SELECT * from emp where CHAR_LENGTH(ENAME) = 6;

--  9、找出姓名以A、B、S开始的员工信息。
-- 庖丁解牛： like 模糊匹配，有两个占位符可以使用
-- % matches any number of characters, even zero characters.
-- _ matches exactly one character.

	SELECT * from emp where ENAME LIKE 'A%' OR ENAME LIKE 'B%' OR ENAME LIKE 'C%'

--  8、找出奖金少于100或者没有获得奖金的员工的信息。
	
	SELECT * from emp where COMM < 100 or  ISNULL(COMM)

-- 7、找出获得奖金的员工的工作。
 
	SELECT JOB,COMM from emp where COMM > 0

-- 6、找出10部门的经理、20部门的职员 或者既不是经理也不是职员但是工资高于2000元的员工信息。

	SELECT * from emp where DEPTNO = 10 and JOB = 'MANAGER' or DEPTNO = 20 and JOB ='CLERK'

  or (JOB not in ('manager','CLERK') and SAL > 2000)

-- 5、找出10部门的经理、20部门的职员 的员工信息。
-- 庖丁解牛： or 联合使用

	SELECT * from emp where DEPTNO = 10 and JOB = '经理' or DEPTNO = 20 and JOB ='职员'


-- 4、检索出奖金多于基本工资60%的员工信息。
	
	SELECT * from emp where COMM > SAL*0.6

-- 3、检索出奖金多于基本工资的员工信息。
	
	SELECT * from emp where COMM > SAL


-- 2、找出从事clerk工作的员工的编号、姓名、部门号。
	
	select empno,ename,deptno from emp where job = 'clerk';


-- 1、查找部门30中员工的详细信息。
-- 庖丁解牛：where 筛选
	
	SELECT * from emp where DEPTNO = 30
 

多表练习

1、返回拥有员工的部门名、部门号。
select distinct d.dname, d.deptno from dept d,emp e where d.deptno = e.deptno;
2、工资水平多于smith的员工信息。
select *from emp where sal > (select sal from emp where ename = 'smith');
3、返回员工和所属经理的姓名。
select e.ename,m.ename from emp e
left outer join emp m on e.mgr = m.empno;
select e.ename ,(select m.ename from emp m where m.empno = e.mgr) ename from emp e;
select e.ename , m.ename from emp e , emp m where e.mgr = m.empno;
4、返回雇员的雇佣日期早于其经理雇佣日期的员工及其经理姓名。
select e.ename,m.ename from emp e
inner join emp m on e.mgr = m.empno
where e.hiredate < m.hiredate;
select e.ename,m.ename from emp e,emp m
where e.mgr=m.empno
and e.hiredate < m.hiredate;
5、返回员工姓名及其所在的部门名称。
select e.ename,d.dname from emp e , dept d where e.deptno = d.deptno;
6、返回从事clerk工作的员工姓名和所在部门名称。
select e.ename,d.dname
from emp e , dept d
where e.deptno = d.deptno and e.job = 'CLERK';
7、返回部门号及其本部门的最低工资。
select deptno ,min(sal) sal
from emp
group by deptno
8、返回销售部(sales)所有员工的姓名。
select e.ename from emp e,dept d
where e.deptno = d.deptno and d.dname = 'sales';
select ename from emp where deptno=(select deptno from dept where dname='sales');
9、返回工资水平多于平均工资的员工。
select * from emp e
where e.sal > (select avg(sal) from emp);
10、返回与SCOTT从事相同工作的员工。
select * from emp
where job = (select job from emp where ename = 'scott');
select e1.* from emp e1 , (select empno,job from emp where ename = 'scott') e2
where e1.job = e2.job and e1.empno != e2.empno;
11、返回与30部门员工工资水平相同的员工姓名与工资。
select ename,sal from emp
where sal in (select sal from emp where deptno = 30);
12、返回工资高于30部门所有员工工资水平的员工信息。
select * from emp
where sal > all(select sal from emp where deptno = 30);
select * from emp
where sal > (select max(sal) from emp where deptno = 30);
13、返回部门号、部门名、部门所在位置及其每个部门的员工总数。
select dept.deptno,dept.dname,dept.loc,count(emp.deptno) number from dept,emp
where dept.deptno = emp.deptno
group by emp.deptno;
14、返回员工的姓名、所在部门名及其工资。
select ename,dname,sal from emp ,dept
where emp.deptno = dept.deptno;
15、返回员工的详细信息。(包括部门名)
select e.* , d.dname from emp e, dept d
where e.deptno = d.deptno;
16、返回员工工作及其从事此工作的最低工资。
select job , min(sal) sal from emp
group by job
17、计算出员工的年薪，并且以年薪排序。
select ename, sal * 12 as ySalary from emp order by ySalary;
18、返回工资处于第四级别的员工的姓名。
select ename,sal from emp e ,salgrade s
where e.sal >= s.losal and e.sal <= s.hisal
and s.grade = 4;
select emp.ename,emp.sal from
emp ,(select losal,hisal from salgrade where grade=4) g
where emp.sal between g.losal and g.hisal;
19、返回工资为二等级的职员名字、部门所在地、和二等级的最低工资和最高工资
select ename ,dname ,sal ,losal,hisal from emp,dept,salgrade
where emp.deptno = dept.deptno and grade = 2
and sal >= losal and sal < hisal;
20.工资等级多于smith的员工信息。
select grade from salgrade s ,emp e
where s.losal < e.sal and s.hisal > e.sal and e.ename = 'smith';
select e.* from emp e, salgrade s
where s.hisal < e.sal and s.grade = 1;
select e.* from emp e, salgrade s
where s.hisal < e.sal and s.grade = (select grade from salgrade s ,emp e
where s.losal < e.sal and s.hisal > e.sal and e.ename = 'smith');
