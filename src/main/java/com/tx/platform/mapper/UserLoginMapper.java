package com.tx.platform.mapper;

import com.tx.platform.entity.UserLoginEntity;

public interface UserLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginEntity record);

    int insertSelective(UserLoginEntity record);

    UserLoginEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginEntity record);

    int updateByPrimaryKey(UserLoginEntity record);
}