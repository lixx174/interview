package org.note;

/**
 * @author Jinx
 */
public interface PerformanceManager {

    /**
     * 慢SQL如何优化？ 注意：不是索引优化
     * <p> 优化前得先开启配置监控
     * <ul>
     *     <li>slow_query_log:是否开启</li>
     *     <li>slow_query_log_file:慢SQL存储所在文件</li>
     *     <li>long_query_time:最大查询时间，超过该时间标记为慢SQL</li>
     *     <li>log_queries_not_using_indexes:没有使用索引也记录</li>
     * </ul>
     *
     * <ul>
     *     单表：
     *     <li>不要select * ：无法索引覆盖，增加磁盘IO</li>
     *     <li>提升group by效率：分组字段得走索引</li>
     *     <li>批量操作减少与数据库交互次数：将批量操作尽可能的封装为一个SQL，减少了网络开销，以及server层的解析（每个sql都会解析优化）</li>
     *     <li>使用limit：可减少网络开销，传输的数据少更快</li>
     *     <li>union all 代替 union：union all 不会去重</li>
     *     多表：
     *     <li>小表驱动大表：一个join相当于一个foreach，外层的for越小 循环次数就越少 效率越高</li>
     *          eg：select * from user u join department d on d.userId = u.id
     *          <p>表user:80w条， 表department:30条
     *          <pre><code>
     *              for(String userId department_size){
     *                  // 30次搞定 如果大表在前则需要循环80w次
     *                  select * from user where id = userId;
     *              }
     *          </code></pre>
     * </ul>
     */
    void slowSql();
}
