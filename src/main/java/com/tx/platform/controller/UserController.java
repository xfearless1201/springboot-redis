package com.tx.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.commons.RedisConstants;
import com.tx.platform.service.UserService;
import com.tx.platform.utils.CookiesUtils;
import com.tx.platform.vo.StandLetterVO;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName UserController
 * @Description 用户controller
 * @author Hardy
 * @Date 2018年12月10日 下午9:19:34
 * @version 1.0.0
 */
@RestController
@RequestMapping("/User")
public class UserController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    /**
     * 功能描述:
     * 获取用户信息
     * @Author: Hardy
     * @Date: 2018年12月12日 11:26:58
     * @param request
     * @param response
     * @param baseVO
     * @return: com.tx.platform.entity.UserEntity
     **/
    @RequestMapping(value="/getUserInfo")
    @ResponseBody
    public JSONObject getUserInfo(HttpServletRequest request, HttpServletResponse response, BaseVO baseVO){
        logger.info("调用查询用户详情接口开始======================START========================");
        JSONObject data = new JSONObject();
        try {
            //从cookie获取身份令牌token
            String token = CookiesUtils.getToken(request, RedisConstants.TT_TOKEN);
            baseVO.setToken(token);
            data = userService.getUserInfo(baseVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用查询用户详情接口异常:{}",e.getMessage());
        }
        return data;
    }

    /**
     * 功能描述:
     * 获取站内信数量
     * @Author: Hardy
     * @Date: 2018年12月12日 12:49:41
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/getMessageNum")
    public JSONObject getMessageNum(HttpServletRequest request, HttpServletResponse response, StandLetterVO standLetterVO){
        logger.info("调用查询用户站内信接口开始================================START===================");
        try {
            //获取用户token
            String token = CookiesUtils.getToken(request,RedisConstants.TT_TOKEN);
            standLetterVO.setToken(token);
            return userService.getMessageNum(standLetterVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用查询用户站内信接口异常:{}",e.getMessage());
            return null;
        }
    }
}
