package com.tx.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *  @ClassName SessionConfig
 *  @Description session配置
 *  @Author Hardy
 *  @Date 2018年12月11日 3:34
 *  @Version 1.0.0
 *  
 **/
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
