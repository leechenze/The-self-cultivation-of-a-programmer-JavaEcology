package cn.itcast.redisdemo;

import io.lettuce.core.ReadFrom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    /**
     * Spring的RedisTemplate 配置读写分离
     * @return
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
        /**
         * 匿名内部类写法：
         * 因为这个接口内部只有customize这一个方法，所以可以直接 new 这个接口 LettuceClientConfigurationBuilderCustomizer
         */
        return new LettuceClientConfigurationBuilderCustomizer() {
            @Override
            public void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigurationBuilder) {
                clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
            }
        };

        /**
         * Lambda表达式写法：
         */
        // return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
    }

}
