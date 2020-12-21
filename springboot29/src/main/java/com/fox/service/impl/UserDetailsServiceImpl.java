package com.fox.service.impl;

import com.fox.pojo.SysUser;
import com.fox.service.SysUserService;
import com.fox.util.SelfUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Date 2020-12-18-9:02
 * @Author fox
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByUsername(username);
        if(null != sysUser) {
            SelfUserDetails selfUserDetails = new SelfUserDetails();
            BeanUtils.copyProperties(sysUser, selfUserDetails);
            return selfUserDetails;
        }
        return null;
    }
}
