package org.note;

/**
 * @author Jinx
 */
public interface Abandon {

    /**
     * 缓存，存的<strong>页数据</strong>在一个LRU链表，每个页可以理解为一个对象，有id（页号）和data（页数据）。
     * 每次获取数据时先来buffer根据页号看看有没有，没有才触发磁盘IO。
     */
    void bufferPool();

    /**
     * mysql架构
     *
     * <p>client
     * <p>  ↓
     * <p>server层（解析SQL，二次过滤等）
     * <p>  ↓
     * <p>存储引擎（处理server的请求，通过索引或是全表扫描的方式将数据返回至server层）
     * <p>  ↓
     * <p>基表
     *
     * <p> eg：select * from table where column = ?;
     * <p>按走索引的流程，因为不走的话就直接全盘扫描（顺序遍历聚簇索引的叶子节点）了
     * <ol>
     *     <li>client拿到连接后把该sql发给server</li>
     *     <li>server开始解析，预处理（校验，表，字段是否存在），查询优化（走索引）</li>
     *     <li>server把解析完后将结果发给innodb（引擎层）开始真正执行查询数据</li>
     *     <li>innodb根据索引去加载索引页（先加载根索引），然后就一级一级的找（每个节点对应一个页，但是可能在同一页，会走buffer也很快）</li>
     *     <li>innodb找到对应的主键后去加载聚簇索引回表（索引覆盖除外），每一个id都会加载一次，如果在同一页就直接buffer获取</li>
     *     <li>innodb将结果返回给server</li>
     * </ol>
     */
    void framework();
}
