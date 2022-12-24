select * from t_l_course;
--insert into t_l_course (course_id,descp,tags,title) values(1,'HTML is the standard markup language for Web pages','html|element|web|css','HTML');


select * from t_l_sub_topic;
SELECT * FROM t_l_content;
--drop table t_l_content;

DESC t_l_sub_topic;
DESC t_l_content;

--alter table t_l_content add constraint FK47337ulrdqorsvqucn5nmq8vf foreign key (sub_topic_sub_topic_id) references t_l_sub_topic;