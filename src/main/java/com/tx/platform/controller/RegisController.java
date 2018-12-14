package com.tx.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.RedisConstants;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.enums.ResponseCode;
import com.tx.platform.service.RegisterService;
import com.tx.platform.utils.CookiesUtils;
import com.tx.platform.utils.IPUtils;
import com.tx.platform.utils.RegexUtils;
import com.tx.platform.vo.RegisterVO;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName RegisController
 * @Description 注册controller
 * @Author Hardy
 * @Date 2018年12月08日 18:37
 * @Version 1.0.0
 *  
 **/
@RestController
public class RegisController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(RegisController.class);

    @Autowired
    private Validator validator;

    @Value("${cagent}")
    private String defaultCagent;

    @Autowired
    private RegisterService registerService;

    /**
     * 功能描述:
     * 注册
     *
     * @param request
     * @param response
     * @Author: Hardy
     * @Date: 2018年12月13日 10:28:54
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/register")
    public JSONObject register(HttpServletRequest request, HttpServletResponse response, RegisterVO registerVO) {
        logger.info("调用注册用户账号接口开始======================START========================");
        try {
            //验证请求参数
            List<ConstraintViolation> valid = validator.validate(registerVO);
            if (valid != null && !valid.isEmpty()) {
                return ResultResponse.registerFail(ResponseCode.DATA_FAIL.getCode(), valid.get(0).getMessage());
            }
            //重置平台编码
            if (StringUtils.isNotBlank(defaultCagent)) {
                registerVO.setCagent(defaultCagent);
            }
            //获取缓存验证码
            if (ObjectUtils.allNotNull(request.getSession().getAttribute("imgcode"))) {
                String simgcode = (String) request.getSession().getAttribute("imgcode");
                registerVO.setSimgcode(simgcode);
            }

            //获取请求域名
            String referUrl = request.getHeader("referer");
            String registerIp = IPUtils.getIp(request);//请求IP
            registerVO.setReferer(referUrl);
            registerVO.setRegisterIp(registerIp);
            //正则校验请求参数
            JSONObject result = validatorParams(registerVO);
            if (!ResponseCode.SUCCESS.getCode().equals(result.getString("code"))) {
                //校验失败
                return result;
            }
            //调用注册业务
            result = registerService.register(registerVO);
            if (result.getString("code").equals(ResponseCode.SUCCESS.getCode())) {
                //注册成功
                String token = result.getString("token");
                request.getSession().invalidate();//先初始化
                request.getSession().setAttribute("isreg", "true");
                request.getSession().setAttribute(RedisConstants.TT_TOKEN, token);
                CookiesUtils.setCookie(response, RedisConstants.TT_TOKEN, token);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用注册用户账号接口异常:{}", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/getToken")
    public JSONObject getToken(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = new JSONObject();
        data.put("msg", UUID.randomUUID().toString());
        return data;
    }

    /**
     * 功能描述:
     * 校验请求参数
     *
     * @param registerVO
     * @Author: Hardy
     * @Date: 2018年12月13日 11:36:59
     * @return: com.alibaba.fastjson.JSONObject
     **/
    private JSONObject validatorParams(RegisterVO registerVO) {
        //校验用户注册账号
        if (RegexUtils.isMobileExact(registerVO.getUserName())) {
            //如果用手机号码注册,判断用户名和手机号是否一致
            if (!registerVO.getUserName().equals(registerVO.getMobileNo())) {
                return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(), "登录账号和绑定手机号码不一致");
            }
        } else {
            //反之则判断,是否为合法字符串
            if (!RegexUtils.isUsername(registerVO.getUserName())) {
                return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(), "请输入合法用户名,[0-9a-zA-Z]等数字、字母组合5-11位字符串");
            }
        }

        //校验平台编码
        if (!RegexUtils.isCagent(registerVO.getCagent())) {
            return ResultResponse.registerFail(ResponseCode.CAGENT_CODE_FAIL.getCode(), "请输入正确的平台编码");
        }

        if (StringUtils.isNotBlank(registerVO.getReferralCode())) {
            //校验推荐码
            if (!RegexUtils.isReferralcode(registerVO.getReferralCode())) {
                return ResultResponse.registerFail(ResponseCode.REFERRAL_CODE_FAIL.getCode(), "请输入合法的推荐码");
            }
        }

        if (StringUtils.isNotBlank(registerVO.getRealName())) {
            //校验用户真实姓名
            if (!RegexUtils.isRegistRealname(registerVO.getRealName())) {
                return ResultResponse.registerFail(ResponseCode.REALNAME_FAIL.getCode(), "请输入合法的真实姓名");
            }
        }

        //判断注册密码是否一样
        if (!registerVO.getPassWord().equals(registerVO.getRepassWord())) {
            return ResultResponse.registerFail(ResponseCode.PASSWORD_FAIL.getCode(), "输入密码不一致");
        }

        if (StringUtils.isNotBlank(registerVO.getQkpwd()) && StringUtils.isNotBlank(registerVO.getReqkpwd())) {
            //判断取款密码是否一致
            if (registerVO.getQkpwd().length() != 4 || registerVO.getReqkpwd().length() != 4) {
                return ResultResponse.registerFail(ResponseCode.PASSWORD_FAIL.getCode(), "输入长度4位的取款密码");
            }

            if (!registerVO.getQkpwd().equals(registerVO.getReqkpwd())) {
                return ResultResponse.registerFail(ResponseCode.PASSWORD_FAIL.getCode(), "输入取款密码不一致");
            }

        }

        //判断验证码
        if (!"0".equals(registerVO.getIsImgCode())) {
            //需要验证验证码
            if (StringUtils.isEmpty(registerVO.getSimgcode())) {
                return ResultResponse.registerFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(), "请刷星图片验证码");
            }
            if (StringUtils.isEmpty(registerVO.getImgcode())) {
                return ResultResponse.registerFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(), "请输入图形验证码");
            }
            if (!registerVO.getImgcode().equalsIgnoreCase(registerVO.getSimgcode())) {
                return ResultResponse.registerFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(), "输入图形验证码错误");
            }
        }

        return ResultResponse.registerSuccess();
    }
}
