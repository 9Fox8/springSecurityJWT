package com.fox.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxiaomeng
 * @date 2017/12/15.
 * @email 154040976@qq.com
 * ajax 回执
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil {

    @ApiModelProperty("成功标识")
    private boolean flag = true;
    @ApiModelProperty("提示信息")
    private String msg;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("其他数据")
    private Object data;
    @ApiModelProperty("json数据")
    private JSONObject josnObj;

    /**
     * restful 返回
     */
    public static ResultUtil error(String msg) {
        return new ResultUtil(false, msg, 500, null, null);
    }

    public static ResultUtil error(String msg, Object data) {
        return new ResultUtil(false, msg, 500, data, null);
    }

    public static ResultUtil error(String msg, JSONObject jsonObject) {
        return new ResultUtil(false, msg, 500, null, jsonObject);
    }

    public static ResultUtil error(String msg, Object data, JSONObject jsonObject) {
        return new ResultUtil(false, msg, 200, data, jsonObject);
    }

    public static ResultUtil error(String msg,Integer status,Object data){
        return new ResultUtil(false,msg,status,data,null);
    }

    public static ResultUtil sucess(String msg) {
        return new ResultUtil(true, msg, 200, null, null);
    }

    public static ResultUtil sucess(String msg, Object data) {
        return new ResultUtil(true, msg, 200, data, null);
    }

    public static ResultUtil sucess(String msg, JSONObject jsonObject) {
        return new ResultUtil(true, msg, 200, null, jsonObject);
    }

    public static ResultUtil sucess(String msg, Object data, JSONObject jsonObject) {
        return new ResultUtil(true, msg, 200, data, jsonObject);
    }
}
