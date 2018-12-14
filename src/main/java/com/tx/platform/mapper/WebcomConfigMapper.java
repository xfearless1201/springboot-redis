package com.tx.platform.mapper;

import com.tx.platform.entity.WebcomConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebcomConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebcomConfigEntity record);

    int insertSelective(WebcomConfigEntity record);

    WebcomConfigEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebcomConfigEntity record);

    int updateByPrimaryKey(WebcomConfigEntity record);

    /**
     * 功能描述:
     * 获取平台通告配置列表
     * @Author: Hardy
     * @Date: 2018年12月12日 12:25:28
     * @param cagent
     * @param types
     * @return: java.util.List<com.tx.platform.entity.WebcomConfigEntity>
     **/
    List<WebcomConfigEntity> findAllByCagentAndType(@Param("cagent") String cagent,@Param("types") List<String> types);
}