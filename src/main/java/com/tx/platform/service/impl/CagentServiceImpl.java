package com.tx.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tx.platform.entity.WebcomConfigEntity;
import com.tx.platform.mapper.WebcomConfigMapper;
import com.tx.platform.service.CagentService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  @ClassName CagentServiceImpl
 *  @Description TODO(这里用一句话描述这个类的作用)
 *  @Author Hardy
 *  @Date 2018年12月08日 17:27
 *  @Version 1.0.0
 *  
 **/
@Service
public class CagentServiceImpl implements CagentService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(CagentServiceImpl.class);

    @Autowired
    private WebcomConfigMapper webcomConfigMapper;


    @Override
    public JSONArray getCagentNotice(String cagent) throws Exception {
        JSONArray jsonArray = new JSONArray();
        //通过平台编码查询平台通告
        List<String> types = new ArrayList<>();
        types.add("6");
        List<WebcomConfigEntity> webcomConfigEntitys = webcomConfigMapper.findAllByCagentAndType(cagent,types);
        if (webcomConfigEntitys != null && !webcomConfigEntitys.isEmpty()){
            for (WebcomConfigEntity webcomConfigEntity : webcomConfigEntitys){
                JSONObject data = new JSONObject();
                data.put("rmk", StringEscapeUtils.unescapeJava(webcomConfigEntity.getRmk()));
                data.put("src1",webcomConfigEntity.getSrc1());
                data.put("type",webcomConfigEntity.getType());
                jsonArray.add(data);
            }
        }

        return jsonArray;
    }

    /**
     * 功能描述:
     * 获取网站广告图
     * @Author: Hardy
     * @Date: 2018年12月12日 12:37:18
     * @param cagent
     * @return: com.alibaba.fastjson.JSONArray
     **/
    @Override
    public JSONArray getCagentAdd(String cagent) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String[] arr = {"1","2","3","4","5"};
        List<String> types = Arrays.asList(arr);
        //查询
        List<WebcomConfigEntity> webcomConfigEntities = webcomConfigMapper.findAllByCagentAndType(cagent,types);
        if (webcomConfigEntities != null && !webcomConfigEntities.isEmpty()){
            for (WebcomConfigEntity webcomConfigEntity : webcomConfigEntities){
                JSONObject data = JSONObject.parseObject(JSON.toJSONString(webcomConfigEntity));
                jsonArray.add(data);
            }
        }
        return jsonArray;
    }
}
