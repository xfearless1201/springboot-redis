package com.tx.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.vo.RegisterVO;

/**
 *  @ClassName RegisterService
 *  @Description 注册接口
 *  @Author Hardy
 *  @Date 2018年12月13日 12:00
 *  @Version 1.0.0
 *  
 **/
public interface RegisterService {

    /**
     * 功能描述:
     * 注册
     * @Author: Hardy
     * @Date: 2018年12月13日 12:01:49
     * @param registerVO
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject register(RegisterVO registerVO) throws Exception;
}
