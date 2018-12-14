package com.tx.platform.mapper;

import com.tx.platform.entity.ReserveAccountEntity;
import org.apache.ibatis.annotations.Param;

public interface ReserveAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReserveAccountEntity record);

    int insertSelective(ReserveAccountEntity record);

    ReserveAccountEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReserveAccountEntity record);

    int updateByPrimaryKey(ReserveAccountEntity record);

    //查询平台保留账号
    ReserveAccountEntity selectByUsernameAndCagent(@Param("username") String username,@Param("cagent") String cagent);
}