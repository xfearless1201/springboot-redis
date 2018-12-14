package com.tx.platform.mapper;

import com.tx.platform.entity.UserTypeEntity;
import org.apache.ibatis.annotations.Param;

public interface UserTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTypeEntity record);

    int insertSelective(UserTypeEntity record);

    UserTypeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTypeEntity record);

    int updateByPrimaryKey(UserTypeEntity record);

    int selectTypeIdByCagent(@Param("cagent") String cagent);
}