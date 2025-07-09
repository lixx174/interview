package org.note;

/**
 * @author Jinx
 */
public interface RedisApplication {

    /**
     * 过期 key 删除策略。
     * 注意：针对的是已经过期的 key ，不要和内存淘汰策略混淆。
     * <p>1. 惰性删除（懒加载原理）：命中该缓存时删除，如果未命中则会一直保留直至触发<strong>内存淘汰策略</strong>。
     * <p>2. 定期删除： 通过定时任务<strong>随机抽样</strong>带过期时间的key，如果过期了就删除。
     */
    void expireKeyDeleteStrategy();
}
