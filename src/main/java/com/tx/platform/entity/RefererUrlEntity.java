package com.tx.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 * 域名白名单实体类
 * @Author: Hardy
 * @Date: 2018年12月11日 12:08:56
 **/
public class RefererUrlEntity implements Serializable {
    private static final long serialVersionUID = 510168565968268232L;
    private Integer id;

    private String domain;

    private String name;

    private String rmk;

    private Date addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
}