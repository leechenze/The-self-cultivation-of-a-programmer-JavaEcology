package cn.lee.feign.clients.fallback;

import cn.lee.feign.clients.IUserClient;
import cn.lee.feign.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<IUserClient> {
    @Override
    public IUserClient create(Throwable cause) {
        // IUserClient 是一个接口，要实现它必须写成匿名内部类。
        return new IUserClient() {
            @Override
            public User findById(Long id) {
                log.error("查询用户异常", cause);
                return new User();
            }
        };
    }
}
