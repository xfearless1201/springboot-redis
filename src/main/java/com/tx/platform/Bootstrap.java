package com.tx.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @ClassName Bootstrap
 * @Description 启动类
 * @author Hardy
 * @Date 2018年12月10日 下午9:18:04
 * @version 1.0.0
 */
@SpringBootApplication()
@EnableScheduling
@Import({net.sf.oval.Validator.class})
@ComponentScan(basePackages = "com.tx.platform")
@PropertySource(value={"classpath:conf/jdbc.properties","classpath:conf/redis.properties","classpath:conf/conf.properties"},ignoreResourceNotFound = true)
public class Bootstrap {
    
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
