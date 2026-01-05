package org.note;

/**
 * @author Jinx
 */
public interface LockManager {

    /**
     * 按粒度：
     * <ul>
     *     <li>表锁：未命中索引时，给聚簇索引加行锁和间隙锁（-oo, +oo）</li>
     *     <li>行锁：锁定当前索引</li>
     *     <li>
     *         间隙锁：对已有索引范围加锁（非唯一索引。如果是唯一索引的等值查询只会加行锁），防止幻读。仅仅RR隔离级别下有效。
     *         <p>eg：1 3 6 9
     *         <p>select ... where column = 6 : 会锁住 6 这个索引和 (3,6) 这个间隙（默认锁左边，因为右边的索引还没扫描）。
     *         <p>select ... where column = 7 : (6,9) 这个间隙。
     *         <p>select ... where column = 10 : (9,+oo) 这个间隙。
     *     </li>
     *     <li>Next-Key Lock：行锁 + 间隙锁 </li>
     * </ul>
     */
    void lock();
}
