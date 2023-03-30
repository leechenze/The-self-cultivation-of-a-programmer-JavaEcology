package cn.lee.order.service;

import cn.lee.order.clients.IUserClient;
import cn.lee.order.mapper.OrderMapper;
import cn.lee.order.pojo.Order;
import cn.lee.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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



}
