package com.fox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fox.mapper.SysUserMapper;
import com.fox.pojo.SysUser;
import com.fox.vo.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020-12-07-17:31
 * @Author fox
 */
@RestController
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    // 所有用户都可以访问
    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    // ADMIN用户都可以访问
    @GetMapping("/adminhi")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHi() {
        return "admin: hi";
    }

    // USER用户都可以访问
    @GetMapping("/userhi")
    @PreAuthorize("hasRole('USER')")
    public String userHi() {
        return "user: hi";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
