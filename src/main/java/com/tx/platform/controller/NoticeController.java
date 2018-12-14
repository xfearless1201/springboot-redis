package com.tx.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.service.CagentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  @ClassName CommonsController
 *  @Description 平台信息接口
 *  @Author Hardy
 *  @Date 2018年12月12日 12:10
 *  @Version 1.0.0
 *  
 **/
@RestController
public class NoticeController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private CagentService cagentService;

    /**
     * 功能描述:
     * 平台通告
     * @Author: Hardy
     * @Date: 2018年12月12日 12:13:02
     * @param request
     * @param response
     * @return: com.alibaba.fastjson.JSONArray
     **/
    @RequestMapping(value = "/gonggao.do")
    public JSONArray cagentNotice(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "cagent",required = true) String cagent){
        logger.info("调用获取平台通告接口开始=====================START==========================");
        try {
            return cagentService.getCagentNotice(cagent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用获取平台通告接口异常:{}",e.getMessage());
        }
        return null;
    }

    /**
     * 功能描述:
     * 获取网站广告图
     * @Author: Hardy
     * @Date: 2018年12月12日 12:33:25
     * @param request
     * @param response
     * @param cagent
     * @return: com.alibaba.fastjson.JSONArray
     **/
    @RequestMapping(value = "/webcom.do")
    public JSONArray webcom(HttpServletRequest request,HttpServletResponse response,
                            @RequestParam(value = "cagent",required = true) String cagent){

        logger.info("调用获取网站广告图接口开始===========================START=======================");
        try {
            return cagentService.getCagentAdd(cagent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用获取网站广告图接口异常:{}",e.getMessage());
        }
        return null;
    }

}
