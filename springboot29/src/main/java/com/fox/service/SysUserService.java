package com.fox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.mapper.SysUserMapper;
import com.fox.pojo.SysMenu;
import com.fox.pojo.SysRole;
import com.fox.pojo.SysUser;

import java.util.List;

/**
 * @Date 2020-12-17-18:45
 * @Author fox
 */
public interface SysUserService extends IService<SysUser> {
    SysUser selectUserByUsername(String username);

    List<SysRole> selectSysRolesByUserId(Long userId);

    List<SysMenu> selectSysMenuByUserId(Long userId);
}
