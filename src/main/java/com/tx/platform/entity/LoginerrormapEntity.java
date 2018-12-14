package com.tx.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 * 登录密码输入错误记录实体类
 * @Author: Hardy
 * @Date: 2018年12月13日 12:24:10
 **/
public class LoginerrormapEntity implements Serializable {
    private static final long serialVersionUID = 1007927123293762034L;
    private Integer id;

    private String username;

    private Integer times;

    private Date logintime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
}