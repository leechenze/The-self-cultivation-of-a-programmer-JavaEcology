package com.heima.item.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<Long, Item> itemCache() {
        // 缓存初始化大小为100，缓存上限为10000
        return Caffeine.newBuilder().initialCapacity(100).maximumSize(10000).build();
    }

    @Bean
    public Cache<Long, ItemStock> stockCache() {
        // 缓存初始化大小为100，缓存上限为10000
        return Caffeine.newBuilder().initialCapacity(100).maximumSize(10000).build();
    }
}
