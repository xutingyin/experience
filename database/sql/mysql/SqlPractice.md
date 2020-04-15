# SQL 经典练习
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
11、查询没有学全所有课程的同学的信息

    SELECT s.* FROM student s WHERE s.id NOT IN
        (SELECT student_id FROM score GROUP BY student_id HAVING COUNT(*) = (SELECT COUNT(*) FROM course));
     
12、查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息

    SELECT DISTINCT s.* FROM student s INNER JOIN
     (SELECT s2.student_id student_id FROM score s2 WHERE s2.student_id <> '01' AND s2.cource_id IN
     (SELECT s1.cource_id FROM score s1 WHERE s1.student_id = '01')) s3
     ON s3.student_id = s.id;
     
    SELECT student.* FROM student WHERE id IN
     (SELECT student_id FROM score sc WHERE sc.cource_id IN
     (SELECT cource_id FROM score sc1 WHERE sc1.student_id = '01')) AND id <> '01';
13、查询和" 01 "号的同学学习的课程 完全相同的其他同学的信息

    SELECT s.* FROM student s INNER JOIN
     (SELECT student_id id, COUNT(*) FROM score sc WHERE sc.cource_id IN
      (SELECT cource_id FROM score WHERE student_id = '01') AND
      sc.student_id <> '01' GROUP By sc.student_id HAVING
      COUNT(*) = (SELECT COUNT(*) FROM score WHERE student_id = '01')) sd
      ON sd.id = s.id;
 
14、查询没学过"张三"老师讲授的任一门课程的学生姓名

    SELECT student.* FROM student WHERE id NOT IN
     (SELECT student_id FROM score WHERE cource_id IN
      (SELECT c.id FROM course c WHERE c.teacher_id = (SELECT t.id FROM teacher t WHERE t.name = '张三')));

15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩

    SELECT s.id, s.name, avg_score FROM student s INNER JOIN
     (SELECT student_id, AVG(score) avg_score, COUNT(*) FROM score
      WHERE score < 60 GROUP BY student_id HAVING COUNT(*) >= 2) a
     ON a. student_id = s.id;

 
16、检索" 01 "课程分数小于 60，按分数降序排列的学生信息

    SELECT s.* FROM student s INNER JOIN
     (SELECT * FROM score WHERE cource_id = '01' AND score < 60) sc ON
     sc.student_id = s.id ORDER BY sc.score DESC;

    SELECT s.*, sc.* FROM student s, (SELECT * FROM score WHERE cource_id = '01' AND score < 60) sc  WHERE s.id = sc.student_id ORDER BY sc.score DESC;
 
17、按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩

    SELECT s.*, sc.score, sc.avg_score FROM student s LEFT JOIN
     (SELECT student_id student_id, score score, AVG(score) avg_score FROM score GROUP BY student_id) sc
     ON sc.student_id = s.id ORDER BY sc.avg_score DESC;

18、查询各科成绩最高分、最低分和平均分：

    – 以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
    – 及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
    – 要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
    -- 选择性聚合
    
    SELECT cource_id c_id, course.name name, COUNT(*) s_count, MAX(score) max_score,
     MIN(score) min_score, AVG(score) avg_score,
     CONCAT(SUM(CASE WHEN score >= 60 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') pass_rate,
     CONCAT(SUM(CASE WHEN score >= 70 AND score < 80 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') mid_rate,
     CONCAT(SUM(CASE WHEN score >= 80 AND score < 90 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') good_rate,
     CONCAT(SUM(CASE WHEN score >= 90 THEN 1 ELSE 0 END) / COUNT(*) * 100, '%') excellent_rate
     FROM score, course WHERE course.id = score.cource_id GROUP BY cource_id
     ORDER BY s_count DESC, c_id ASC;

 
19、按各科成绩进行排序，并显示排名， Score 重复时保留名次空缺
--- MYSQL 8.0 实现

    SELECT cource_id, score, RANK() OVER(PARTITION BY cource_id ORDER BY score DESC) FROM score;
     
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
    ORDER BY s.cource_id, s.score DESC;

 
20、按各科成绩进行排序，并显示排名， Score 重复时合并名次

    --- MYSQL 8.0 实现
    SELECT cource_id, score, DENSE_RANK() OVER(PARTITION BY cource_id ORDER BY score DESC) FROM score;
     
    SELECT s.cource_id, s.score,
    IF(@pre_score = s.score, @cur_rank, @cur_rank := @cur_rank + 1) temp1,
    @pre_score := s.score,
    IF(@pre_course_id = s.cource_id, @cur_rank, @cur_rank := 1) ranking,
    @pre_course_id := s.cource_id
    FROM score s, (SELECT @cur_rank :=0, @pre_score = NULL, @pre_course_id := NULL) r
    ORDER BY cource_id, score DESC;

 
    SELECT s.cource_id, s.score,
    IF(@pre_course_id = s.cource_id,
       IF(@pre_score = s.score, @cur_rank, @cur_rank := @cur_rank + 1),
       @cur_rank := 1) ranking,
    @pre_score := s.score,
    @pre_course_id := s.cource_id
    FROM score s, (SELECT @cur_rank :=0, @pre_score = NULL, @pre_course_id := NULL) r
    ORDER BY cource_id, score DESC;

 
21、查询学生的总成绩，并进行排名，总分重复时保留名次空缺

    SELECT student_id, SUM(score) total, RANK() OVER(ORDER BY SUM(score) DESC) ranking
    FROM score GROUP BY student_id;
     
    SELECT t.student_id, t.total,
    IF(@pre_total = t.total, @cur_rank, @cur_rank := @rank_counter) ranking,
    @pre_total := t.total temp1,
    @rank_counter := @rank_counter + 1 temp2
    FROM (SELECT student_id, SUM(score) total
          FROM score GROUP BY student_id ORDER BY total DESC) t,
          (SELECT @pre_total := NULL, @cur_rank := 0, @rank_counter := 1) r;

 
查询学生的总成绩，并进行排名，总分重复时不保留名次空缺
SELECT student_id, SUM(score) total, DENSE_RANK() OVER(ORDER BY SUM(score) DESC) ranking
FROM score GROUP BY student_id;
1
2
SELECT t.student_id, t.total,
IF(@pre_total = t.total, @cur_rank, @cur_rank := @cur_rank + 1) ranking,
@pre_total := t.total temp1
FROM (SELECT student_id, SUM(score) total
      FROM score GROUP BY student_id ORDER BY total DESC) t,
      (SELECT @pre_total := NULL, @cur_rank := 0) r;

4
5
6
统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比
SELECT c.id, c.name,
SUM(IF(sc.score >= 0 AND sc.score < 60, 1, 0)) / COUNT(*) '[0-60)',
SUM(IF(sc.score >= 60 AND sc.score < 70, 1, 0)) / COUNT(*) '[60-70)',
SUM(IF(sc.score >= 70 AND sc.score < 85, 1, 0)) / COUNT(*) '[70-85)',
SUM(IF(sc.score >= 85 AND sc.score <= 100, 1, 0)) / COUNT(*) '[80-100]'
FROM course c LEFT JOIN score sc ON c.id = sc.cource_id
GROUP BY sc.cource_id;

4
5
6
7
查询各科成绩前三名的记录
--关联子查询
SELECT sc1.cource_id, sc1.student_id, sc1.score
FROM score sc1
WHERE (SELECT COUNT(*) FROM score sc2 WHERE sc1.cource_id = sc2.cource_id AND sc1.score < sc2.score) < 3
ORDER BY sc1.cource_id, sc1.score DESC;

4
5
查询每门课程被选修的学生数
SELECT cource_id, COUNT(*) FROM score GROUP BY cource_id;
1
查询出只选修两门课程的学生学号和姓名
SELECT s.* FROM student s INNER JOIN
 (SELECT student_id, COUNT(*) s_count FROM score GROUP BY student_id HAVING COUNT(*) = 2) sc
 ON sc.student_id = s.id;

查询男生、女生人数
SELECT sex, COUNT(*) FROM student GROUP BY sex;
1
查询名字中含有「风」字的学生信息
SELECT student.* FROM student WHERE name Like '%风%';
1
查询同名同性学生名单，并统计同名人数
SELECT s1.name, COUNT(*) FROM student s1, student s2 WHERE
 s1.name = s2.name AND s1.id <> s2.id GROUP By name;
1
2
SELECT s1.name, COUNT(*) FROM student s1
 INNER JOIN student s2 ON s1.name = s2.name AND s1.id <> s2.id
 ORDER BY name;

SELECT name, COUNT(*) FROM student GROUP BY name HAVING COUNT(*) >1;
1
查询 1990 年出生的学生名单
SELECT * FROM student WHERE YEAR(age) = 1990;
1
查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列
SELECT cource_id, AVG(score) avg_score
FROM score GROUP BY cource_id
ORDER BY avg_score DESC, cource_id;

查询平均成绩大于等于 85 的所有学生的学号、姓名和平均成绩
SELECT s.id, s.name, AVG(sc.score) avg_score
FROM student s INNER JOIN score sc ON s.id = sc.student_id
GROUP BY sc.student_id HAVING avg_score >=85;

查询课程名称为「数学」，且分数低于 60 的学生姓名和分数
SELECT s.name, sc.score FROM student s
LEFT JOIN score sc ON s.id = sc.student_id
WHERE sc.cource_id = (SELECT id FROM course WHERE name = '数学') AND sc.score < 60;

查询所有学生的课程及分数情况（存在学生没成绩，没选课的情况）
SELECT s.id, s.name, sc.cource_id, sc.score FROM student s
LEFT JOIN score sc ON s.id = sc.student_id;
1
2
查询任何一门课程成绩在 70 分以上的姓名、课程名称和分数
SELECT s.name, c.name, sc.score
FROM score sc INNER JOIN student s ON s.id = sc.student_id
INNER JOIN course c ON c.id = sc.cource_id
WHERE sc.score >= 70;

4
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