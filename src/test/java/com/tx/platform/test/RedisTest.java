package com.tx.platform.test;

import com.tx.platform.Bootstrap;
import com.tx.platform.utils.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName RedisTest
 * @Description 缓存测试类
 * @author Hardy
 * @Date 2018年12月10日 下午11:35:29
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Bootstrap.class)
public class RedisTest {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Test
    public void test(){
//        String uid = "526705";
//        String temptime = String.valueOf(System.currentTimeMillis());
//        String randStr = RandomUtils.generateString(8);//8位字符串
//        String token = uid+temptime+randStr;
//        //把用户ID存入缓存
//        redisTemplate.opsForValue().set(token,uid);
//        //从缓存中取出用户ID
//        String uuid = redisTemplate.opsForValue().get(token).toString();
//        System.err.println(uuid);

        Map<String,String> data = new HashMap<>();
        data.put("name","hardy");
        data.put("job","java");
        data.put("sex","man");
        data.put("uid","526705");

        String result = putAll(data);

        System.out.println(result);
    }

    private String putAll(Map<String,String> data) {
        redisTemplate.opsForHash().putAll("token",data);
        return "seccess";
    }

}
