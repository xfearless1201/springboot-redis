package com.tx.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.entity.LoginerrormapEntity;
import com.tx.platform.entity.UserEntity;
import com.tx.platform.entity.UserLoginEntity;
import com.tx.platform.enums.ResponseCode;
import com.tx.platform.mapper.*;
import com.tx.platform.service.LoginService;
import com.tx.platform.service.SessionTokenService;
import com.tx.platform.utils.DESUtils;
import com.tx.platform.utils.RegexUtils;
import com.tx.platform.vo.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  *  @ClassName LoginServiceImpl
 *  *  @Description TODO(这里用一句话描述这个类的作用)
 *  *  @Author Hardy
 *  *  @Date 2018年12月08日 18:27
 *  *  @Version 1.0.0
 *  
 **/
@Service
public class LoginServiceImpl implements LoginService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDissociateMapper userDissociateMapper;

    @Autowired
    private LoginerrormapMapper loginerrormapMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserWalletMapper userWalletMapper;

    @Autowired
    private SessionTokenService sessionTokenService;

    //登录
    @Transactional
    public JSONObject login(LoginVO loginVO) throws Exception {
        logger.info("用户登录开始===================START======================");
        try {
            //判断验证
            if (!"0".equals(loginVO.getIsImgCode())) {
                //需要验证验证码
                if (StringUtils.isEmpty(loginVO.getSimgcode())) {
                    return ResultResponse.loginFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(),"请刷星图片验证码");
                }
                if (StringUtils.isEmpty(loginVO.getImgcode())) {
                    return ResultResponse.loginFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(),"请输入图形验证码");
                }

                if (!loginVO.getImgcode().equalsIgnoreCase(loginVO.getSimgcode())) {
                    return ResultResponse.loginFail(ResponseCode.VERIFICAT_CODE_FAIL.getCode(),"输入图形验证码错误");
                }
            }
            if (StringUtils.isNotBlank(loginVO.getToken())){
                Map<String, String> tokenMap = sessionTokenService.getUserInfoFromRedis(loginVO.getToken());
                if (tokenMap != null) {
                    tokenMap.put("token", loginVO.getToken());
                    return ResultResponse.loginSuccess(tokenMap);
                }
            }
            return processLogin(loginVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("用户登录异常:{}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultResponse.loginFail(ResponseCode.LOGIN_FAIL.getCode(),"登录异常");
        }

    }

    //登出
    public ResultResponse logout(BaseVO baseVO) throws Exception {
        logger.info("调用退出业务接口开始===================START=================");
        try {
            //删除redis中的缓存信息
            sessionTokenService.deleteUserInfoFromRedis(baseVO.getToken());
            return ResultResponse.success(ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用退出业务接口异常:{}",e.getMessage());
            return ResultResponse.error(ResponseCode.LOGOUT_ERROR.getCode(),"调用退出业务接口异常");
        }
    }

    /**
     * 功能描述:
     * 检查登录
     * @Author: Hardy
     * @Date: 2018年12月12日 15:45:00
     * @param token
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @Override
    public JSONObject checkLogin(String token) throws Exception {
        JSONObject result = new JSONObject();
        result.put("msg","faild");
        try {

            if (StringUtils.isBlank(token)){
                result.put("message","会话令牌token失效,请重新登录");
                result.put("code",ResponseCode.TOKEN_FAIL.getCode());
                return result;
            }

            //获取缓存信息
            Map<String,String> redismap = sessionTokenService.getUserInfoFromRedis(token);
            if (CollectionUtils.isEmpty(redismap)){
                result.put("message","会话令牌token失效,请重新登录");
                result.put("code",ResponseCode.TOKEN_FAIL.getCode());
                return result;
            }
            result = JSONObject.parseObject(JSON.toJSONString(redismap));
            result.put("msg","success");
            result.put("message","登录状态");
            result.put("code",ResponseCode.SUCCESS.getCode());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("检查用户登录状态异常:{}",e.getMessage());
            throw new Exception("检查用户登录状态异常");
        }

    }

    /**
     * 功能描述:
     * 检查登录用户
     *
     * @param loginVO
     * @Author: Hardy
     * @Date: 2018年12月11日 16:31:43
     * @return: com.tx.platform.commons.LoginResponse
     **/
    private JSONObject processLogin(LoginVO loginVO) throws Exception {
        String username = loginVO.getTname();//账号
        String cagent = username.substring(0,3);//平台编码
        String password = DESUtils.desEncrypt(loginVO.getTpwd());//用户输入密码
        String account = username;
        //判断用户是否手机号码登录
        int type = 0;//默认为0,账号登录
        if (RegexUtils.isMobileExact(loginVO.getTname())) {
            //手机号码登录用户
            type = 1;
            username = username.replace(cagent, "");
        }

        //查询用户账号锁定状态
        LoginerrormapEntity loginerrormapEntity = loginerrormapMapper.selectByUsername(account);
        if (loginerrormapEntity != null) {
            Calendar calendar = Calendar.getInstance();
            //当前系统时间
            long temptime = calendar.getTimeInMillis();//当前系统毫秒数
            //获取最后登录错误的时间
            Date loginDate = loginerrormapEntity.getLogintime();
            //最后登录错误毫秒数
            calendar.setTime(loginDate);
            long logintime = calendar.getTimeInMillis();//最后登录毫秒数
            int lock_times = loginerrormapEntity.getTimes();
            if ((temptime - logintime) / 1000 < 300 && lock_times >= 5) {
                //锁定5分钟
                return ResultResponse.loginFail(ResponseCode.PASSWORD_FAIL.getCode(),"密码输入错误次超过{" + lock_times + "}次,为了你的账户安全,已被系统锁定");
            } else if ((temptime - logintime) / 1000 < 60 * 60 * 24 && lock_times >= 10) {
                //锁定一天自定解锁
                return ResultResponse.loginFail(ResponseCode.PASSWORD_FAIL.getCode(),"密码输入错误次数超过{" + lock_times + "}次,为了你的账户安全,已被系统锁定");
            } else if ((temptime - logintime) / 1000 > 60 * 60 * 24) {
                //超过一天,自动清零
                loginerrormapEntity.setTimes(0);
                loginerrormapEntity.setLogintime(new Date());
                loginerrormapMapper.updateByPrimaryKeySelective(loginerrormapEntity);
            }
        }

        int isInsertUser = 0;//是否需要插入一条用户信息,默认为0 不需要
        //查询用户信息
        UserEntity userEntity = userMapper.selectByIsMobile(cagent, username, type);
        if (userEntity == null) {
            //查询游离表的是否存在用户
            userEntity = userDissociateMapper.selectByIsMobile(cagent, username, type);
            if (userEntity == null) {
                return ResultResponse.loginFail(ResponseCode.USERNAME_FAIL.getCode(),"登录用户名称错误,请重新输入");
            } else {
                isInsertUser = 1;//需要插入一条用户信息,由于用户长时间为使用,所以把用户信息隔离到游离表
            }
        }
        String sourcePassword = userEntity.getPassword();//用户原始密码
        //判断用户密码是否正常
        if (!password.equals(sourcePassword)) {
            //密码输入错误,需要添加一条
            if (loginerrormapEntity == null) {
                loginerrormapEntity = new LoginerrormapEntity();
                loginerrormapEntity.setUsername(account);
                loginerrormapEntity.setTimes(1);
                loginerrormapEntity.setLogintime(new Date());
                loginerrormapMapper.insertSelective(loginerrormapEntity);
            } else {
                loginerrormapMapper.updateLockTimes(account);
            }
            return ResultResponse.loginFail(ResponseCode.PASSWORD_FAIL.getCode(),"密码输入错误,请重新输入");
        }

        //密码验证成功,登录成功
        //查询用户积分
        Double integral = userWalletMapper.getUserIntegral(userEntity.getUid());
        //写入登录日志
        UserLoginEntity userLoginEntity = new UserLoginEntity();
        userLoginEntity.setUid(userEntity.getUid());
        userLoginEntity.setLoginTime(new Date());
        userLoginEntity.setLoginIp(loginVO.getLoginIp());//代写
        userLoginEntity.setIsLogin((byte) 1);
        userLoginEntity.setStatus("1");
        userLoginEntity.setLoginNum(1);
        userLoginEntity.setIsMobile(StringUtils.isEmpty(loginVO.getIsMobile())?"0":"1");
        userLoginEntity.setAddress(loginVO.getIpAddress());//待写
        userLoginEntity.setRefurl(loginVO.getRefurl().split("/")[2]);
        userLoginMapper.insertSelective(userLoginEntity);
        //更新登录日志
        userEntity.setLoginIp(loginVO.getLoginIp());
        userEntity.setLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(userEntity);
        //查询缓存是否存在用户已登录key
        Map<String,String> data = new HashMap<>();
        data.put("uid",String.valueOf(userEntity.getUid()));
        data.put("username",userEntity.getUsername().replace(userEntity.getCagent(),""));
        data.put("cagent",userEntity.getCagent());
        data.put("cid",String.valueOf(userEntity.getUid()));
        data.put("typeId",String.valueOf(userEntity.getTypeId()));
        data.put("balance",String.valueOf(userEntity.getWallet()));
        data.put("integral", String.valueOf(integral));
        String token = sessionTokenService.addUserInfoToRedis(data);
        data.put("token",token);
        data.put("userKey",token);
        return ResultResponse.loginSuccess(data);
    }
}
