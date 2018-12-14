package com.tx.platform.service;

import java.util.Map;

/**
 * 
 * @ClassName SessionTokenService
 * @Description token
 * @author Hardy
 * @Date 2018年12月10日 下午10:48:19
 * @version 1.0.0
 */
public interface SessionTokenService {
    

    /**
     * 功能描述:
     * 生成token
     * @Author: Hardy
     * @Date: 2018年12月14日 10:43:05
     * @param uid
     * @return: java.lang.String
     **/
    public String generatorToken(String uid) throws Exception;


    /**
     * 功能描述:
     * 添加用户信息到缓存
     * @Author: Hardy
     * @Date: 2018年12月14日 10:09:34
     * @param data
     * @return: java.lang.String
     **/
    public String addUserInfoToRedis(Map<String,String> data) throws Exception;

    /**
     * 功能描述:
     * 从缓存中获取用户详情
     * @Author: Hardy
     * @Date: 2018年12月14日 10:45:30
     * @param key
     * @return: java.util.Map<java.lang.String , java.lang.String>
     **/
    public Map<String,String> getUserInfoFromRedis(String key) throws Exception;


    /**
     * 功能描述:
     * 从缓存中删除用户信息
     * @Author: Hardy
     * @Date: 2018年12月14日 10:55:01
     * @param key
     * @return: void
     **/
    public void deleteUserInfoFromRedis(String key) throws Exception;

}
