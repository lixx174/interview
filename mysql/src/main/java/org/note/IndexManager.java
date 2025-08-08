package org.note;

/**
 * @author Jinx
 */
public interface IndexManager {

    /**
     * 索引：一种提高查询效率数据结构。
     * <p> 为什么用B+Tree？
     * <ol>
     *     <li>Hash：不支持范围查询</li>
     *     <li>二叉树：极端条件下会是一条链表</li>
     *     <li>平衡二叉树：海量数据树依然很高</li>
     *     <li>B树：所有节点都存有数据，一页存的行少</li>
     * </ol>
     */
    void index();

    /**
     * 联合索引：多个字段组成的索引。
     * <p>底层怎么存的呢？ -- 左前缀原则进行排序
     * <p>eg: index(name, age)
     * <p>(name, age) => 主键id（指向整行数据）
     * <p>("Alice", 20) => 1
     * <p>("Alice", 25) => 2
     * <p>("Bob", 18)   => 3
     * <p>("Charlie", 22) => 4
     */
    void unionIndex();

    /**
     * 索引下推：把where<strong>索引</strong>条件下推到存储引擎层，减少回表次数（过滤完再回表）以及存储引擎和server层的交互次数（流式模型，一条一条的返）。
     * <p>通俗来说：本由server层过滤的条件下推给引擎层过滤。
     * <p>eg：select * from table where col1 like ? and col2 = ?; 联合索引：union_idx_col1_col2
     * <p>innodb根据索引过滤完col1后再根据索引数据过滤col2(col1是范围查询 col2的索引失效 本应该由server层来过滤)，再回表查询数据返给server。
     * <p>如果没有ICP，innodb在过滤完col1后就开始回表了，把结果给server过滤。（如果有100条col2不满足的行就会多触发100次回表）
     */
    void indexConditionPushDown();
}
