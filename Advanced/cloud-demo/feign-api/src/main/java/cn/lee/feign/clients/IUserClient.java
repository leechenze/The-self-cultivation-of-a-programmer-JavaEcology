package cn.lee.feign.clients;


import cn.lee.feign.clients.fallback.UserClientFallbackFactory;
import cn.lee.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 声明客户端，参数为服务名称
@FeignClient(value = "userService", fallbackFactory = UserClientFallbackFactory.class)
public interface IUserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

}
