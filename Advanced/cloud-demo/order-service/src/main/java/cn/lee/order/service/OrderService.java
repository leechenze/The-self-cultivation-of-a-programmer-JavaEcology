package cn.lee.order.service;


import cn.lee.feign.clients.IUserClient;
import cn.lee.order.mapper.OrderMapper;
import cn.lee.order.pojo.Order;
import cn.lee.feign.pojo.User;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * RestTemplate的方式进行请求调用
     */
    // @Autowired
    // private RestTemplate restTemplate;
    //
    // public Order queryOrderById(Long orderId) {
    //
    //     // 1.查询订单
    //     Order order = orderMapper.findById(orderId);
    //     // 2.使用RestTemplate发起Http请求，查询用户
    //     // String url = "http://localhost:8081/user/" + order.getUserId();
    //     String url = "http://userService/user/" + order.getUserId();
    //     // 2.1 发送http请求，实现远程调用
    //     User user = restTemplate.getForObject(url, User.class);
    //     // 3.封装User到Order
    //     order.setUser(user);
    //     // 4.返回
    //     return order;
    // }


    /**
     * 使用 Feign 进行请求调用
     */
    @Autowired
    private IUserClient userClient;
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.使用 Feign 发起Http请求，查询用户
        User user = userClient.findById(order.getUserId());
        // 3.封装User到Order
        order.setUser(user);
        // 4.返回
        return order;
    }


    // Sentinel 限流高级设置的链路模式的相关案例 /queryGoods 和 /saveGoods
    @SentinelResource("queryGoods")
    public void queryGoods() {
        System.out.println("===========================");
        System.out.println("查询商品");
        System.out.println("===========================");
    }
}
