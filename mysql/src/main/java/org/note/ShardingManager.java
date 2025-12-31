package org.note;

/**
 * @author jinx
 */
public interface ShardingManager {

    /**
     * 分表：
     * 按月：
     * 续表：运维脚本，每月自动新增表
     */
    void shardingTable();
}
