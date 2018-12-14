package com.tx.platform.commons;

import java.io.Serializable;

/**
 *  *  @ClassName BaseVO
 *  *  @Description 基类VO
 *  *  @Author Hardy
 *  *  @Date 2018年12月08日 17:36
 *  *  @Version 1.0.0
 *  
 **/
public class BaseVO implements Serializable {
    private static final long serialVersionUID = 6193900537597571624L;

//    @NotNull(message="平台编码不能为空")
    private String cagent;//平台编码

//    @NotNull(message="版本号不能为空")
    private String version;//版本号
    private String isMobile = "0";//是否手机端,默认为0 PC端

    private String token;//会话令牌

    public String getCagent() {
        return cagent;
    }

    public void setCagent(String cagent) {
        this.cagent = cagent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseVO{" +
                "cagent='" + cagent + '\'' +
                ", version='" + version + '\'' +
                ", isMobile='" + isMobile + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
