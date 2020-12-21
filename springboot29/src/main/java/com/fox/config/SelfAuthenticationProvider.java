package com.fox.config;

import com.alibaba.druid.util.StringUtils;
import com.fox.pojo.SysRole;
import com.fox.service.SysUserService;
import com.fox.service.impl.SelfUserDetailsService;
import com.fox.util.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义登录验证
 * @Date 2020-12-17-18:31
 * @Author fox
 */
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户名
        String username = String.valueOf(authentication.getPrincipal());
        // 密码
        String password = String.valueOf(authentication.getCredentials());

        // 获取用户
        SelfUserDetails selfUserDetails = selfUserDetailsService.loadUserByUsername(username);

        // 用户不存在
        if (null == selfUserDetails) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 密码不正确
        if (!new BCryptPasswordEncoder().matches(password, selfUserDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 用户已被冻结
        if (StringUtils.equals("PROHIBIT", selfUserDetails.getStatus())) {
            throw new LockedException("该用户已被冻结");
        }

        // 验证通过，查询用户角色
        List<SysRole> sysRoles = sysUserService.selectSysRolesByUserId(selfUserDetails.getUserId());

        // 将角色设置给自定义的UserDetails
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (null != sysRoles && sysRoles.size() > 0) {
            for (SysRole sysRole : sysRoles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleName()));
            }
        }
        selfUserDetails.setAuthorities(authorities);

        // 登录管理
        return new UsernamePasswordAuthenticationToken(selfUserDetails, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
