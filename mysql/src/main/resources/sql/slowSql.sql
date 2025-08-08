# 查询事务自动提交 0：关闭 1：开启
select @@autocommit;

SET autocommit = 0;

# 事务操作
begin;

commit;

rollback;


# select *
# 看看是否会走索引 idx_name_age  ->  会走 并且Using index condition
explain
select *
from user
where name like 's%';

explain
select *
from user
where name = 's%';
