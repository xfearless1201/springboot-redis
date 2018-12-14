package com.tx.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.entity.UserEntity;
import com.tx.platform.vo.StandLetterVO;

/**
 *  * @ClassName UserService
 *  * @Description 用户接口
 *  * @Author Hardy
 *  * @Date 2018年12月08日 17:19
 *  * @Version 1.0.0
 *  
 **/
public interface UserService {

    /**
     * 功能描述:
     * 根据主键删除一条信息
     * @Author: Hardy
     * @Date: 2018年12月08日 17:21:32
     * @param uid
     * @return: int
     **/
    public int deleteByPrimaryKey(Integer uid);

    /**
     * 功能描述:
     * 插入一条用户信息
     * @Author: Hardy
     * @Date: 2018年12月08日 17:23:13
     * @param record
     * @return: int
     **/
    public int insert(UserEntity record);

    /**
     * 功能描述:
     * 插入一条用户信息,判断字段是否为空
     * @Author: Hardy
     * @Date: 2018年12月08日 17:23:22
     * @param record
     * @return: int
     **/
    public int insertSelective(UserEntity record);

    /**
     * 功能描述:
     * 根据UID查询用户信息
     * @Author: Hardy
     * @Date: 2018年12月08日 17:23:49
     * @param uid
     * @return: com.tx.platform.entity.UserEntity
     **/
    public UserEntity selectByPrimaryKey(Integer uid);

    /**
     * 功能描述:
     * 更新一条用户信息,不为空的字段
     * @Author: Hardy
     * @Date: 2018年12月08日 17:24:00
     * @param record
     * @return: int
     **/
    public int updateByPrimaryKeySelective(UserEntity record);

    /**
     * 功能描述:
     * 跟新一条用户信息
     * @Author: Hardy
     * @Date: 2018年12月08日 17:24:26
     * @param record
     * @return: int
     **/
    public int updateByPrimaryKey(UserEntity record);

    /**
     * 功能描述:
     * 根据用户名查询一条用户信息
     * @Author: Hardy
     * @Date: 2018年12月08日 17:25:06
     * @param username
     * @return: com.tx.platform.entity.UserEntity
     **/
    public UserEntity selectByUsername(String username);


    /**
     * 功能描述:
     * 获取用户信息
     * @Author: Hardy
     * @Date: 2018年12月12日 14:40:42
     * @param baseVO
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject getUserInfo(BaseVO baseVO)throws Exception;

    /**
     * 功能描述:
     * 获取站内信
     * @Author: Hardy
     * @Date: 2018年12月12日 14:40:33
     * @param standLetterVO
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject getMessageNum(StandLetterVO standLetterVO) throws Exception;

}
