package com.tx.platform.vo;

import java.io.Serializable;

/**
 *  @ClassName ProxyVO
 *  @Description 代理商VO类
 *  @Author Hardy
 *  @Date 2018年12月13日 19:43
 *  @Version 1.0.0
 *  
 **/
public class ProxyVO implements Serializable {
    private static final long serialVersionUID = 2952605061176625686L;

    private Integer topUid;

    private Integer proxyId;

    private String typeId;

    public Integer getTopUid() {
        return topUid;
    }

    public void setTopUid(Integer topUid) {
        this.topUid = topUid;
    }

    public Integer getProxyId() {
        return proxyId;
    }

    public void setProxyId(Integer proxyId) {
        this.proxyId = proxyId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "ProxyVO{" +
                "topUid=" + topUid +
                ", proxyId=" + proxyId +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
