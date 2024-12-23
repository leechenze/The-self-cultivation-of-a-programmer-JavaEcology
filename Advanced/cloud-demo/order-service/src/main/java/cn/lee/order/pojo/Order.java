package cn.lee.order.pojo;


import lombok.Data;
import cn.lee.feign.pojo.User;

@Data
public class Order {
    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    private User user;
}