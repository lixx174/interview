package org.note;

/**
 * @author Jinx
 */
public interface LockManager {

    /**
     * 按粒度：表锁 行锁。
     */
    void lock();
}
