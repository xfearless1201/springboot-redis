package com.tx.platform.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *  @ClassName DynamicDataSource
 *  @Description 动态加载数据源
 *  @Author Hardy
 *  @Date 2018年12月15日 20:06
 *  @Version 1.0.0
 *  
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSource.getDataSourceType();
    }

    public static String getDataSourceType(){
        return contextHolder.get();
    }

    public static void setDataSourceType(String dataSourceType){
        contextHolder.set(dataSourceType);
    }
}
