package com.tx.platform.datasource;

/**
 * 功能描述:
 * 数据源枚举类
 * @Author: Hardy
 * @Date: 2018年12月15日 20:11:11
 **/
public enum DataSourceType {
    MASTER_DATABASE("txdata_db1", "业务库"),
    SLAVE_DATABASE("ftpdata_xmldb", "注单库"),;

    String dataBase;

    String decription;

    DataSourceType(String dataBase, String decription) {
        this.dataBase = dataBase;
        this.decription = decription;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }


}
