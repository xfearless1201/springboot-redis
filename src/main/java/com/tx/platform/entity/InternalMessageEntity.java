package com.tx.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 * 站内行实体类
 * @Author: Hardy
 * @Date: 2018年12月13日 12:23:18
 **/
public class InternalMessageEntity implements Serializable {
    private static final long serialVersionUID = 3350973588398687345L;
    private Integer id;

    private Integer uid;

    private String message;

    private String status;

    private Date addtime;

    private Integer adduserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getAdduserid() {
        return adduserid;
    }

    public void setAdduserid(Integer adduserid) {
        this.adduserid = adduserid;
    }
}