package com.tx.platform.mapper;

import com.tx.platform.entity.CagentEntity;

public interface CagentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CagentEntity record);

    int insertSelective(CagentEntity record);

    CagentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CagentEntity record);

    int updateByPrimaryKey(CagentEntity record);

    CagentEntity selectByCagent(String cagent);
}