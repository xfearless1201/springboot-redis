package com.tx.platform.service.impl;

import com.tx.platform.commons.RedisConstants;
import com.tx.platform.service.SessionTokenService;
import com.tx.platform.utils.MD5Utils;
import com.tx.platform.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * 
 * @ClassName SessionTokenServiceImpl
 * @Description token实现类
 * @author Hardy
 * @Date 2018年12月10日 下午10:48:39
 * @version 1.0.0
 */
@Service
public class SessionTokenServiceImpl implements SessionTokenService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(SessionTokenServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    //生成token
    @Override
    public String generatorToken(String uid) throws Exception{
        try {
            //生成token规则:用户ID + 时间戳 + 随机数,进行MD5加密
            String encrpStr = uid + String.valueOf(System.currentTimeMillis()) + RandomUtils.generateString(8);
            return MD5Utils.encryptToLower_32bit(encrpStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("生成token签名串异常:{}",e.getMessage());
        }
        return null;
    }

    @Override
    public String addUserInfoToRedis(Map<String, String> data) throws Exception {
        logger.info("保存用户信息,写入redis开始==================START===================");
        try {
            if (CollectionUtils.isEmpty(data)){
                return null;
            }
            //生成缓存token令牌
            if (data.containsKey("uid") && StringUtils.isNotBlank(data.get("uid"))){
                String uid = data.get("uid").toString();
                //判断该用户是否已登录,是则踢掉
                if (redisTemplate.hasKey(RedisConstants.REPEAT_UID+uid)){
                    //把新的token,替换已存在的
                    String oldToken = (String) redisTemplate.opsForValue().get(RedisConstants.REPEAT_UID+uid);
                    redisTemplate.opsForHash().getOperations().delete(oldToken);
                    redisTemplate.opsForValue().getOperations().delete(RedisConstants.REPEAT_UID+uid);
                }

                String token = generatorToken(uid);
                if (redisTemplate.hasKey(token)){
                    redisTemplate.delete(token);
                }
                //设置保存时间
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, +1);//得到下个月
                redisTemplate.opsForHash().putAll(token,data);
                //重置登录证明value
                redisTemplate.opsForValue().set(RedisConstants.REPEAT_UID+uid,token);
                //保存用户信息,并保存一个月
                redisTemplate.expireAt(token,calendar.getTime());//保存一个月
                redisTemplate.expireAt(RedisConstants.REPEAT_UID+uid,calendar.getTime());
                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("保存用户信息,写入redis异常:{}",e.getMessage());
        }
        return null;
    }


    /**
     * 功能描述:
     * 从缓存中获取详情
     * @Author: Hardy
     * @Date: 2018年12月14日 10:45:49
     * @param key
     * @return: java.util.Map<java.lang.String , java.lang.String>
     **/
    public Map<String,String> getUserInfoFromRedis(String key) throws Exception{
        logger.info("从缓存中获取用户详情=====================START=======================");
        try {
            if (redisTemplate.hasKey(key)){
                //获取信息
                return (Map<String, String>) redisTemplate.opsForHash().entries(key);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("从缓存中获取用户详情异常:{}",e.getMessage());
        }
        return null;
    }

    /**
     * 功能描述:
     * 从缓存中删除信息
     * @Author: Hardy
     * @Date: 2018年12月14日 10:55:17
     * @param key
     * @return: void
     **/
    public void deleteUserInfoFromRedis(String key) throws Exception{
        logger.info("从缓存中删除用户信息============================START===================");
        try {

            if (redisTemplate.hasKey(key)){
                Map<String,String> data = (Map<String, String>) redisTemplate.opsForHash().entries(key);
                String uid = data.get("uid");
                redisTemplate.delete(key);
                redisTemplate.delete(RedisConstants.REPEAT_UID+uid);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("从缓存中删除用户信息异常:{}",e.getMessage());
        }
    }
}
