package com.fox.config;

import com.fox.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2020-12-22-下午 04:03
 * @Author fox
 */
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        String username = sysUser.getUsername();
        log.info("username: {} is offline now",username);
    }
}
