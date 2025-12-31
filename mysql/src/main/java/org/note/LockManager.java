package org.note;

/**
 * @author Jinx
 */
public interface LockManager {

    /**
     * 按粒度：表锁（没命中索引时，给聚簇索引都加行锁） 行锁。
     */
    void lock();
}
