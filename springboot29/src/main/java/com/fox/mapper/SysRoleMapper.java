package com.fox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fox.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date 2020-12-17-18:42
 * @Author fox
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
