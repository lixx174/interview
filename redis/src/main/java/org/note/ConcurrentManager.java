package org.note;

/**
 * @author jinx
 */
public interface ConcurrentManager {

    /**
     * 高并发环境时缓存失效。
     * <ul>
     *     <li>
     *         缓存击穿：热点key突然失效，击穿了这个key。
     *         <p>查询数据库加锁 ｜ 热点key不过期
     *     </li>
     *     <li>
     *         缓存穿透：缓存，db数据都不存在，一般为恶意请求。
     *         <p>缓存空对象 ｜ 布隆过滤器（可判断一个key不存在，如果不存在就一定不存在，存在则可能不存在（可能时其他key的hash值））。
     *     </li>
     *     <li>
     *         缓存雪崩：大量key同时过期或缓存宕机。
     *         <p>key过期时间加随机数防止同时过期 ｜ 限流 ｜  redis高可用 ｜ 多级缓存
     *     </li>
     * </ul>
     */
    void onInvalid();


    /**
     * 缓存一致性问题。
     * <ul>
     *     <li>
     *         先删再写：先删缓存再更新db。
     *         <p>不一致场景：T1删了缓存正在更新db时，T2来读数据发现没缓存就会更新db的老数据到缓存中。
     *     </li>
     *     <li>
     *         先写再删：先更新db再删缓存。
     *         <p>不一致场景：如果删除redis失败。
     *     </li>
     * </ul>
     * <p>解决方案：
     * <ul>
     *     <li>延时双删：先删，更新完db延迟一段时间再删一次</li>
     *     <li>读写锁：强一致</li>
     *     <li>异步删：订阅binlog ｜ 主动投递至MQ（针对删除缓存失败）</li>
     * </ul>
     */
    void consistent();

    /**
     * 分布式锁。
     * <ul>
     *     <li>
     *         setnx + expire：先setnx抢锁，再expire给key设置过期时间。
     *         <p>缺点：两步操作非原子性，可能expire失败。
     *     </li>
     *     <li>
     *         setnx + value（过期时间）：value存锁过期时间，抢不到锁就拿出value校验。
     *         <p>缺点：客户端设置的value，需要保证分布式环境下所有客户端时间一致。
     *     </li>
     *     <li>
     *         lua脚本setnx + expire：使用lua实现原子操作
     *         <p>缺点：锁续命，可重入还需要额外操作。
     *     </li>
     *     <li>
     *         set ex nx；set扩展命令。
     *         <p>缺点：锁续命，可重入还需要额外操作。
     *     </li>
     *     <li>
     *         redisson：通过lua脚本发送setnx + expire。
     *         <p>成功会开个子线程进行锁续命（判断主线程是否还持有锁，如果是会重新设置过期时间（递归），不是则退出延时任务），
     *         <p>失败则会自旋尝试再次获取锁（semaphore.tryAcquire(ttl, unit)，还是获取失败就会去监听topic等待唤醒接着抢）。
     *         <p>释放锁时会唤醒所有正在等待争抢锁的线程，通过redis自带的发布订阅功能（阻塞前会监听一个topic）。
     *     </li>
     * </ul>
     * <p>为啥需要锁续命？：因为key需要有过期时间。
     * <p>为啥key需要过期时间？：防止redis宕机，出现死锁。
     */
    void lock();
}
