package com.tx.platform.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName DataSourceConfig
 * @Description 数据库连接池配置类
 * @Author Hardy
 * @Date 2018年12月11日 1:46
 * @Version 1.0.0
 *  
 **/
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
    //dao包
    static final String PACKAGE = "com.tx.platform.mapper";
    //xml文件
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${jdbc.data.driver}")
    private String dataDriver;

    @Value("${jdbc.data.url}")
    private String dataUrl;

    @Value("${jdbc.data.username}")
    private String dataUsername;

    @Value("${jdbc.data.password}")
    private String dataPassword;

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(dataUsername);
        druidDataSource.setPassword(dataPassword);
        druidDataSource.setDriverClassName(dataDriver);
        druidDataSource.setUrl(dataUrl);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(0);
        druidDataSource.setMaxActive(100);
        druidDataSource.setMaxWait(100000);
        return druidDataSource;
    }

    @Bean(name = "dataSourceTransactionManager")
    @ConditionalOnMissingBean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource txDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(txDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(DataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
