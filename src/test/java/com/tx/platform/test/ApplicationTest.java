package com.tx.platform.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.platform.Bootstrap;
import com.tx.platform.entity.UserEntity;
import com.tx.platform.mapper.UserMapper;

/**
 * 
 * @ClassName ApplicationTest
 * @Description 测试类
 * @author Hardy
 * @Date 2018年12月10日 下午9:28:57
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Bootstrap.class)
public class ApplicationTest {
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void test(){
        Integer uid = 526705;
        UserEntity user = userMapper.selectByPrimaryKey(uid);
        System.err.println(user);
    }
}
