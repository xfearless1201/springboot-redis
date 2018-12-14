package com.tx.platform.mapper;

import com.tx.platform.entity.ProxyUserEntity;
import com.tx.platform.vo.ProxyVO;
import org.apache.ibatis.annotations.Param;

public interface ProxyUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProxyUserEntity record);

    int insertSelective(ProxyUserEntity record);

    ProxyUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProxyUserEntity record);

    int updateByPrimaryKey(ProxyUserEntity record);

    //通过推荐码查询代理商信息
    ProxyVO selectProxyByReferralCode(@Param("referralCode") String referralCode, @Param("proxyName") String proxyName);

}