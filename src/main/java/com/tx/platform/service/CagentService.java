package com.tx.platform.service;

import com.alibaba.fastjson.JSONArray;

/**
 *  @ClassName CagentService
 *  @Description 平台接口
 *  @Author Hardy
 *  @Date 2018年12月08日 17:25
 *  @Version 1.0.0
 *  
 **/
public interface CagentService {

    /**
     * 功能描述:
     * 通过平台编码获取平台通告
     * @Author: Hardy
     * @Date: 2018年12月12日 12:20:47
     * @param cagent
     * @return: com.alibaba.fastjson.JSONArray
     **/
    public JSONArray getCagentNotice(String cagent) throws Exception;

    /**
     * 功能描述:
     * 获取网站广告图
     * @Author: Hardy
     * @Date: 2018年12月12日 12:37:00
     * @param cagent
     * @return: com.alibaba.fastjson.JSONArray
     **/
    public JSONArray getCagentAdd(String cagent) throws Exception;

}
