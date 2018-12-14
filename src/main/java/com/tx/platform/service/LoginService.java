package com.tx.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.vo.LoginVO;

/**
 *  * @ClassName LoginService
 *  * @Description 登录接口
 *  * @Author Hardy
 *  * @Date 2018年12月08日 18:27
 *  * @Version 1.0.0
 *  
 **/
public interface LoginService {

    //登录
    JSONObject login(LoginVO loginVO) throws Exception;


    //登出
    ResultResponse logout(BaseVO baseVO) throws Exception;


    /**
     * 功能描述:
     * 检查是否登录
     * @Author: Hardy
     * @Date: 2018年12月12日 15:44:44
     * @param token
     * @return: com.alibaba.fastjson.JSONObject
     **/
    JSONObject checkLogin(String token) throws Exception;
}
