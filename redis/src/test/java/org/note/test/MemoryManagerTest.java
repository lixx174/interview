package org.note.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.note.core.Context.mockJsonString;
import static org.note.core.Context.mockRandomString;
import static org.note.core.Context.waitSeconds;

/**
 * @author Jinx
 */
@SpringBootTest
public class MemoryManagerTest {

    @Autowired
    private RedissonClient rc;

    /**
     * 场景：redis内存溢出
     * <ul>
     *     配置如下：
     *     <li>maxmemory 1k</li>
     *     <li>maxmemory-policy noeviction</li>
     * </ul>
     */
    @Test
    @DisplayName("内存溢出")
    public void onMemoryOver() {
        String prefixKey = this.getClass().getName() + "_onMemoryOver_";

        for (; ; ) {
            String suffixKey = mockRandomString();
            String value = mockJsonString();

            rc.getBucket(prefixKey + suffixKey)
                    .set(value, Duration.ofDays(1));

            System.out.printf("execute successfully ===> %s%n", value);

            waitSeconds(2);
        }
    }
}
