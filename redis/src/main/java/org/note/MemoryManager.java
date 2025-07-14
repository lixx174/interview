package org.note;

/**
 * @author Jinx
 */
public interface MemoryManager {

    /**
     * <p>
     * 过期 key 删除策略。
     * 注意：针对的是已经过期的 key ，不要和内存淘汰策略混淆。
     * <p>1. 惰性删除（懒加载原理）：命中该缓存时删除，如果未命中则会一直保留。
     * <p>2. 定期删除：通过定时任务<strong>随机抽样（部分key，防止cpu飙高）</strong>带过期时间的key，如果过期了就删除。
     * <p>3. 内存淘汰：内存超过阈值时触发。策略包括：
     * <ul>
     *     <li>noeviction：默认策略，只读，写会OOM</li>
     *     <li>lru：最少最近使用：根据时间淘汰</li>
     *     <li>lfu：最少频率使用：根据次数淘汰</li>
     *     <li>random：随机淘汰</li>
     *     <li>ttl：优先淘汰快过期的key</li>
     * </ul>
     */
    void expireKeyDeleteStrategy();
}
