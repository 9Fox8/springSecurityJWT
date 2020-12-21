package com.fox.config;

import com.alibaba.fastjson.JSONObject;
import com.fox.util.SelfUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020-12-18-9:13
 * @Author fox
 */
public class SelfAuthenticationFilter extends BasicAuthenticationFilter {

    public SelfAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获取token
        String token = request.getHeader(JWTConfig.tokenName);

        // token存在，并且前缀正确
        if (StringUtils.isNotBlank(token) && token.startsWith(JWTConfig.tokenPrefix)) {
            // 截取前缀
            token = token.replace(JWTConfig.tokenPrefix, "");
            // 解析JWT
            Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
            // 获取用户
            String username = claims.getSubject();
            String userId = claims.getId();
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(userId)) {
                // 获取角色信息
                String authority = claims.get("authorities").toString();
                if (StringUtils.isNotBlank(authority)) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    @SuppressWarnings("unchecked")
                    List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, ArrayList.class);
                    for (Map<String, String> role : authorityMap) {
                        if (StringUtils.isNotBlank(role.get("authority"))) {
                            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                        }
                    }
                    // 设置Authentication
                    SelfUserDetails selfUserDetails = new SelfUserDetails();
                    selfUserDetails.setUsername(username);
                    selfUserDetails.setUserId(Long.parseLong(userId));
                    selfUserDetails.setAuthorities(authorities);
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(selfUserDetails, userId, authorities));
                }
            }
        }

        chain.doFilter(request, response);
    }
}
