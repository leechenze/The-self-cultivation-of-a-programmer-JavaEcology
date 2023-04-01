package cn.lee.user.web;

import cn.lee.user.config.PatternProperties;
import cn.lee.user.pojo.User;
import cn.lee.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
// 配置热更新：方式一
// @RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    // @Value("${pattern.dateformat}")
    // private String dateformat;

    @Autowired
    private PatternProperties patternProperties;

    /**
     * 路径： /user/time
     *
     * @return 时间
     */
    @GetMapping("/time")
    public String time() {
        // System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat)));
        // return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
        // 配置热更新：方式二
        System.out.println("nacos 热更新配置方式二");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat())));
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat()));
    }

    /**
     * 路径： /user/prop
     *
     * @return PatternProperties 实体类(就是配置中的所有属性)
     */
    @GetMapping("prop")
    public PatternProperties prop() {
        return patternProperties;
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     * 第二个参数：@RequestHeader(value = "Truth", required = false) String truth 用来接收过滤器中的请求头参数信息：Truth。
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id, @RequestHeader(value = "Truth", required = false) String truth) {
        System.out.println("Truth: " + truth);
        return userService.queryById(id);
    }
}
