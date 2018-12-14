package com.tx.platform.mapper;

import com.tx.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    UserEntity selectByUsername(String username);

    //查询用户信息,手机账号 或者 登录账号
    UserEntity selectByIsMobile(@Param("cagent")String cagent,@Param("username") String username,@Param("type") int type);

    UserEntity selectByloginmobileAndCagent(@Param("mobileNo") String mobileNo,@Param("cagent") String cagent);
}