package com.heima.item.test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class CaffeineTest {

    /**
     * 基本用法测试
     */
    @Test
    void testBasicOps() {
        Cache<Object, Object> cache = Caffeine.newBuilder().build();
        // 存数据
        cache.put("girlfriend","leechenhui");
        // 取数据
        String girlfriend = (String) cache.getIfPresent("girlfriend");
        System.out.println("girlfriend = " + girlfriend);
        // 取数据，如果未命中，则执行第二个参数查询数据库
        Object defaultGF = cache.get("defaultGF", key -> {
            // 根据key取数据库查询数据。
            return "liuyan";
        });
        // 小技巧记录：soutv ==> System.out.println("defaultGF = " + defaultGF);
        System.out.println("defaultGF = " + defaultGF);
    }

    /**
     * 基于大小设置驱逐策略：
     * @throws InterruptedException
     */
    @Test
    void testEvictByNum() throws InterruptedException {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                // 设置缓存大小上限为 1
                .maximumSize(1)
                .build();
        // 存数据
        cache.put("gf1", "柳岩");
        cache.put("gf2", "范冰冰");
        cache.put("gf3", "迪丽热巴");
        // 延迟10ms，给清理线程一点时间
        Thread.sleep(10L);
        // 获取数据
        System.out.println("gf1: " + cache.getIfPresent("gf1"));
        System.out.println("gf2: " + cache.getIfPresent("gf2"));
        System.out.println("gf3: " + cache.getIfPresent("gf3"));
    }

    /**
     * 基于时间设置驱逐策略：
     * @throws InterruptedException
     */
    @Test
    void testEvictByTime() throws InterruptedException {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(1)) // 设置缓存有效期为 10 秒
                .build();
        // 存数据
        cache.put("gf", "柳岩");
        // 获取数据
        System.out.println("gf: " + cache.getIfPresent("gf"));
        // 休眠一会儿
        Thread.sleep(1200L);
        System.out.println("gf: " + cache.getIfPresent("gf"));
    }
}
