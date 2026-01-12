package org.note;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * @author Jinx
 */
@RequiredArgsConstructor
public abstract class Abandon {

    private final RocketMQTemplate rocketTemplate;

    /**
     * <ol>
     *     <li>Topic：主题。创建主题时需要指定队列数量（物理queue数量以写队列数量为准，因为读写队列一般在物理上指向的是同一个队列）</li>
     *     <li>Queue：队列，消息存储的最小单元</li>
     *     <li>生产者组：无太多实际意义</li>
     *     <li>消费者组：可以监听topic，当有消息时，每个消费者组都可以消费该消息，但只会有一个消费者组内的实例消费</li>
     * </ol>
     */
    void framework() {
        // 顺序消息，通过额外传入 orderly_id 确认指定queue，每次发送带该唯一键的消息时会投递到同一队列保证消息顺序。
        rocketTemplate.syncSendOrderly("topic", "payload", "orderly_id");

        rocketTemplate.syncSendDelayTimeSeconds("topic", "payload", 1);

        int stock = 10;

    }
}
