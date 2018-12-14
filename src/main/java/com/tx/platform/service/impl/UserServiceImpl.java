package com.tx.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tx.platform.commons.BaseVO;
import com.tx.platform.entity.UserEntity;
import com.tx.platform.mapper.InternalMessageMapper;
import com.tx.platform.mapper.UserMapper;
import com.tx.platform.mapper.UserWalletMapper;
import com.tx.platform.service.SessionTokenService;
import com.tx.platform.service.UserService;
import com.tx.platform.vo.StandLetterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *  @ClassName UserServiceImpl
 *  @Description TODO(这里用一句话描述这个类的作用)
 *  @Author Hardy
 *  @Date 2018年12月08日 17:19
 *  @Version 1.0.0
 *  
 **/
@Service
public class UserServiceImpl implements UserService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWalletMapper userWalletMapper;

    @Autowired
    private InternalMessageMapper internalMessageMapper;

    @Autowired
    private SessionTokenService sessionTokenService;

    public int deleteByPrimaryKey(Integer uid) {
        return userMapper.deleteByPrimaryKey(uid);
    }

    public int insert(UserEntity record) {
        return userMapper.insert(record);
    }

    public int insertSelective(UserEntity record) {
        return userMapper.insertSelective(record);
    }

    public UserEntity selectByPrimaryKey(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    public int updateByPrimaryKeySelective(UserEntity record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserEntity record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public UserEntity selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /***
     * 功能描述:
     * 查询用户详情
     * @Author: Hardy
     * @Date: 2018年12月12日 11:37:40
     * @param baseVO
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @Override
    public JSONObject getUserInfo(BaseVO baseVO) throws Exception {
        JSONObject result = new JSONObject();//返回结果
        //通过token获取用户缓存中的信息
        Map<String,String> data = sessionTokenService.getUserInfoFromRedis(baseVO.getToken());
        //获取用户ID
        String uid = data.get("uid");
        if (StringUtils.isNotBlank(uid)){
            //查询用户详情
            UserEntity userEntity = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
            //查询用户积分
            Double integral = userWalletMapper.getUserIntegral(Integer.parseInt(uid));
            result.put("uid",uid);
            result.put("username",userEntity.getUsername());
            result.put("realname",userEntity.getRealname());
            result.put("typeId",userEntity.getTypeId());
            result.put("email",userEntity.getEmail());
            result.put("vip_level",userEntity.getVipLevel());
            result.put("reg_date",userEntity.getRegDate());
            result.put("wallet",userEntity.getWallet());
            result.put("integral",integral);
            result.put("loginmobile",userEntity.getLoginmobile());
        }
        return result;
    }

    /**
     * 功能描述:
     * 获取用户站内信
     * @Author: Hardy
     * @Date: 2018年12月12日 14:41:04
     * @param standLetterVO
     * @return: com.alibaba.fastjson.JSONObject
     **/
    @Override
    public JSONObject getMessageNum(StandLetterVO standLetterVO) throws Exception {
        logger.info("调用用户平台站内信业务开始====================START==================");
        try {
            //用户token
            String token = standLetterVO.getToken();
            //通过token获取用户信息
            Map<String,String> data = sessionTokenService.getUserInfoFromRedis(token);
            String uid = data.get("uid").toString();//用户ID
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String bdate = standLetterVO.getBdate();
            String edate = standLetterVO.getEdate();
            if (StringUtils.isNotBlank(bdate) && StringUtils.isNotBlank(edate)){
                //判断传递的时间小于当前系统毫秒数
                long bdatetime = sdf.parse(bdate).getTime();
                long edatetime = sdf.parse(edate).getTime();
                long currentime = System.currentTimeMillis();
                if (bdatetime > currentime || bdatetime > edatetime || (bdatetime - currentime) / 1000 > 60 * 60 * 24 * 30) {
                    return null;
                }
            }else{
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,-29);
                bdate = sdf.format(calendar.getTime());//一个月以前
                edate = sdf.format(new Date());//当前的时间,即今天
            }
            //查询用户的站内信
            Map<String,Object> lettermap = internalMessageMapper.countUnreadLetters(Integer.parseInt(uid),bdate,edate);
            if (CollectionUtils.isEmpty(lettermap)){
                return JSONObject.parseObject(JSONObject.toJSON(lettermap).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用用户平台站内信业务异常:{}",e.getMessage());
        }
        return null;
    }

}
