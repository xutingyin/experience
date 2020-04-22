# SQL 经典练习

## MYSQL 版本 ：8.0.12
## 参考：https://dev.mysql.com/doc/refman/8.0/en
## 数据准备 
    --  创建 学生、课程、教师、分数 四张表，并录入初始数据
    
    CREATE TABLE student(
      id VARCHAR(10),
      name VARCHAR(10),
      age DATETIME,
      sex VARCHAR(10)
    );
    
    insert into student values('01' , '赵雷' , '1990-01-01' , '男');
    insert into student values('02' , '钱电' , '1990-12-21' , '男');
    insert into student values('03' , '孙风' , '1990-05-20' , '男');
    insert into student values('04' , '李云' , '1990-08-06' , '男');
    insert into student values('05' , '周梅' , '1991-12-01' , '女');
    insert into student values('06' , '吴兰' , '1992-03-01' , '女');
    insert into student values('07' , '郑竹' , '1989-07-01' , '女');
    insert into student values('09' , '张三' , '2017-12-20' , '女');
    insert into student values('10' , '李四' , '2017-12-25' , '女');
    insert into student values('11' , '李四' , '2017-12-30' , '女');
    insert into student values('12' , '赵六' , '2017-01-01' , '女');
    insert into student values('13' , '孙七' , '2018-01-01' , '女');
    
    create table course(
      id varchar(10),
      name varchar(10),
      teacher_id varchar(10)
    );
    
    insert into course values('01' , '语文' , '02');
    insert into course values('02' , '数学' , '01');
    insert into course values('03' , '英语' , '03');
    
    create table teacher(
      id varchar(10),
      name varchar(10)
    );
    
    insert into teacher values('01' , '张三');
    insert into teacher values('02' , '李四');
    insert into teacher values('03' , '王五');
    
    create table score(
      student_id varchar(10),
      cource_id varchar(10),
      score decimal(18,1)
    );
    
    insert into score values('01' , '01' , 80);
    insert into score values('01' , '02' , 90);
    insert into score values('01' , '03' , 99);
    insert into score values('02' , '01' , 70);
    insert into score values('02' , '02' , 60);
    insert into score values('02' , '03' , 80);
    insert into score values('03' , '01' , 80);
    insert into score values('03' , '02' , 80);
    insert into score values('03' , '03' , 80);
    insert into score values('04' , '01' , 50);
    insert into score values('04' , '02' , 30);
    insert into score values('04' , '03' , 20);
    insert into score values('05' , '01' , 76);
    insert into score values('05' , '02' , 87);
    insert into score values('06' , '01' , 31);
    insert into score values('06' , '03' , 34);
    insert into score values('07' , '02' , 89);
    insert into score values('07' , '03' , 98);

## 经典查询练习

-- 35、查询任何一门课程成绩在 70 分以上的姓名、课程名称和分数
-- 庖丁解牛：INNER JOIN 两个结果集都有的数据
 
    SELECT
        s.NAME,
        c.NAME,
        sc.score 
    FROM
        score sc
        INNER JOIN course c ON c.id = sc.cource_id
        INNER JOIN student s ON s.id = sc.student_id 
     WHERE
        sc.score >= 70
-- 34、查询所有学生的课程及分数情况（存在学生没成绩，没选课的情况）
-- 庖丁解牛：LEFT JOIN -- 以左表为主，右表没有的用null填充
    
    SELECT
        s.id,
        sc.cource_id,
        sc.score 
    FROM
        student s
        LEFT JOIN score sc ON s.id = sc.student_id
-- 33、查询课程名称为「数学」，且分数低于 60 的学生姓名和分数
-- 庖丁解牛：INNER JOIN、子查询
  
    SELECT
        s.`name`,
        sc.score 
    FROM
        student s
        INNER JOIN ( SELECT student_id, score FROM score WHERE cource_id =( SELECT id FROM course WHERE `name` = '数学' ) AND score < 60 ) sc ON s.id = sc.student_id



-- 32、查询平均成绩大于等于 85 的所有学生的学号、姓名和平均成绩
-- 庖丁解牛： AVG()、 HAVING 、 INNER JOIN、GROUP BY
    
    SELECT
        s.id,
        s.NAME,
        AVG( score ) AS avg_score 
    FROM
        score sc
        INNER JOIN student s ON s.id = sc.student_id 
    GROUP BY
        student_id 
    HAVING
        avg_score >= 85
-- 31、查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列
-- 庖丁解牛：avg()、GROUP BY、order ORDER BY
  
    SELECT
        cource_id,
        avg( score ) avg_score 
    FROM
        score 
    GROUP BY
        score 
    ORDER BY
        avg_score DESC,
        cource_id
-- 30、查询 1990 年出生的学生名单
-- 庖丁解牛：这里可以使用YEAR() 和 DATE_FORMAT(date,format) 两种方式

    SELECT * from student where YEAR(age) = '1990';
    SELECT * from student where DATE_FORMAT(age,'%Y') = '1990';
-- 29、查询同名同性别学生名单，并统计同名人数
-- 庖丁解牛: inner join 、同一张表进行关联

    SELECT
        s1.NAME,
        count(*) total 
    FROM
        student s1
        INNER JOIN student s2 ON s1.NAME = s2.NAME 
        AND s1.id <> s2.id 
    GROUP BY
        s1.NAME
-- 能够使用 inner join 的地方，一定能够使用逗号互换
    
    SELECT
        s1.NAME,
        COUNT(*) total
    FROM
        student s1,
        student s2 
    WHERE
        s1.NAME = s2.NAME 
        AND s1.id <> s2.id 
    GROUP BY
        NAME;
-- 方法2：name 分组统计	

    SELECT name, COUNT(*) total FROM student GROUP BY name HAVING total >1;

-- 28、查询名字中含有「风」字的学生信息
-- 庖丁解牛 ：like 模糊匹配的使用
-- % matches one % character.
-- _ matches one _ character.
    
    SELECT * from student where name like '%风%'

-- 27、查询男生、女生人数
-- 庖丁解牛：count()、group by 的使用
-- 方法1：根据sex 进行分组统计
    
    SELECT sex,count(*) sex_total from student GROUP BY sex
 

-- 26、查询出只选修两门课程的学生学号和姓名
-- 庖丁解牛
-- INNER JOIN 、count 、group by 、having 的用法
-- 分组查询出只选了两门课的学生id
    
    SELECT student_id, count(*) total FROM score sc GROUP BY student_id HAVING total = 2

-- 关联学生表得出学生姓名
  
    SELECT
        s.id,
        s.NAME,
        sc.total 
    FROM
        student s
        INNER JOIN ( SELECT student_id, count(*) total FROM score sc GROUP BY student_id HAVING total = 2 ) sc ON s.id = sc.student_id

-- 25、查询每门课程被选修的学生数
-- 庖丁解牛： count()、 group by 的用法
    
    SELECT cource_id, count(*) total FROM score GROUP BY cource_id


-- 24、查询各科成绩前三名的记录
-- 庖丁解牛：
-- 子查询
   
    SELECT sc1.cource_id, sc1.student_id, sc1.score
    FROM score sc1
    WHERE (SELECT COUNT(*) FROM score sc2 WHERE sc1.cource_id = sc2.cource_id AND sc1.score < sc2.score) < 3
    ORDER BY sc1.cource_id, sc1.score DESC;
-- MYSQL 8 实现
 
    SELECT * from (
    SELECT  sc.cource_id, sc.student_id, sc.score,
    ROW_NUMBER() over(PARTITION by sc.cource_id ORDER BY score desc)  ranking
    from score  sc)as A
    where A.ranking  < 4;

 

-- 23、统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比
-- 庖丁解牛：SUM()、IF()、COUNT
-- 各分数段人数
-- [0-60]
    
    SELECT SUM(IF(sc.score >= 0 AND sc.score < 60,1,0)) '[0-60)' from score sc;
-- 同理可得 [60-70)、[70-85),[85-100)
    
    SELECT SUM(IF(sc.score >= 60 AND sc.score < 70,1,0)) '[60-70)' from score sc;;
    SELECT SUM(IF(sc.score >= 70 AND sc.score < 85,1,0)) '[70-85)' from score sc;
    SELECT SUM(IF(sc.score >= 85 AND sc.score <= 100,1,0)) '[85-100]' from score sc;

-- 各分数断占百分比
    
    SELECT 
        SUM(IF(sc.score >= 0 AND sc.score < 60,1,0))/count(*)   '[0-60)',
        SUM(IF(sc.score >= 60 AND sc.score < 70,1,0))/count(*) '[60-70)',
        SUM(IF(sc.score >= 70 AND sc.score < 85,1,0))/count(*)  '[70-85)',
      SUM(IF(sc.score >= 85 AND sc.score <= 100,1,0))/count(*) '[85-100]'
    from score sc
-- 再联合课程表查出其字段

    SELECT 
        sc.cource_id,
        c.name,
        SUM(IF(sc.score >= 0 AND sc.score < 60,1,0))/count(*)   '[0-60)',
        SUM(IF(sc.score >= 60 AND sc.score < 70,1,0))/count(*) '[60-70)',
        SUM(IF(sc.score >= 70 AND sc.score < 85,1,0))/count(*)  '[70-85)',
      SUM(IF(sc.score >= 85 AND sc.score <= 100,1,0))/count(*) '[85-100]'
    from score sc,course c
    where sc.cource_id = c.id
    GROUP BY sc.cource_id;

-- 22、 查询学生的总成绩，并进行排名，总分重复时不保留名次空缺
-- 庖丁解牛：总分重复时不保留名次空缺，即两个第一名，下一个为第二名

-- MySQL 8中实现

    SELECT
        student_id,
        SUM( score ) sum_score,
        DENSE_RANK() over ( ORDER BY sum( score ) DESC ) AS ranking 
    FROM
        score 
    GROUP BY
        student_id
	
-- MySQL 8 之前版本实现

-- @实现 rownum 
-- := 除了在 set、update 等DDL中和 = 是一样的作用外，还能在select 中实现赋值操作
-- 第一步：总分倒叙排

    SELECT
        student_id,
        SUM( score ) total 
    FROM
        score 
    GROUP BY
        student_id 
    ORDER BY
        sum_score DESC

-- 第二步：排序初始值
    
    select @pre_total:=NULL,@current_rank:=0,@rank_counter:=0 

-- 第三步：增加 排序逻辑 ：如果@pre_total = total,则当前的排序为@current_rank，否则则为@current_rank +1
    
    IF(@pre_total = t.total, @current_rank, @current_rank := @current_rank + 1 ) AS ranking

-- 第四步，综合上述三步，得出最终SQL
	
	SELECT
		t.student_id,
		t.total,
	IF
		( @pre_total = t.total, @current_rank, @current_rank := @current_rank + 1 ) AS ranking,
		-- @pre_total := t.total temp1 这里需要一个临时值来存储每一次遍历的总分数
		@pre_total := t.total temp1 
	FROM
		( SELECT student_id, SUM( score ) total FROM score GROUP BY student_id ORDER BY total DESC ) t,(
		SELECT
			@pre_total := NULL,
			@current_rank := 0 
		) r 
 

-- 第五步 ：获取需要的字段
 
     SELECT
        t2.student_id,
        t2.total,
        t2.ranking 
    FROM
        (
        SELECT
            t.student_id,
            t.total,
        IF
            ( @pre_total = t.total, @current_rank, @current_rank := @current_rank + 1 ) AS ranking,
            @pre_total := t.total temp1 
        FROM
            ( SELECT student_id, SUM( score ) total FROM score GROUP BY student_id ORDER BY total DESC ) t,(
            SELECT
                @pre_total := NULL,
                @current_rank := 0 
            ) r 
        ) t2
-- 21 查询学生的总成绩，并进行排名，总分重复时保留名次空缺
-- 庖丁解牛：总分重复时保留名次空缺，即总分数相同时，若有两个第一名，则下一个为第三名
-- MYSQL 8 实现

    SELECT
        student_id,
        sum( score ) total_score,
        RANK() over ( ORDER BY SUM( score ) DESC ) AS sum_rank 
    FROM
        score 
    GROUP BY
        student_id;
	
--  MySQL 8之前版本实现

	SELECT
		t1.student_id,
		t1.total,
		t1.ranking 
	FROM
		(
		SELECT
			t.student_id,
			t.total,
		IF
			( @pre_total = t.total, @cur_rank, @cur_rank := @rank_counter ) ranking,
			@pre_total := t.total temp1,
			@rank_counter := @rank_counter + 1 temp2 
		FROM
			( SELECT student_id, SUM( score ) total FROM score GROUP BY student_id ORDER BY total DESC ) t,
		  ( SELECT @pre_total := NULL, @cur_rank := 0, @rank_counter := 1 ) r 
		) t1



-- 20、按各科成绩进行排序，并显示排名,score 重复时合并名次
-- 庖丁解牛：,score 重复时合并名次的意思是，如果有两个第一名，则下一个是第二名

    SELECT cource_id,score,DENSE_RANK() over(PARTITION by cource_id ORDER BY score desc) ranking from score ;

-- 下列写法等价
    
    SELECT
        cource_id,
        score,
        DENSE_RANK() over w AS ranking 
    FROM
        score window w AS (
            PARTITION BY cource_id 
    ORDER BY
        score DESC)
-- 19、按各科成绩进行排序，并显示排名， score 重复时保留名次空缺
-- 庖丁解牛：score 重复时保留名次空缺的含义是：如果有两个第一名，那么下一个就是第三名，没有第二名

	SELECT cource_id, score, 
	RANK() OVER w as rank1 
	 -- DENSE_RANK() OVER(PARTITION BY cource_id ORDER BY score DESC)  as rank2 ,
     -- ROW_NUMBER() OVER(PARTITION BY cource_id ORDER BY score DESC)  as rank3 
	FROM score
    WINDOW w AS (PARTITION BY cource_id ORDER BY score DESC);
-- MYSQL 8 之前的版本实现
-- 使用 @ 来模拟生成自增id 实现 rownum

    SELECT t.cource_id,t.score,t.ranking from
        ( 
        SELECT s.cource_id, s.score,
        IF(@pre_course_id = s.cource_id,
           @rank_counter := @rank_counter + 1,
           @rank_counter := 1) temp1,
        IF(@pre_course_id = s.cource_id,
           IF(@pre_score = s.score, @cur_rank, @cur_rank := @rank_counter),
           @cur_rank := 1) ranking,
        @pre_score := s.score temp2,
        @pre_course_id := s.cource_id temp3
        FROM score s, (SELECT @cur_rank := 0, @pre_course_id := NULL, @pre_score := NULL, @rank_counter := 1)r
        ORDER BY s.cource_id, s.score DESC)t
	  
-- 拓展开的知识点 :

    -- RANK()       --Returns the rank of the current row within its partition, with gaps.
    -- DENSE_RANK() --Returns the rank of the current row within its partition, without gaps. 
    -- ROW_NUMBER() --Returns the number of the current row within its partition. Rows numbers range from 1 to the number of partition rows.
    -- PARTITION BY
    -- ORDER BY
    -- IFNULL(expr1,expr2) : If expr1 is not NULL, IFNULL() returns expr1; otherwise it returns expr2.
    -- @ 实现 rownum 自增id
    -- SELECT @i:=@i+1 as rownnum  from score s,(SELECT @i:=0) as init;
    -- window w  为MYSQl 8 中新增的窗口函数
-- 18、查询各科成绩最高分、最低分和平均分：
-- 以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
-- 及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
-- 要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
-- 选择性聚合


-- 庖丁解牛：max,min,avg,GROUP BY
-- 1、课程号，最高分、最低分、平均分,选课数

    SELECT
        c.`name`,
        sc.* 
    FROM
        course c,
        (
        SELECT
            cource_id,
            max( score ) max_score,
            min( score ) min_score,
            avg( score ) avg_socre,
            count(*) s_count 
        FROM
            score 
        GROUP BY
            cource_id 
        ) sc 
    WHERE
        c.id = sc.cource_id
 
 -- 及格率 = 及格人数/总人数
 -- 这里使用一个思想，满足>=60 则为1，否则为0，再用SUM进行求和就是 及格的总人数
    
    SELECT  CONCAT(SUM(CASE WHEN score >= 60 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') pass_rate from score;
 
 -- 同理 中等率，优良率，优秀率 可得出，综合起来
 
    SELECT  CONCAT(SUM(CASE WHEN score >= 60 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') pass_rate,
     CONCAT(SUM(CASE WHEN score >= 70 AND score < 80 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') mid_rate,
     CONCAT(SUM(CASE WHEN score >= 80 AND score < 90 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') good_rate,
     CONCAT(SUM(CASE WHEN score >= 90 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') excellent_rate 
		 from score 
		 
		 
-- 将这个结合到步骤1查出的结果中，得到未进行排序的SQL
	
    SELECT
        cource_id c_id,
        course.NAME NAME,
        COUNT(*) s_count,
        MAX( score ) max_score,
        MIN( score ) min_score,
        AVG( score ) avg_score,
        CONCAT( SUM( CASE WHEN score >= 60 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) pass_rate,
        CONCAT( SUM( CASE WHEN score >= 70 AND score < 80 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) mid_rate,
        CONCAT( SUM( CASE WHEN score >= 80 AND score < 90 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) good_rate,
        CONCAT( SUM( CASE WHEN score >= 90 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) excellent_rate 
    FROM
        score,
        course 
    WHERE
        course.id = score.cource_id 
    GROUP BY
        cource_id
-- 再按照选课人数、课程id升序排
	 
    SELECT
        cource_id c_id,
        course.NAME NAME,
        COUNT(*) s_count,
        MAX( score ) max_score,
        MIN( score ) min_score,
        AVG( score ) avg_score,
        CONCAT( SUM( CASE WHEN score >= 60 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) pass_rate,
        CONCAT( SUM( CASE WHEN score >= 70 AND score < 80 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) mid_rate,
        CONCAT( SUM( CASE WHEN score >= 80 AND score < 90 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) good_rate,
        CONCAT( SUM( CASE WHEN score >= 90 THEN 1 ELSE 0 END ) / COUNT(*) * 100, '%' ) excellent_rate 
    FROM
        score,
        course 
    WHERE
        course.id = score.cource_id 
    GROUP BY
        cource_id 
    ORDER BY
        s_count DESC,
        c_id ASC;
-- 17、按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
-- 庖丁解牛 :GROUP BY ,avg ,ORDER BY 

-- 分数表中所有同学的平均成绩

    SELECT student_id,avg(score) avg_score,cource_id from score GROUP BY student_id 

-- 分数表中所有同学的成绩
    
    SELECT sc.* from score sc


-- 两个结果集关联起来并按照平均成绩降序排
	
	SELECT
		sc.*,
		t.avg_score 
	FROM
		score sc
		LEFT JOIN ( SELECT student_id, avg( score ) avg_score, cource_id FROM score GROUP BY student_id ) t ON sc.student_id = t.student_id 
	ORDER BY
		t.avg_score DESC
		
 -- 所有学生的
		
		SELECT
			s.id,
			t2.* 
		FROM
			student s
			LEFT JOIN (
			SELECT
				sc.*,
				t.avg_score 
			FROM
				score sc
				LEFT JOIN ( SELECT student_id, avg( score ) avg_score, cource_id FROM score GROUP BY student_id ) t ON sc.student_id = t.student_id 
			ORDER BY
				t.avg_score DESC 
			) t2 ON s.id = t2.student_id
	

-- 16、 检索" 01 "课程分数小于 60，按分数降序排列的学生信息

-- 庖丁解牛 : 考察 order by ，默认是asc (升序),desc 为降序排
-- 01 课程小于 60 分的学生id 

    SELECT student_id from score where cource_id = '01' and score < 60 
--  关联student 查出  学生信息

    SELECT * from student s
    INNER JOIN (
        SELECT student_id,score from score where cource_id = '01' and score < 60 
    )sc
    on s.id = sc.student_id

-- 按分数降序排列

    SELECT * from student s
    INNER JOIN (
        SELECT student_id,score from score where cource_id = '01' and score < 60 
    )sc
    on s.id = sc.student_id
    ORDER BY score desc



-- 或者 ：能够使用 inner join 的，一定能够改为两个结果集逗号连接查询

    SELECT s.*,sc.score from student s ,(SELECT student_id,score from score where cource_id = '01' and score < 60 ) sc
    
    where s.id = sc.student_id order by score desc
-- 15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩

-- 庖丁解牛： where 、having 的联合使用
-- 从分数表中 按学生分组，查询分数小于60 的学生id，平均分

     SELECT student_id,AVG(score) avg_score , count(*)from score  where score < 60 GROUP BY student_id
-- 再对上述结果集进行条件筛选，筛选条件为有两门课及其以上
    
    SELECT student_id,AVG(score) avg_score,count(*)from score  where score < 60 GROUP BY student_id HAVING count(*) >= 2

-- 联合学生表 查询出学生姓名
    
    SELECT s.id,s.name,t.avg_score from student s 
    INNER JOIN(
    SELECT student_id,AVG(score) avg_score from score  where score < 60 GROUP BY student_id HAVING count(*) >= 2)t
    on s.id = t.student_id
-- 14、查询没学过"张三"老师讲授的任一门课程的学生姓名
-- 庖丁解牛：not in 的使用
-- 张三老师讲授了哪些课

    SELECT id from course where teacher_id = (
     SELECT teacher_id from teacher where `name` ='张三'
    )
-- 查询分数表中有上述课程的学生id
  
    SELECT student_id from score where cource_id in (
    SELECT id from course where teacher_id = (
     SELECT teacher_id from teacher where `name` ='张三'
    )) GROUP BY student_id


-- 查询学生表，排除 上述结果集的学生id ,即为没有学过张三老师讲授的任意一门课的学生

    SELECT * from student where id not in (
		SELECT student_id from score where cource_id in (
			SELECT id from course where teacher_id = (
				SELECT teacher_id from teacher where `name` ='张三'
			)) GROUP BY student_id);
-- 13、查询和" 01 "号的同学学习的课程 完全相同的其他同学的信息
-- 庖丁解牛 : 考察 
-- group by  分组
-- having    分组之后再进行条件筛选

--  在 12 题的基础上加上 having 筛选条件，条件为01 同学的选课总数
	
	SELECT s.* from student s where id in (
		SELECT student_id from score where cource_id in (
		SELECT cource_id from score where student_id = '01') and student_id <> '01' GROUP BY student_id HAVING count(*) = (SELECT count(*) from score where student_id = '01'));

-- 12、查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
-- 庖丁解牛: 查看 in 关键字的使用
-- 查询 01 学号的同学选了哪些课,得到课程id
    
    SELECT cource_id from score where student_id = '01'

-- 查询哪些同学也选了 01 同学选的课,得出学生id
    
    SELECT student_id from score where cource_id in (
	    SELECT cource_id from score where student_id = '01') and student_id <> '01' GROUP BY student_id

-- 从学生表中查出哪些学生在上一步查出的学生id结果集里
	
	SELECT s.* from student s where id in (
		SELECT student_id from score where cource_id in (
		SELECT cource_id from score where student_id = '01') and student_id <> '01' GROUP BY student_id)
-- 11、查询没有学全所有课程的同学的信息
-- 庖丁解牛：
-- 总共有几门课
    
    SELECT count(id) as courceTotalNum from course;
-- 分组统计出每个学生选了几门课
    
    SELECT student_id from score  GROUP BY student_id;
-- 将课程总数作为筛选条件，查询出选了所有课程的学生信息
    
    SELECT student_id FROM score GROUP BY student_id HAVING COUNT(*) = (SELECT COUNT(*) FROM course);
-- 从学生表中排除选了所有课程的学生即为没有选择所有学生的信息
   
    SELECT s.* from student s 
    where id not in (SELECT student_id FROM score GROUP BY student_id HAVING COUNT(*) = (SELECT COUNT(*) FROM course));



-- 10、查询学过「张三」老师授课的同学的信息
-- 庖丁解牛：有多种处理方法
-- 方法1：
-- 查询出张三老师的教师id

    SELECT id FROM teacher WHERE name = '张三'
-- 张三老师教的什么课 ，查询出课程id
    
    SELECT id FROM course WHERE teacher_id = (SELECT id FROM teacher WHERE name = '张三')
-- 哪些学生选了这些课,查询出学生id
    
    SELECT student_id FROM score sc WHERE
     sc.cource_id in (SELECT id FROM course WHERE teacher_id = (SELECT id FROM teacher WHERE name = '张三'))
		 
-- 根据学生id查询出学生的信息	 
  
    SELECT * FROM student s WHERE s.id IN(SELECT student_id FROM score sc 
    WHERE sc.cource_id in (SELECT id FROM course 
    WHERE teacher_id = (SELECT id FROM teacher WHERE name = '张三')));
		 
		  
   
 -- 方法2：  
	-- 直接三张表 联合查询 [使用逗号和inner join 是等价的]
    
    SELECT s.* FROM student s, teacher t, course c, score sc WHERE
    t.id = c.teacher_id AND c.id = sc.cource_id AND sc.student_id = s.id and  t.name = '张三';
		 
 -- 或者
		
		 SELECT s.* from student s 
		 INNER JOIN score sc on s.id = sc.student_id
		 INNER JOIN course c on sc.cource_id = c.id
		 INNER JOIN teacher t on t.id = c.teacher_id
		 and t.`name` ='张三'



-- 9、查询「李」姓老师的数量

    SELECT count(*) from teacher where `name` like '李%';

-- 庖丁解牛：
-- 考察使用 count()统计数量 和 like 进行模糊匹配


-- 7、查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩(没成绩的显示为 null )

    SELECT s.id,s.`name`,sc.cource_num,sc.total_score  from 
    (SELECT  student_id, count(cource_id) cource_num, sum(score) total_score from score GROUP BY student_id)sc
    right join student s
    on sc.student_id = s.id 
-- 庖丁解牛：
-- 根据每个学生进行分组，统计出每个学生的选课总数-count(),课程总成绩sum()

    SELECT  student_id, count(cource_id) cource_num, sum(score) total_score from score GROUP BY student_id
-- 再联合 student 表进行关联查询出学生姓名,没成绩的用null 表示，故说明应该以student 表为主


-- 6、查询在 SC 表存在成绩的学生信息

-- 庖丁解牛
-- 题目需要查询出student 表中存在有成绩的学生信息

-- 很多种方法：
-- 第1种： 学生表中的id 在 成绩表里的，则代表有成绩 -- 这里使用 in

	SELECT * from student where id in (
	SELECT DISTINCT student_id from score
	)
	
-- 第2种：能够用in 的地方就能使用 exists 进行替换
	
	SELECT * from student where  EXISTS (
		SELECT student_id from score where student.id = score.student_id
	)	
	
-- 第3种：两张表都有的则代表该生有成绩 -- 这里使用 inner join 

    SELECT * FROM student s INNER JOIN (SELECT DISTINCT student_id id FROM score) sc ON sc.id = s.id;
   
-- 或者	 
    SELECT DISTINCT s.* FROM student s, score sc WHERE s.id = sc.student_id;
		
-- 第4种：以成绩表为主，成绩表中有的，则代表该生有成绩

		select * from student s RIGHT JOIN (
		SELECT DISTINCT student_id from score
		) sc
		on s.id = sc.student_id



-- 5、查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
    
    SELECT t.student_id,s.`name`,t.avgScore from 
    (SELECT student_id,AVG(score) avgScore  from score sc GROUP BY student_id HAVING  avgScore > 60) t
    left JOIN student s
    on t.student_id = s.id

-- 庖丁解牛：
-- 查询出平均成绩大于60分的学生编号和平均成绩  SELECT student_id,AVG(score) avgScore  from score sc GROUP BY student_id HAVING  avgScore > 60
-- 联合 student 表查出学生姓名，以上一步查出来的结果为主，所以采用left join -- 个人觉得这里采用 inner join 也是可以的


-- 4、查询不存在" 01 “课程但存在” 02 "课程的情况

    SELECT sc1.student_id 01studentid,sc2.student_id 02studentid,sc1.cource_id 01courceid,sc1.score 01score,sc2.cource_id 02courceid,sc2.score 02score from 
					(SELECT student_id,cource_id,score FROM score where cource_id = '01')sc1
					right join
					(SELECT student_id,cource_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id  where sc1.student_id is null
					
					
--  另解：
    
    SELECT * from score where cource_id = '02' and  student_id not in (SELECT student_id from score where cource_id = '01')


-- 庖丁解牛 ： 考察right join的用法，right join 以右表为主，左表没有的数据，用null表示
-- right join 和 left join 之间可以相互转换

-- 3、查询存在" 01 “课程但可能不存在” 02 "课程的情况(不存在时显示为 null )
    
    SELECT sc1.student_id,sc1.cource_id 01courceid,sc1.score 01score,sc2.cource_id 02courceid,sc2.score 02score from 
					(SELECT student_id,cource_id,score FROM score where cource_id = '01')sc1
					left join
					(SELECT student_id,cource_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id 

-- 庖丁解牛： 考察left JOIN 的用法，Left join 以左表为主，右表没有的数据显示null


-- 2、查询同时存在" 01 “课程和” 02 "课程的情况 
    
    SELECT sc1.student_id,sc1.score from 
					(SELECT student_id,score FROM score where cource_id = '01')sc1
					INNER join
					(SELECT student_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id 
-- 庖丁解牛 ：这里考察的是inner join 的用法，两个结果集共同拥有的交集


-- 1、查询" 01 “课程比” 02 "课程成绩高的学生的信息及课程分数

    select t.student_id,s.name,t.score
		from student s right JOIN (
		SELECT sc1.student_id,sc1.score from 
					(SELECT student_id,score FROM score where cource_id = '01')sc1
					inner join
					(SELECT student_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id where sc1.score > sc2.score
		)t on s.id = t.student_id

 -- 庖丁解牛：
 -- 查询选了 01 课程的学生 ：SELECT student_id,score FROM score where cource_id = '01'
 -- 查询选了 02 课程的学生 ：SELECT student_id,score FROM score where cource_id = '02'
 -- 查询同时选了这两门课的学生

    SELECT sc1.student_id,sc1.score from 
					(SELECT student_id,score FROM score where cource_id = '01')sc1
					inner join
					(SELECT student_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id
-- 查询 01课程 成绩比 02课程 高的学生信息及分数
    
    SELECT sc1.student_id,sc1.score from 
			(SELECT student_id,score FROM score where cource_id = '01')sc1
			inner join
			(SELECT student_id,score FROM score where cource_id = '02')sc2
			ON sc1.student_id = sc2.student_id where sc1.score > sc2.score
-- 如果题目是包含学生的姓名信息，则再联合student表进行查 
	 
	  select t.student_id,s.name,t.score
		from student s right JOIN (
		SELECT sc1.student_id,sc1.score from 
					(SELECT student_id,score FROM score where cource_id = '01')sc1
					inner join
					(SELECT student_id,score FROM score where cource_id = '02')sc2
					ON sc1.student_id = sc2.student_id where sc1.score > sc2.score
		)t on s.id = t.student_id


查询不及格的课程
SELECT * FROM score WHERE score < 60;
1
查询课程编号为 01 且课程成绩在 80 分以上的学生的学号和姓名
SELECT s.id, s.name, sc.score FROM score sc
INNER JOIN student s ON sc.student_id = s.id
WHERE sc.score >= 80 AND sc.cource_id = '01';

求每门课程的学生人数
SELECT cource_id, COUNT(*) num FROM score
GROUP BY(cource_id);
1
2
成绩不重复，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
SELECT s.id, s.name, sc.score FROM score sc
INNER JOIN student s ON sc.student_id = s.id
WHERE sc.cource_id = (SELECT id FROM course WHERE teacher_id =
      (SELECT id FROM teacher WHERE name = '张三'))
ORDER BY sc.score DESC LIMIT 1;

4
5
SELECT s.id, s.name, sc.score FROM score sc, student s, teacher t, course c
WHERE sc.student_id = s.id AND sc.cource_id = c.id AND
      c.teacher_id = t.id AND t.name = '张三'
ORDER BY sc.score DESC LIMIT 1;

4
成绩有重复的情况下，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
SELECT s.id, s.name, sc.score FROM score sc, student s, teacher t, course c
WHERE sc.student_id = s.id AND sc.cource_id = c.id AND
      c.teacher_id = t.id AND t.name = '张三' AND
      sc.score = (SELECT MAX(ss.score)
                  FROM (SELECT sc.score
                        FROM score sc, student s, teacher t, course c
                        WHERE sc.student_id = s.id AND sc.cource_id = c.id AND
                  c.teacher_id = t.id AND t.name = '张三') ss);

4
5
6
7
8
查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩
SELECT sc1.student_id, sc1.cource_id, sc1.score FROM score sc1
INNER JOIN score sc2 ON sc1.student_id = sc2.student_id
WHERE sc1.cource_id <> sc2.cource_id
GROUP BY student_id, cource_id;

4
查询每门功成绩最好的前两名
SELECT sc1.cource_id, sc1.student_id, sc1.score FROM score sc1
WHERE (SELECT COUNT(*) FROM score sc2
       WHERE sc1.cource_id = sc2.cource_id
       AND sc1.score < sc2.score) < 2
ORDER BY cource_id;

4
5
---MYSQL8.0 实现
SELECT *
FROM (SELECT cource_id, student_id, score,
      ROW_NUMBER() OVER (PARTITION BY cource_id ORDER BY score DESC) ranking
      FROM score) r
WHERE r.ranking < 3;

4
5
6
统计每门课程的学生选修人数（超过 5 人的课程才统计）。
SELECT cource_id, COUNT(*) student_num FROM score
GROUP BY(cource_id) HAVING COUNT(*) > 5;
1
2
检索至少选修两门课程的学生学号
SELECT student_id FROM score GROUP BY student_id HAVING COUNT(*) >= 2;
1
查询选修了全部课程的学生信息
SELECT student_id FROM score GROUP BY student_id
HAVING COUNT(*) = (SELECT COUNT(*) FROM course);
1
2
查询各学生的年龄，只按年份来算
SELECT name, (YEAR(CURRENT_DATE()) - YEAR(age)) age FROM student;
1
按照出生日期来算，当前月日 < 出生年月的月日则，年龄减一
SELECT name, TIMESTAMPDIFF(YEAR, age, CURDATE()) FROM student;
1
查询本周过生日的学生
SELECT * FROM student WHERE WEEKOFYEAR(age) = WEEKOFYEAR(CURRENT_DATE());
1
查询下周过生日的学生
SELECT * FROM student WHERE WEEKOFYEAR(age) = WEEKOFYEAR(DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY));
1
查询本月过生日的学生
SELECT * FROM student WHERE MONTH(age) = MONTH(CURRENT_DATE());
1
查询下月过生日的学生
SELECT * FROM student WHERE MONTH(age) = MONTH(DATE_ADD(CURRENT_DATE(), INTERVAL 1 MONTH));