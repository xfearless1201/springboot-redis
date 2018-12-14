package com.tx.platform.mapper;

import com.tx.platform.entity.UserDissociateEntity;
import com.tx.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserDissociateMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserDissociateEntity record);

    int insertSelective(UserDissociateEntity record);

    UserDissociateEntity selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserDissociateEntity record);

    int updateByPrimaryKey(UserDissociateEntity record);

    UserEntity selectByIsMobile(@Param("cagent") String cagent, @Param("username") String username, @Param("type") int type);

    UserEntity selectByUsername(@Param("username") String usernmae) throws Exception;

    UserEntity selectDisUserByMobileNo(@Param("mobileNo") String mobileNo,@Param("cagent") String cagent);
}