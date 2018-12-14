package com.tx.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.ResultResponse;
import com.tx.platform.entity.*;
import com.tx.platform.enums.ResponseCode;
import com.tx.platform.mapper.*;
import com.tx.platform.service.RegisterService;
import com.tx.platform.service.SessionTokenService;
import com.tx.platform.service.UserProxyService;
import com.tx.platform.utils.DESUtils;
import com.tx.platform.vo.ProxyVO;
import com.tx.platform.vo.RegisterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 *  @ClassName RegisterServiceImpl
 *  @Description 注册接口
 *  @Author Hardy
 *  @Date 2018年12月13日 12:00
 *  @Version 1.0.0
 *  
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private SessionTokenService sessionTokenService;

    @Autowired
    private UserProxyService userProxyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CagentMapper cagentMapper;

    @Autowired
    private UserDissociateMapper userDissociateMapper;

    @Autowired
    private ReserveAccountMapper reserveAccountMapper;

    @Autowired
    private UserTypeMapper userTypeMapper;

    @Autowired
    private UserQuantityMapper userQuantityMapper;

    @Autowired
    private UserWalletMapper userWalletMapper;

    @Override
    @Transactional
    public JSONObject register(RegisterVO registerVO) throws Exception {
        logger.info("调用注册用户业务开始======================START====================");
        try {

            //查询用户名是否已存在
            String username = registerVO.getCagent()+registerVO.getUserName();//用户名
            String cagent = registerVO.getCagent();//平台编码
            String mobileNo = registerVO.getMobileNo();//手机号码
            UserEntity entity = userMapper.selectByUsername(username);
            if (entity != null){
                return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(),"用户名已存在,请重新输入");
            }else{
                //判断手机号码是否绑定过
                entity = userMapper.selectByloginmobileAndCagent(mobileNo,cagent);
                if (entity != null){
                    return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(),"用户名已存在,请重新输入");
                }
            }
            //查询游离表是否存在用户名
            entity = userDissociateMapper.selectByUsername(username);
            if (entity != null){
                return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(),"用户名已存在,请重新输入");
            }else {
                entity = userDissociateMapper.selectDisUserByMobileNo(mobileNo,cagent);
                if (entity != null){
                    return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(),"用户名已存在,请重新输入");
                }
            }

            //判断用户是否为系统保留账号
            ReserveAccountEntity reserveAccountEntity = reserveAccountMapper.selectByUsernameAndCagent(username,cagent);
            if (reserveAccountEntity != null){
                return ResultResponse.registerFail(ResponseCode.USERNAME_FAIL.getCode(),"用户名已存在,请重新输入");
            }

            //更新代理商
            ProxyVO proxyVO = userProxyService.getRegisterBindProxy(registerVO.getReferralCode(),registerVO.getProxyname());
            if (proxyVO == null){
                //平台商会员
                registerVO.setProxyId(0);
                registerVO.setTopUid(0);
                //查询默认分层ID
                int typeId = userTypeMapper.selectTypeIdByCagent(registerVO.getCagent().toLowerCase());
                registerVO.setTypeId(String.valueOf(typeId));
            }else {
                registerVO.setProxyId(proxyVO.getProxyId());
                registerVO.setTopUid(proxyVO.getTopUid());
                registerVO.setTypeId(proxyVO.getTypeId());
            }

            //获取平台ID
            CagentEntity cagentEntity = cagentMapper.selectByCagent(registerVO.getCagent());
            registerVO.setCid(cagentEntity.getId());
            registerVO.setLoginmobile(mobileNo);
            //写入用户信息
            insertUserInfo(registerVO);
            //查询用户信息
            UserEntity userEntity = userMapper.selectByUsername(username);
            //写入缓存
            Map<String,String> data = new HashMap<>();
            data.put("uid",String.valueOf(userEntity.getUid()));
            data.put("username",userEntity.getUsername().replace(userEntity.getCagent(),""));
            data.put("cagent",userEntity.getCagent());
            data.put("cid",String.valueOf(userEntity.getUid()));
            data.put("typeId",String.valueOf(userEntity.getTypeId()));
            data.put("balance",String.valueOf(userEntity.getWallet()));
            data.put("integral", "0.00");
            String token = sessionTokenService.addUserInfoToRedis(data);
            data.put("token",token);
            data.put("userKey",token);
            return ResultResponse.registerSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用注册业务异常:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultResponse.registerFail(ResponseCode.REGIS_ERROR.getCode(),"注册用户异常");
        }

    }

    /**
     * 功能描述:
     * 写入用户信息
     * @Author: Hardy
     * @Date: 2018年12月13日 20:45:09
     * @param registerVO
     * @return: void
     **/
    private void insertUserInfo(RegisterVO registerVO) throws Exception{
        //写入用户信息
        UserEntity userEntity = new UserEntity();

        String username = registerVO.getUserName();
        String cagent = registerVO.getCagent();
        StringBuilder str = new StringBuilder();
        str.append(cagent);
        Random randoms = new Random();
        // 随机生成数字，并添加到字符串
        int max = 999999;
        int min = 100000;
        int ss = randoms.nextInt(max) % (max - min + 1) + min;
        str.append(ss);
        String gamepassword = str.toString();
        if (gamepassword.length() > 9) {
            gamepassword = gamepassword.substring(0, 9);
        }
        //游戏帐号超长截取
        String gameusername = (cagent + username).toLowerCase();
        userEntity.setUsername(registerVO.getCagent()+registerVO.getUserName());//登录名称
        String password = DESUtils.desEncrypt(registerVO.getPassWord());
        userEntity.setPassword(password);//登录密码
        userEntity.setRegIp(registerVO.getRegisterIp());
        userEntity.setLoginIp(registerVO.getRegisterIp());
        userEntity.setAgUsername(gameusername);
        userEntity.setHgUsername(gameusername);
        userEntity.setMgUsername(gameusername);
        userEntity.setAgPassword(gamepassword);
        userEntity.setEmail("");
        userEntity.setVipLevel("1");
        userEntity.setMobile(registerVO.getMobileNo());
        userEntity.setCagent(registerVO.getCagent());
        userEntity.setIsDaili("0");
        userEntity.setIsDelete("0");
        userEntity.setQkPwd(StringUtils.isEmpty(registerVO.getQkpwd())?"":registerVO.getQkpwd());
        userEntity.setRegDate(new Date());
        userEntity.setLoginTime(new Date());
        userEntity.setWallet(0.00);
        userEntity.setIsStop("0");
        userEntity.setIsMobile(registerVO.getIsMobile());
        userEntity.setRealname(registerVO.getRealName());
        userEntity.setTypeId(Integer.parseInt(registerVO.getTypeId()));
        userEntity.setTopUid(registerVO.getTopUid());
        userEntity.setJuniorUid(registerVO.getProxyId());//二级代理UID
        userEntity.setRegurl(registerVO.getReferer());//注册域名
        userEntity.setLoginmobile(registerVO.getLoginmobile());
        userEntity.setRmk(registerVO.getRemark());
        userMapper.insertSelective(userEntity);
        int uid = userEntity.getUid();
        if (uid > 0){
            //写入会员打码量
            UserQuantityEntity userQuantityEntity = new UserQuantityEntity();
            userQuantityEntity.setMarkingQuantity(0.00);
            userQuantityEntity.setUserQuantity(0.00);
            userQuantityEntity.setUserWinamount(0.00);
            userQuantityEntity.setWinamount(0.00);
            userQuantityEntity.setUid(uid);
            userQuantityEntity.setCid(registerVO.getCid());
            userQuantityMapper.insertSelective(userQuantityEntity);
            //写入用户钱包信息
            UserWalletEntity userWalletEntity = new UserWalletEntity();
            userWalletEntity.setBalance(0.00);
            userWalletEntity.setFrozenBalance(0.00);
            userWalletEntity.setType("1");
            userWalletEntity.setUptime(new Date());
            userWalletEntity.setUid(uid);//用户ID
            userWalletMapper.insertSelective(userWalletEntity);
        }
    }
}
