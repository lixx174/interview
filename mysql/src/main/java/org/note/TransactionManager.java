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

    /**
     * 分布式事务
     *
     * <ol>
     * <li>
     *     2PC（Two-Phase Commit）：二阶段提交。组件：TM：事务管理器， RM：资源管理器（DB）
     *     <ol>
     *         <li>PreCommit(预提交)，TM向RM发起prepare，RM会执行本地事务但是不提交（已生成undo.log和redo.log）</li>
     *         <li>commit/rollback（决策阶段）：TM根据RM返回结果决定是提交还是回滚</li>
     *     </ol>
     *     缺点：阻塞
     *     <ul>
     *         <li>RM会加锁等TM决策，高并发环境下阻塞严重</li>
     *         <li>TM单点故障，RM会长时间阻塞</li>
     *         <li>二阶段决策，有RM故障时（网络不可靠等）会产生不一致，需要人工解决</li>
     *     </ul>
     * </li>
     * <li>
     *     3PC（Three-Phase Commit）：三阶段提交，组件：和2pc一样。
     *     <p>解决了2pc的阻塞问题，RM可以超时提交。
     *     <ol>
     *         <li>CanCommit（询问阶段），TM问所有RM是否可以执行事务，RM不会做真的提交，只会做校验相关，判断这个事务是否能成功执行</li>
     *         <li>PreCommit(预提交)，和2pc一样，但是会加个超时自动提交的定时器</li>
     *         <li>commit/rollback（决策阶段）：TM根据RM返回结果决定是提交还是回滚</li>
     *     </ol>
     *     缺点：复杂度高，性能不理想。
     * </li>
     * <br/>
     * <strong>
     *     <p>⬆⬆⬆前面都是强一致的方案⬆⬆⬆
     *     <p>⬇⬇⬇后面的方案从根本上做出了改变，不再要求强一致，只需要保证最终一致就行⬇⬇⬇
     * </strong>
     * <br/>
     * <li>
     *     TCC（Try-Confirm-Cancel）：业务层自己实现，一般需要定义并实现3个接口 prepare（Try 冻结），commit（Confirm 提交），rollback（Cancel 回滚）。
     *     <p>解决了阻塞问题（无锁）。
     *     缺点：业务侵入，每个地方都需要手动实现。
     * </li>
     * <li>
     *     Saga：为每一步增加一个补偿，当失败时执行前面已执行的补偿事务。
     *     <p>解决了阻塞问题（无锁）。
     *     缺点：一致性弱，每个业务都需要设计补偿事务。
     * </li>
     * <li>
     *     最大努力通知：事务发起方本地事务执行成功后，主动通知（重试） + 结果查询接口 通知第三方保证最终一致。
     * </li>
     * <li>
     *     事务消息：先发送half消息到broker，本地事务执行完提交后告诉broker提交，broker将消息投递给消费者（未确认前消息不可见），
     *     如果broker在规定时间内没接受到生产者的确认消息，则会进行回查（生产者检查本地事务的执行结果返回给broker）。
     * </li>
     * <li>
     *     Seata：基于AT（Automatic Transaction）模式（默认都是该模式）的分布式事务。还支持其它模式，eg：XA（数据库层面的规范，基于2pc实现），SAGA，TCC等。
     *     <ul>
     *         组件：
     *         <li>TM（事务管理器）：@GlobalTransactional 标记的服务都可以视为TM，向TC注册事务，发起提交/回滚。</li>
     *         <li>RM（资源管理器）：引入seata后被动感知自己是分支事务（根据组件内全局XID实现），执行完后会向TC注册自己的状态。</li>
     *         <li>TC（事务协调器）：通知RM提交/回滚，同时他也是兜底组件，因为RM注册到了TC，即使TM挂了，他也能代替TM完成事务。</li>
     *     </ul>
     * </li>
     * </ol>
     */
    void distributeTransaction();
}
