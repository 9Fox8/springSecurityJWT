package com.fox.config;

import com.fox.util.JWTUtil;
import com.fox.util.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.PrintWriter;

/**
 * @Date 2020-12-17-18:21
 * @Author fox
 */
@Configuration
//开启security
@EnableWebSecurity
//保证post之前的请求可以使用
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SelfAuthenticationProvider selfAuthenticationProvider;

    /**
     * 加密方式
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用自定义的登陆验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(selfAuthenticationProvider);
    }

    /**
     * 配置控制逻辑
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不进行验证的资源地址
                .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
                // 其他都需要验证
                .anyRequest().authenticated().and()
                .formLogin()
                // 配置登陆地址
//				.loginPage("login.html")
                // 配置登陆接口地址
                .loginProcessingUrl("/login/userLogin")
                // 请求成功处理
                .successHandler((request, response, authentication) -> {
                    SelfUserDetails selfUserDetails = (SelfUserDetails) authentication.getPrincipal();
                    String token = JWTConfig.tokenPrefix + JWTUtil.createToken(selfUserDetails);
                    System.out.println("登陆成功：token=" + token);
                    PrintWriter out = null;
                    try {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
                        out = response.getWriter();
                        out.println(token);
                    } finally {
                        if (null != out) {
                            out.flush();
                            out.close();
                        }
                    }
                })
                //.successForwardUrl("/hi")
                .failureHandler((request, response, exception) -> {
                    System.out.println(exception);
                    PrintWriter out = null;
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    out = response.getWriter();
                    out.println(exception);
                })
                .and()
                .logout()
                // 配置登出地址
                .logoutUrl("/logout").and().cors().and().csrf();
        // 不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加自定义过滤器
        http.addFilter(new SelfAuthenticationFilter(authenticationManager()));
    }
}
