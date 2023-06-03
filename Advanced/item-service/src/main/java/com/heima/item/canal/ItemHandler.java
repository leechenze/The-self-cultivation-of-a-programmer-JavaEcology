package com.heima.item.canal;

import com.github.benmanes.caffeine.cache.Cache;
import com.heima.item.config.RedisHandler;
import com.heima.item.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

// canal的声明表的注解
// @CanalTable("tb_item")
// @Component
public class ItemHandler implements EntryHandler<Item> {

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private Cache<Long, Item> itemCache;

    /**
     * 每当数据库进行了增删改，就会把对应的数据传递到这些方法。
     * @param item
     */
    @Override

    public void insert(Item item) {
        // 写数据到JVM进程缓存
        itemCache.put(item.getId(), item);
        // 写数据到Redis缓存
        redisHandler.saveItem(item);
    }

    @Override
    public void update(Item before, Item after) {
        // 更新数据到JVM进程缓存
        itemCache.put(after.getId(), after);
        // 更新数据到Redis缓存
        redisHandler.saveItem(after);
    }

    @Override
    public void delete(Item item) {
        // 删除数据到JVM进程缓存
        itemCache.invalidate(item.getId());
        // 删除数据到Redis缓存
        redisHandler.deleteItem(item.getId());
    }
}
