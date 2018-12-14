package com.tx.platform.mapper;

import com.tx.platform.entity.UserQuantityEntity;

public interface UserQuantityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserQuantityEntity record);

    int insertSelective(UserQuantityEntity record);

    UserQuantityEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserQuantityEntity record);

    int updateByPrimaryKey(UserQuantityEntity record);
}