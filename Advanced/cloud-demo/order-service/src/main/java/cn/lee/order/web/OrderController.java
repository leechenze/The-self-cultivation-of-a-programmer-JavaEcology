package cn.lee.order.web;

import cn.lee.order.pojo.Order;
import cn.lee.order.service.OrderService;
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

}
