package com.fox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.mapper.SysRoleMapper;
import com.fox.mapper.SysUserMapper;
import com.fox.pojo.SysMenu;
import com.fox.pojo.SysRole;
import com.fox.pojo.SysUser;
import com.fox.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020-12-17-18:46
 * @Author fox
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser selectUserByUsername(String username) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.lambda().eq(SysUser::getUsername, username);
        return sysUserMapper.selectOne(sysUserQueryWrapper);
    }

    @Override
    public List<SysRole> selectSysRolesByUserId(Long userId) {
        List<SysRole> roles = sysUserMapper.selectSysRolesByUserId(userId);
        return roles;
    }

    @Override
    public List<SysMenu> selectSysMenuByUserId(Long userId) {
        List<SysMenu> sysMenus = sysUserMapper.selectSysMenuByUserId(userId);
        return sysMenus;
    }
}
