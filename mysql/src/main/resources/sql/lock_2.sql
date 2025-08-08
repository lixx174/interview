### related to lock_1.sql by the no

# 查询事务自动提交 0：关闭 1：开启
select @@autocommit;

SET autocommit = 0;

# 事务操作
begin;

commit;

rollback;


# demo1 表锁
select *
from user;
update user
set name = 'zs_update'
where id = 1; # 事务1已经锁住了整张表 试试事务2能修改吗？ -> 答案是不能 会阻塞