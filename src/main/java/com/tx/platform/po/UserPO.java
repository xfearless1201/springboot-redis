package com.tx.platform.po;

import java.io.Serializable;

/**
 *  @ClassName UserPO
 *  @Description 用户返回信息封装类
 *  @Author Hardy
 *  @Date 2018年12月08日 18:53
 *  @Version 1.0.0
 *  
 **/
public class UserPO implements Serializable {
    private static final long serialVersionUID = 4303236694657036430L;

    private Integer uid;

    private String username;

    private Double balance;//用户余额

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
