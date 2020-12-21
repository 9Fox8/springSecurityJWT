package com.fox.util;

import com.alibaba.fastjson.JSON;
import com.fox.config.JWTConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Date 2020-12-18-9:18
 * @Author fox
 */
public class JWTUtil {
    /**
     * @Description 私有化构造器
     */
    private JWTUtil() {
    }

    /**
     *
     * @Description 生成token
     *
     * @param selfUserDetails 用户实体
     * @return token
     */
    public static String createToken(SelfUserDetails selfUserDetails) {
        String token = Jwts.builder()
                .setId(String.valueOf(selfUserDetails.getUserId()))
                .setSubject(selfUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setIssuer("")
                .claim("authorities", JSON.toJSONString(selfUserDetails.getAuthorities()))
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expirationTime * 1000))
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
}
