package com.tx.platform.mapper;

import com.tx.platform.entity.InternalMessageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface InternalMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InternalMessageEntity record);

    int insertSelective(InternalMessageEntity record);

    InternalMessageEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InternalMessageEntity record);

    int updateByPrimaryKey(InternalMessageEntity record);

    /**
     * 功能描述:
     * 根据时间查询用户所有的站内信
     * @Author: Hardy
     * @Date: 2018年12月12日 15:04:45
     * @param uid
     * @param bdate
     * @param edate
     * @return: java.util.List<com.tx.platform.entity.InternalMessageEntity>
     **/
    Map<String,Object> countUnreadLetters(@Param("uid") Integer uid, @Param("bdate") String bdate, @Param("edate") String edate);
}