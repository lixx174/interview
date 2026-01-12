package org.note;

/**
 * @author jinx
 */
public interface ShardingManager {

    /**
     * 分表：一般来说一张表 100-300w 数据最佳，考虑未来5年的数据增长一次性把表分够，因为数据迁移很麻烦。
     * 按月：
     * 续表：运维脚本，每月自动新增表
     *
     * <P>按业务key分，一般取hash 然后 % 表数量，
     * 当需要用到其它非分表key来查询时，需要单独维护mapping。
     * eg：订单表通过id分，现在通过orderId来查询，则需要先通过orderId获取到id。
     * 痛点：范围查询
     *
     */
    void shardingTable();
}
