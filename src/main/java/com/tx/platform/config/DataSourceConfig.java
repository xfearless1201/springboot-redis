package com.tx.platform.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tx.platform.datasource.DataSourceType;
import com.tx.platform.datasource.DynamicDataSource;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DataSourceConfig
 * @Description 数据库连接池配置类
 * @Author Hardy
 * @Date 2018年12月11日 1:46
 * @Version 1.0.0
 *  
 **/
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE)
public class DataSourceConfig {
    //dao包
    static final String PACKAGE = "com.tx.platform.mapper";
    //xml文件
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.master.url}")
    private String masterUrl;

    @Value("${jdbc.slave.url}")
    private String slaveUrl;

    @Bean
    public DataSource masterDataSource(){
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setUsername(username);
        masterDataSource.setPassword(password);
        masterDataSource.setDriverClassName(driverClassName);
        masterDataSource.setUrl(masterUrl);
        masterDataSource.setMaxWait(30000);
        masterDataSource.setMaxActive(100);
        masterDataSource.setMinIdle(0);
        masterDataSource.setInitialSize(0);
        return masterDataSource;
    }


    @Bean
    public DataSource slaveDataSource(){
        DruidDataSource slaveDataSource = new DruidDataSource();
        slaveDataSource.setUsername(username);
        slaveDataSource.setPassword(password);
        slaveDataSource.setDriverClassName(driverClassName);
        slaveDataSource.setUrl(slaveUrl);
        slaveDataSource.setMaxWait(30000);
        slaveDataSource.setMaxActive(100);
        slaveDataSource.setMinIdle(0);
        slaveDataSource.setInitialSize(0);
        return slaveDataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceType.MASTER_DATABASE.getDataBase(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE_DATABASE.getDataBase(), slaveDataSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);//指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));//

        return sqlSessionFactory.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
