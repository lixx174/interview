package org.note;

/**
 * @author jinx
 */
public interface TransactionManager {

    /**
     * 事务：通过 undo log（回滚SQL） + redo log（真正持久化SQL，提交只是修改的Buffer，异步执行该log时磁盘才真正持久化）。
     *
     * <ul>
     * <li> Read Uncommitted：不加锁，会出现 脏读，不可重复读，幻读 </li>
     * <li> Read Committed：读已提交，会出现 不可重复读，幻读  </li>
     * <li> Repeatable Read：可重复读，会出现 幻读，但是可以通过 For Update 当前读来解决（加间隙锁）</li>
     * <li> Serializable：串行 </li>
     * <p>
     * <p>Read Committed 怎么解决的脏读？ -  通过mvcc在事务 <strong>每次查询</strong> 的时候读取当前快照，确保读到的都是已经持久化的数据。
     * <p>Repeatable Read 怎么解决的不可重复读？ -  通过mvcc在事务 <strong>第一次查询</strong> 时读当前快照，后续的读都是走当前快照 所以可以重复读。
     * <p>mvcc: 多版本并发控制 （版本链 + 一致性视图），实际就是找快照。
     * 会给行隐式的加上 事务id + undo指针（它会指向另一条记录）, 每次该行变更就会有一条记录，形成了 一个<strong>版本链</strong>，
     * 在 RC（每次查询都走mvcc找，即使结果一样） RR（第二次不会再找了，直接返回第一次的快照） 隔离级别下 从版本链查询快照的过程 就是mvcc。
     * </ul>
     */
    void transaction();
}
