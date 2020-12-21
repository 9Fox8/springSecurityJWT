package com.fox.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Date 2020-12-17-18:39
 * @Author fox
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 权限ID
     */
    @TableId
    private Long menuId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;
}
