package org.example.common;

import org.example.config.EncryptionConfig;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserService userService;

    // @Autowired
    // public EncryptionConfig encryptionConfig;

    /**
     *
     * 基本使用
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // security默认会自动生成一个名为 user的账号，密码会在控制台打印：
        // Using generated security password: bf301dcf-db3d-42f1-bb6c-2f6d9c3b17d6
        // 开启登录（开启登录后才会正常访问 /login 路径）
        http.formLogin()
                // 登录成功之后转发到 /index 页面
                .successForwardUrl("/index")
                // 返回 HttpSecurity 对象
                .and()
                .authorizeRequests()
                // 指定 /admin 的请求只能由 角色为 admin 的用户访问。antMatchers中可以使用（ant表达式）
                .antMatchers("/admin").hasRole("admin")
                // 指定 /user 的请求只能由 角色为 user 的用户访问。antMatchers中可以使用（ant表达式）
                .antMatchers("/user").hasRole("user")
                // 指定 /**/*.jpg 和 /**/*.png 这种静态资源请求只有 角色为 user 的用户可以访问。
                .antMatchers("/**/*.jpg", "/**/*.png").hasRole("user")
                // 指定 /**/*.jpg 和 /**/*.png 这种静态资源请求只有 拥有img权限的用户可以访问。注意这里 antMatchers 的规则和上面相同就会覆盖。导致上面的规则不生效，即 user 用户无法访问 aa/1.jpg
                .antMatchers("/**/*.jpg", "/**/*.png").hasAuthority("img")
                // 声明任何请求都必须登录才能访问 /index 页面，否则无需登录也可以访问 /index 页面。
                .anyRequest().authenticated();

    }


    /**
     * 登录和认证
     * @param auth
     * @throws Exception
     */
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     // 这里先把账号放到内存中。正常应该从数据库读取。
    //     auth.inMemoryAuthentication()
    //             // 设置加密方式，这里暂时先不设置，先使用明文。
    //             // .passwordEncoder(NoOpPasswordEncoder.getInstance())
    //             // 设置密码加密方式，这里使用 BCryptPasswordEncoder 代替 md5，这种加密比较方便。
    //             .passwordEncoder(new BCryptPasswordEncoder())
    //             // 设置名为 admin 的账号，密码为 123，账号角色是admin。当添加账号之后就不再提供默认的账号和密码，控制台也不会再输出一个自动生成的密码了。
    //             .withUser("admin").password(encryptionConfig.encrypt("123")).roles("admin")
    //             .and()
    //             // 设置名为 user 的账号，密码为 123，账号角色是user。
    //             .withUser("user").password(encryptionConfig.encrypt("123")).roles("user")
    //             .and()
    //             // 设置名为 guest 的账号，密码为 123，赋有 img 的权限。
    //             .withUser("guest").password(encryptionConfig.encrypt("123")).authorities("img");
    // }


    /**
     * 连接数据库认证登录，这里以替换登录和认证章节代码。
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
