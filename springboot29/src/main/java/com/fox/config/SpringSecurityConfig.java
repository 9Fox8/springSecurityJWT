package com.fox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Date 2020-12-17-18:21
 * @Author fox
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SelfAuthenticationProvider selfAuthenticationProvider;
    /**
     * 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
                .loginProcessingUrl("/process")
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure")
                .and()
                .logout()
                // 配置登出地址
                .addLogoutHandler(new CustomLogoutHandler()).logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .logoutUrl("/logout").and().cors().and().csrf();
        // 不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加自定义过滤器
        http.addFilter(new SelfAuthenticationFilter(authenticationManager()));
    }

}
