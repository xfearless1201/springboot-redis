package com.tx.platform.mapper;

import com.tx.platform.entity.RefererUrlEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RefererUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RefererUrlEntity record);

    int insertSelective(RefererUrlEntity record);

    RefererUrlEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RefererUrlEntity record);

    int updateByPrimaryKey(RefererUrlEntity record);

    int checkReqUrl(@Param("cagent") String cagent,@Param("domain") String domain);
}