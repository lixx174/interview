### related to lock_2.sql by the no

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
select *
from user for
update;
select *
from user
where id = 1 for
update;

# 联合索引
explain
select *
from user
where name = 'zs'
  and age = 50; # 联合索引 第二个值不存在看看会走联合索引吗？