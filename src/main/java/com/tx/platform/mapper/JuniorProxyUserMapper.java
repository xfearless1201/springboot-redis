package com.tx.platform.mapper;

import com.tx.platform.entity.JuniorProxyUserEntity;
import com.tx.platform.vo.ProxyVO;
import org.apache.ibatis.annotations.Param;

public interface JuniorProxyUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JuniorProxyUserEntity record);

    int insertSelective(JuniorProxyUserEntity record);

    JuniorProxyUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JuniorProxyUserEntity record);

    int updateByPrimaryKey(JuniorProxyUserEntity record);

    //通过推荐码查询三级代理商信息
    ProxyVO selectProxyByReferralCode(@Param("referralCode") String referralCode, @Param("proxyName") String proxyName);
}