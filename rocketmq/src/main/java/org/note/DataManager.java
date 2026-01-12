package org.note;

/**
 * @author jinx
 */
public interface DataManager {

    /**
     * 持久化通过 commitLog 顺序io（快）存储，每个queue会有单独的索引文件（存commitLog地址，快速寻址）
     */
    void persist();
}
