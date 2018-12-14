package com.tx.platform.config;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.RedisConstants;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.enums.ResponseCode;
import com.tx.platform.service.SessionTokenService;
import com.tx.platform.utils.CookiesUtils;
import com.tx.platform.utils.SLLUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 *  @ClassName MySpringMVCConfig
 *  @Description mvc配置类
 *  @Author Hardy
 *  @Date 2018年12月11日 3:47
 *  @Version 1.0.0
 *  
 **/
@Configuration
public class MySpringMVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SessionTokenService sessionTokenService;

    @Value("${excludePath}")
    private String excludePaths;

    public void addInterceptors(InterceptorRegistry registry){

        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                //从请求域名的cookie中获取cookie信息
                Cookie cookie = CookiesUtils.getCookieByName(httpServletRequest, RedisConstants.TT_TOKEN);
                if (cookie == null){
                    //获取cookie失败,重新登录
//                    httpServletResponse.sendRedirect(redirectUrl);
                    printMessage(httpServletResponse,ResponseCode.COOKIE_FAIL.getCode(),"获取cookie失败");
                    return false;
                }
                //获取token
                String token = cookie.getValue();
                if (StringUtils.isEmpty(token)){
                    //token已过期
//                    httpServletResponse.sendRedirect(redirectUrl);
                    printMessage(httpServletResponse,ResponseCode.TOKEN_FAIL.getCode(),"token已过期,请重新登录");
                    return false;
                }

                //通过token校验用户信息
                Map<String,String> data = sessionTokenService.getUserInfoFromRedis(token);
                if (CollectionUtils.isEmpty(data)){
                    //缓存过期
//                    httpServletResponse.sendRedirect(redirectUrl);
                    printMessage(httpServletResponse,ResponseCode.TOKEN_FAIL.getCode(),"token已过期,请重新登录");
                    return false;
                }

                //校验请求参数，防止SLL注入,如果存在非法参数自动过滤
                Enumeration<String> names = httpServletRequest.getParameterNames();
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    String[] values = httpServletRequest.getParameterValues(name);
                    for (String value : values) {
                        value = SLLUtils.clearXss(value);
                    }
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

            }
        };
        //过滤路径
        String[] excludePath = excludePaths.split(",");
        registry.addInterceptor(handlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
    }


    public void printMessage(HttpServletResponse response,String code,String message) throws IOException {
        PrintWriter pw =	response.getWriter();
        ResultResponse resultResponse = new ResultResponse(code,ResultResponse.FAIL_STATUS,message);
        String resposeStr = JSONObject.toJSONString(resultResponse);
        pw.print(resposeStr);
        pw.flush();
        pw.close();
    }

}
