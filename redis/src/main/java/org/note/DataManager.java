package org.note;

/**
 * @author jinx
 */
public interface DataManager {


    /**
     * 内存数据持久化
     * <ul>
     *     <li>
     *         RDB（默认开启，Redis Data Base）：内存快照（dump.drb），某个时间节点的二进制数据。
     *         <p>文件体积小，恢复速度快，但可能会丢失部分数据。
     *         <p>可手动/定时触发，手动触发命令：save｜ bgsave（默认，冲突时使用写时复制）
     *     </li>
     *     <li>
     *         AOF（需要手动开启，Append Only File）：增量日志，写命令追加记录到文件末尾。
     *         <p>文件过大满足条件时会对文件重写，恢复速度较慢，但更安全基本不会丢失数据。
     *         <p>两种方式都开启时，默认使用AOF恢复。
     *         <p>手动重写命令：bgrewriteaof
     *     </li>
     *     <li>
     *         混合持久化（生产推荐）：RDB + AOF，融合了两者的优点。
     *         <p>文件过大重写时用RDB生成快照存入AOF，其它情况使用AOF追加。
     *         <p>开启命令：aof-use-rdb-preamble yes
     *     </li>
     * </ul>
     */
    default void persist() {
        // TODO 验证
    }
}
