package org.note;

/**
 * @author jinx
 */
public interface DataManager {

    /**
     * 页：最小的磁盘读取单位（可以理解为mysql的封装，一页相当于很多行，减少磁盘IO），默认大小16K。
     * <p>按类型可分：数据页，索引页，undo页....
     */
    void page();
}
