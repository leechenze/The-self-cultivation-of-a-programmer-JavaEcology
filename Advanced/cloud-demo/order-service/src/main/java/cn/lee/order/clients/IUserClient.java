package cn.lee.order.clients;


import cn.lee.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 声明客户端，参数为服务名称
@FeignClient("userService")
public interface IUserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

}
