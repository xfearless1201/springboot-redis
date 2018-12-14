package com.tx.platform.commons;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.enums.ResponseCode;
import org.apache.commons.lang3.ObjectUtils;

/**
 *  *  @ClassName ResultResponse
 *  *  @Description 返回封装类
 *  *  @Author Hardy
 *  *  @Date 2018年12月08日 14:59
 *  *  @Version 1.0.0
 *  
 **/
public class ResultResponse {

    public static final String SUCCESS_STATUS="SUCCESS";

    public static final String FAIL_STATUS="FAIL";

    public static final String ERROR_STATUS="ERROR";

    private String code;//状态码

    private String status;//状态

    private String message;//返回信息

    private Object data;//封装数据

    public ResultResponse(String code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public ResultResponse(String code, String status, String message, Object data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultResponse success(String message){
        return new ResultResponse(ResponseCode.SUCCESS.getCode(),SUCCESS_STATUS,message);
    }

    public static ResultResponse success(String message,Object data){
        return new ResultResponse(ResponseCode.SUCCESS.getCode(),SUCCESS_STATUS,message,data);
    }

    public static ResultResponse fail(String code,String message){
        return new ResultResponse(code,FAIL_STATUS,message);
    }

    public static ResultResponse fail(String code,String message,Object data){
        return new ResultResponse(code,FAIL_STATUS,message,data);
    }

    public static ResultResponse error(String code,String message){
        return new ResultResponse(code,ERROR_STATUS,message);
    }

    public static ResultResponse error(String code,String message,Object data){
        return new ResultResponse(code,ERROR_STATUS,message,data);
    }


    /**
     * 功能描述:
     * 登录失败
     * @Author: Hardy
     * @Date: 2018年12月13日 11:18:14
     * @param obj
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject loginSuccess(Object obj){
        JSONObject data = new JSONObject();
        if (ObjectUtils.allNotNull(obj)){
            data = JSONObject.parseObject(JSONObject.toJSONString(obj));
        }
        data.put("status","ok");
        data.put("errmsg","登录成功");
        data.put("code",ResponseCode.SUCCESS.getCode());
        return data;
    }

    /**
     * 功能描述:
     * 登录失败
     * @Author: Hardy
     * @Date: 2018年12月13日 11:18:04
     * @param code
     * @param message
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject loginFail(String code,String message){
        JSONObject data = new JSONObject();
        data.put("status","faild");
        data.put("errmsg",message);
        data.put("code",code);
        return data;
    }


    /**
     * 功能描述:
     * 注册成功
     * @Author: Hardy
     * @Date: 2018年12月13日 11:17:56
     * @param object
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject registerSuccess(Object object){
        //注册成功
        JSONObject data = new JSONObject();
        if (ObjectUtils.allNotNull(object)){
            data = JSONObject.parseObject(JSONObject.toJSONString(object));
        }
        data.put("msg","success");
        data.put("errmsg","注册成功");
        data.put("code",ResponseCode.SUCCESS.getCode());
        return data;
    }

    public static JSONObject registerSuccess(){
        //注册成功
        JSONObject data = new JSONObject();
        data.put("msg","success");
        data.put("code",ResponseCode.SUCCESS.getCode());
        return data;
    }

    /**
     * 功能描述:
     * 注册失败
     * @Author: Hardy
     * @Date: 2018年12月13日 11:17:46
     * @param code
     * @param message
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject registerFail(String code,String message){
        JSONObject data = new JSONObject();
        data.put("msg",message);
        data.put("code",code);
        return data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
