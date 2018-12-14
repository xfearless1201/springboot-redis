package com.tx.platform.controller;

import com.tx.platform.service.ValidateCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  @ClassName ValidateCodeController
 *  @Description 生成验证码接口
 *  @Author Hardy
 *  @Date 2018年12月12日 16:05
 *  @Version 1.0.0
 *  
 **/
@RestController
public class ValidateCodeController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 功能描述:
     * 获取验证码
     * @Author: Hardy
     * @Date: 2018年12月12日 15:54:59
     * @param request
     * @param response
     * @return: java.lang.String
     **/
    @RequestMapping(value = "/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response){
        logger.info("调用生成验证码接口开始========================START==========================");
        try {
            // 设置响应的类型格式为图片格式
            response.setContentType("image/jpeg");
            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            HttpSession session = request.getSession();
            String code = validateCodeService.generatorValidateCode(100,30,4,30);
            session.setAttribute("imgcode", code);
            validateCodeService.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用生成验证码接口异常:{}",e.getMessage());
        }
    }
}
