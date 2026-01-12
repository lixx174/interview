package org.note;

/**
 * @author Jinx
 */
public interface PerformanceManager {

    /**
     * 消息堆积。
     *  <ol>
     *      <li> 观察堆积瓶颈，如果随着时间消息还在堆积，说明消费能力不足，否则可能为历史债务。 </li>
     *      <li> 如果 消费者实例 < Queue ，可以增加消费者提高吞吐 </li>
     *      <li> consumeThreadMin / consumeThreadMax 增加消费线程，比如配置8，那么就会有8个线程去消费一个queue </li>
     *      <li> consumeMessageBatchMaxSize 批量消费，一次拉多条，减少了IO </li>
     *      <li> 以上都不行，需要进行扩容，一般当前队列 * 2，同时增加对应的消费者实例 </li>
     *  </ol>
     * <p>
     * 注意：消费者实例和队列关系：一个queue只会被一个消费者实例监听消费。
     * <p>eg： 8个写队列，消费者组下8个实例时就是一人监听一个队列，
     * 4个实例时就是一人监听二个队列，
     * 10个实例时就会有2个实例空转（也就是说增加消费者有时候并不能提高消费速度）。
     */
    void onPileUp();
}
