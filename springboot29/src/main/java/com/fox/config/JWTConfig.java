package com.fox.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2020-12-18-9:11
 * @Author fox
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    /**
     * 密钥
     */
    public static String secret;

    /**
     * 名称
     */
    public static String tokenName;

    /**
     * 前缀
     */
    public static String tokenPrefix;

    /**
     * 过期时间 : 单位秒
     */
    public static Long expirationTime;

    /**
     * 不需要认证的接口
     */
    public static String antMatchers;

    public void setSecret(String secret) {
        JWTConfig.secret = secret;
    }

    public void setTokenName(String tokenName) {
        JWTConfig.tokenName = tokenName;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JWTConfig.tokenPrefix = tokenPrefix;
    }

    public void setExpirationTime(Long expirationTime) {
        JWTConfig.expirationTime = expirationTime;
    }

    public void setAntMatchers(String antMatchers) {
        JWTConfig.antMatchers = antMatchers;
    }
}
