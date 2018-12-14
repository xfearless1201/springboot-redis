package com.tx.platform.vo;

import com.tx.platform.commons.BaseVO;
import net.sf.oval.constraint.NotNull;

/**
 *  @ClassName LoginVO
 *  @Description 登录VO类
 *  @Author Hardy
 *  @Date 2018年12月08日 17:37
 *  @Version 1.0.0
 *  
 **/
public class LoginVO extends BaseVO {

    @NotNull(message="用户名不能为空")
    private String tname;
    @NotNull(message="用户密码不能为空")
    private String tpwd;
    @NotNull(message="验证码不能为空")
    private String imgcode;//验证码

    private String isImgCode;//是否验证
    private String isMobile;//是否是移动端
    private String savelogin;

    private String simgcode;//
    private String refurl;//白名单
    private String loginIp;
    private String ipAddress;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTpwd() {
        return tpwd;
    }

    public void setTpwd(String tpwd) {
        this.tpwd = tpwd;
    }

    public String getImgcode() {
        return imgcode;
    }

    public void setImgcode(String imgcode) {
        this.imgcode = imgcode;
    }

    public String getIsImgCode() {
        return isImgCode;
    }

    public void setIsImgCode(String isImgCode) {
        this.isImgCode = isImgCode;
    }

    @Override
    public String getIsMobile() {
        return isMobile;
    }

    @Override
    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public String getSavelogin() {
        return savelogin;
    }

    public void setSavelogin(String savelogin) {
        this.savelogin = savelogin;
    }

    public String getSimgcode() {
        return simgcode;
    }

    public void setSimgcode(String simgcode) {
        this.simgcode = simgcode;
    }

    public String getRefurl() {
        return refurl;
    }

    public void setRefurl(String refurl) {
        this.refurl = refurl;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "tname='" + tname + '\'' +
                ", tpwd='" + tpwd + '\'' +
                ", imgcode='" + imgcode + '\'' +
                ", isImgCode='" + isImgCode + '\'' +
                ", isMobile='" + isMobile + '\'' +
                ", savelogin='" + savelogin + '\'' +
                ", simgcode='" + simgcode + '\'' +
                ", refurl='" + refurl + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
