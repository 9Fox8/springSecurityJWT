package com.fox.service;

import com.fox.util.LoginTypeEnum;

import javax.servlet.ServletRequest;

/**
 * @Date 2020-12-22-下午 03:04
 * @Author fox
 */
public interface LoginPostProcessor {
    /**
     * 获取登录类型
     */
    LoginTypeEnum getLoginTypeEnum();
    /**
     * 获取用户名
     */
    String obtainUsername(ServletRequest request);
    /**
     * 获取密码
     */
    String obtainPassword(ServletRequest request);
}
