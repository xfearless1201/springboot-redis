package com.tx.platform.mapper;

import com.tx.platform.entity.LoginerrormapEntity;
import org.apache.ibatis.annotations.Param;

public interface LoginerrormapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginerrormapEntity record);

    int insertSelective(LoginerrormapEntity record);

    LoginerrormapEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginerrormapEntity record);

    int updateByPrimaryKey(LoginerrormapEntity record);

    LoginerrormapEntity selectByUsername(@Param("username") String username);

    int updateLockTimes(@Param("username") String username);
}