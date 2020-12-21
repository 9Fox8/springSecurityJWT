package com.fox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fox.pojo.SysMenu;
import com.fox.pojo.SysRole;
import com.fox.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date 2020-12-17-18:42
 * @Author fox
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_role r left join sys_user_role ur on r.role_id=ur.role_id where ur.user_id=#{userId}")
    List<SysRole> selectSysRolesByUserId(Long userId);

    @Select("select * from sys_user_role ur left join sys_role_menu rm on ur.role_id=rm.role_id left join sys_menu m on rm.menu_id=m.menu_id where user_id=#{userId}")
    List<SysMenu> selectSysMenuByUserId(Long userId);
}
