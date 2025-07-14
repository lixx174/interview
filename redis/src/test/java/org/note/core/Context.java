package org.note.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jinx
 */
public class Context {

    private static final ObjectMapper om = new ObjectMapper();


    /**
     * 构造json字符串
     *
     * @return json字符串
     */
    @SneakyThrows
    public static String mockJsonString() {
        URL url = Thread.currentThread()
                .getContextClassLoader()
                .getResource("test.json");

        return om.writeValueAsString(
                om.readValue(url, Map.class)
        );
    }

    /**
     * 构造随机数
     *
     * @return 12位随机纯
     */
    public static String mockRandomString() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(24);
    }


    /**
     * 阻塞调用线程
     *
     * @param seconds 时间 单位：秒
     */
    public static void waitSeconds(long seconds) {
        LockSupport.parkNanos(
                TimeUnit.SECONDS.toNanos(seconds)
        );
    }
}
