package cn.lee.order.web;

import cn.lee.order.pojo.Order;
import cn.lee.order.service.OrderService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 热点参数限流对默认的SpringMVC资源无效，只有通过@SentinelResource注解声明的资源才可以配置热点参数限流。
    @SentinelResource("hot")
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    // Sentinel 限流高级设置的关联模式的相关案例 /query 和 /update
    @GetMapping("/query")
    public String queryOrder() {
        return "订单查询成功";
    }
    @GetMapping("/update")
    public String updateOrder() {
        return "订单更新成功";
    }

    // Sentinel 限流高级设置的链路模式的相关案例 /queryGoods 和 /saveGoods
    @GetMapping("/queryGoods")
    public String queryGoods() {
        // 查询商品
        orderService.queryGoods();
        // 查询商品
        System.out.println("查询商品=====queryGoods");
        return "查询商品成功=====queryGoods";
    }
    @GetMapping("/saveGoods")
    public String saveGoods() {
        // 查询商品
        orderService.queryGoods();
        // 查询商品
        System.out.println("创建商品=====saveGoods");
        return "创建商品成功=====saveGoods";
    }

}
