package com.fox.controller;

import com.fox.pojo.SysUser;
import com.fox.service.SysUserService;
import com.fox.util.SelfUserDetails;
import com.fox.vo.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020-12-22-下午 02:42
 * @Author fox
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录失败返回401以及提示信息
     */
    @PostMapping("/failure")
    public ResultUtil loginFailure(){
        return ResultUtil.error("登录失败", 401,"");
    }

    @PostMapping("/success")
    public ResultUtil loginSuccess(){
        //登录成功后用户的认证信息，userDetails会存放在安全上下文寄存器中
        SelfUserDetails userDetails = (SelfUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();
        SysUser sysUser = sysUserService.selectUserByUsername(username);
        return ResultUtil.sucess("登陆成功", sysUser);

    }
}
