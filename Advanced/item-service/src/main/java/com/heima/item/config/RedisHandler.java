package com.heima.item.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import com.heima.item.service.IItemService;
import com.heima.item.service.IItemStockService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;

@Configuration
public class RedisHandler implements InitializingBean {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IItemService iItemService;
    @Autowired
    private IItemStockService iItemStockService;

    // 静态常量工具，用以序列化和反序列化Json
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * InitializingBean.afterPropertiesSet
     * 这个Bean的afterPropertiesSet方法会在 bean（RedisHandler）创建完，且@Autowired注入成功之后执行。
     * 那么它就可以在项目启动后就执行，这样来实现缓存预热预存。
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 初始化缓存
         */
        // 1.1查询商品信息
        List<Item> itemList = iItemService.list();
        // 1.2放入缓存
        for (Item item : itemList) {
            // item序列化为JSON
            String itemJson = MAPPER.writeValueAsString(item);
            // 存入Redis，一般我们不能直接存ID，因为有冲突的可能，所以这里加一个 item:id: 的前缀。
            redisTemplate.opsForValue().set("item:id:" + item.getId(), itemJson);
        }

        // 2.1查询库存信息
        List<ItemStock> itemStockList = iItemStockService.list();
        // 2.2放入缓存，步骤同上。
        for (ItemStock itemStock : itemStockList) {
            String itemStockJson = MAPPER.writeValueAsString(itemStock);
            redisTemplate.opsForValue().set("item:stock:id:" + itemStock.getId(), itemStockJson);
        }
    }
}
