package com.tx.platform.service.impl;

import com.tx.platform.mapper.JuniorProxyUserMapper;
import com.tx.platform.mapper.ProxyUserMapper;
import com.tx.platform.service.UserProxyService;
import com.tx.platform.vo.ProxyVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  @ClassName ProxyServiceImpl
 *  @Description 代理商接口
 *  @Author Hardy
 *  @Date 2018年12月13日 19:47
 *  @Version 1.0.0
 *  
 **/
@Service
public class UserProxyServiceImpl implements UserProxyService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserProxyServiceImpl.class);

    @Autowired
    private ProxyUserMapper proxyUserMapper;

    @Autowired
    private JuniorProxyUserMapper juniorProxyUserMapper;

    /**
     * 功能描述:
     * 获取注册用户绑定代理商信息
     * @Author: Hardy
     * @Date: 2018年12月13日 20:35:58
     * @param referralCode
     * @param proxyName
     * @return: com.tx.platform.vo.ProxyVO
     **/
    @Override
    public ProxyVO getRegisterBindProxy(String referralCode,String proxyName) throws Exception{
        logger.info("获取用户绑定代理商信息开始===========================START========================");
        ProxyVO proxyVO = new ProxyVO();
        try {
            if (StringUtils.isNotBlank(referralCode) || StringUtils.isNotBlank(proxyName)){
                //一级代理,查询一级代理的信息
                proxyVO = proxyUserMapper.selectProxyByReferralCode(referralCode,proxyName);
                if (proxyVO != null){
                    return proxyVO;
                }
                //二级代理,查询二级代理的信息
                proxyVO = juniorProxyUserMapper.selectProxyByReferralCode(referralCode,proxyName);
                if (proxyVO != null){
                    return proxyVO;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取用户绑定代理商信息异常:{}",e.getMessage());
        }
        return null;
    }
}
