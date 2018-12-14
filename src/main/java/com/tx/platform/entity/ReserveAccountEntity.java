package com.tx.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 * 系统保留账号实体类
 * @Author: Hardy
 * @Date: 2018年12月13日 15:25:17
 **/
public class ReserveAccountEntity implements Serializable {
    private static final long serialVersionUID = -4494292985746978476L;
    private Integer id;

    private String username;

    private String cagent;

    private String rmk;

    private Date addtime;

    private Integer adduid;

    private Integer cid;

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

    public String getCagent() {
        return cagent;
    }

    public void setCagent(String cagent) {
        this.cagent = cagent == null ? null : cagent.trim();
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk == null ? null : rmk.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getAdduid() {
        return adduid;
    }

    public void setAdduid(Integer adduid) {
        this.adduid = adduid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}