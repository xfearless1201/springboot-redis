package com.tx.platform.mapper;

import com.tx.platform.entity.UserWalletEntity;

public interface UserWalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWalletEntity record);

    int insertSelective(UserWalletEntity record);

    UserWalletEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWalletEntity record);

    int updateByPrimaryKey(UserWalletEntity record);

    Double getUserIntegral(Integer uid);
}