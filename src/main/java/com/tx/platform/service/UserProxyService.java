package com.tx.platform.service;

import com.tx.platform.vo.ProxyVO;

/**
 *  @ClassName ProxyService
 *  @Description 代理商接口
 *  @Author Hardy
 *  @Date 2018年12月13日 19:42
 *  @Version 1.0.0
 *  
 **/
public interface UserProxyService {

    /**
     * 功能描述:
     * 获取注册会员绑定代理商信息
     * @Author: Hardy
     * @Date: 2018年12月13日 20:35:32
     * @param referralCode
     * @param proxyName
     * @return: com.tx.platform.vo.ProxyVO
     **/
    public ProxyVO getRegisterBindProxy(String referralCode,String proxyName) throws Exception;
}
