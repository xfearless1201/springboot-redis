package com.tx.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.commons.RedisConstants;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.enums.ResponseCode;
import com.tx.platform.service.LoginService;
import com.tx.platform.utils.CookiesUtils;
import com.tx.platform.utils.IPUtils;
import com.tx.platform.utils.RegexUtils;
import com.tx.platform.vo.LoginVO;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  @ClassName LoginController
 *  @Description 登录controller
 *  @Author Hardy
 *  @Date 2018年12月08日 14:37
 *  @Version 1.0.0
 *  
 **/
@Controller
public class LoginController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private Validator validator;


    /**
     * 功能描述:
     * 跳转页面
     * @Author: Hardy
     * @Date: 2018年12月11日 21:00:30
     * @param
     * @return: java.lang.String
     **/
    @RequestMapping(value = "/")
    public String root(){
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/index.html")
    public String index() {
        return "index";
    }

    /**
     * 功能描述:
     * 登录
     * @Author: Hardy
     * @Date: 2018年12月08日 17:40:28
     * @param request
     * @param response
     * @param loginVO
     * @return: com.tx.platform.commons.LoginResponse
     **/
    @RequestMapping(value="/login.do",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO){
        logger.info("调用登录接口开始================START======================");
        try {
            //验证请求参数
            List<ConstraintViolation> valid = validator.validate(loginVO);
            if(valid != null && !valid.isEmpty()){
                return ResultResponse.loginFail(ResponseCode.DATA_FAIL.getCode(),valid.get(0).getMessage());
            }
            //判断用户名是否合法
            if(!RegexUtils.isAccount(loginVO.getTname())){
                return ResultResponse.loginFail(ResponseCode.DATA_FAIL.getCode(),"非法用户名,请输入6-11位字母和数字组合串");
            }
            //获取缓存中的
            String simgcode = (String) request.getSession().getAttribute("imgcode");
            //验证来源域名
            String refurl=request.getHeader("referer");
            //登录IP
            String loginIp = IPUtils.getIp(request);
            String ipAddress = IPUtils.getIpAddress(request);
            loginVO.setLoginIp(loginIp);
            loginVO.setRefurl(refurl);
            loginVO.setSimgcode(simgcode);
            loginVO.setIpAddress(ipAddress);
            String token = CookiesUtils.getToken(request,RedisConstants.TT_TOKEN);
            if (StringUtils.isNotBlank(token)){
                loginVO.setToken(token);
            }
            JSONObject resultResponse = loginService.login(loginVO);
            if ("ok".equalsIgnoreCase(resultResponse.get("status").toString())){
                //登录成功
                token = resultResponse.get("token").toString();
                request.getSession().setAttribute(RedisConstants.TT_TOKEN,token);
                request.getSession().setAttribute("isreg", "");
                request.getSession().setAttribute("imgcode", "");
                //保存cookie
                CookiesUtils.setCookie(response,RedisConstants.TT_TOKEN,token);
            }
            return resultResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用登录接口异常:{}",e.getMessage());
            return ResultResponse.loginFail(ResponseCode.LOGIN_FAIL.getCode(),"登录异常");
        }
    }

    /**
     * 功能描述:
     * 退出登录
     * @Author: Hardy
     * @Date: 2018年12月11日 20:12:58
     * @param baseVO
     * @return: com.tx.platform.commons.ResultResponse
     **/
    @RequestMapping(value = "/logout.do")
    @ResponseBody
    public ResultResponse logout(HttpServletRequest request,HttpServletResponse response,BaseVO baseVO){
        logger.info("调用退出接口开始=======================start======================");
        try {
            //验证请求数据
            List<ConstraintViolation> valid = validator.validate(baseVO);
            if (valid != null && !valid.isEmpty()){
                return ResultResponse.fail(ResponseCode.DATA_FAIL.getCode(),valid.get(0).getMessage());
            }
            String token = CookiesUtils.getToken(request,RedisConstants.TT_TOKEN);
            baseVO.setToken(token);
            ResultResponse resultResponse = loginService.logout(baseVO);
            if (resultResponse.getStatus().equals(ResultResponse.SUCCESS_STATUS)){
                //退出成功,删除缓存
                CookiesUtils.delectCookieByName(request,response,RedisConstants.TT_TOKEN);
                request.getSession().invalidate();
            }
            return resultResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用退出接口异常:{}",e.getMessage());
            return ResultResponse.error(ResponseCode.LOGOUT_ERROR.getCode(),"退出异常");
        }
    }

    /**
     * 功能描述:
     * 检查登录状态
     * @Author: Hardy
     * @Date: 2018年12月12日 11:56:17
     * @param request
     * @param response
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/checklogin.do")
    @ResponseBody
    public JSONObject checkLogin(HttpServletRequest request,HttpServletResponse response){
        logger.info("调用检查用户登录状态接口开始======================START=======================");
        JSONObject result = new JSONObject();
        try {
            //获取token
            String token = CookiesUtils.getToken(request, RedisConstants.TT_TOKEN);
            result = loginService.checkLogin(token);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用检查用户登录状态异常:{}",e.getMessage());
            result.put("msg","faild");
            result.put("message","检查用户登录状态异常");
            result.put("code",ResponseCode.LOGIN_ERROR.getCode());
        }

        return result;
    }

}
