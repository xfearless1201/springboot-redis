package com.tx.platform.vo;

import com.tx.platform.commons.BaseVO;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

/**
 * @ClassName RegisterVO
 * @Description 注册VO类
 * @Author Hardy
 * @Date 2018年12月13日 11:00
 * @Version 1.0.0
 *  
 **/
public class RegisterVO extends BaseVO {
    private static final long serialVersionUID = -7302177471026201352L;

    @NotNull(message = "用户名不能为空")
    private String userName;//用户名
    @NotNull(message = "手机号码不能为空")
    private String mobileNo;//手机号
    @Length(min = 5, max = 20, message = "请输入5-19位字符串")
    private String passWord;//密码
    @Length(min = 5, max = 20, message = "请输入5-19位字符串")
    private String repassWord;//确认密码
    @NotNull(message = "注册token不能为空")
    private String reguuid;//注册token
    private String imgcode;//验证码
    private String qkpwd;//取款密码
    private String reqkpwd;//确认取款密码
    @Length(min = 3, max = 3, message = "平台编码输入错误")
    private String cagent;//平台编码
    private String realName;//真实姓名
    private String proxyname;//代理名称
    private String referralCode;//推荐码
    private String remark;//备注
    private String isImgCode;//是否需要输入验证码


    //内部参数
    private String referer;//请求域名
    private String simgcode;//缓存中的验证码
    private String registerIp;//请求IP
    private String typeId;//分层ID
    private Integer proxyId;//代理ID
    private Integer topUid;//上层ID
    private String loginmobile;//手机登录账号
    private Integer cid;//平台ID
    private Integer uid;//用户ID


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRepassWord() {
        return repassWord;
    }

    public void setRepassWord(String repassWord) {
        this.repassWord = repassWord;
    }

    public String getReguuid() {
        return reguuid;
    }

    public void setReguuid(String reguuid) {
        this.reguuid = reguuid;
    }

    public String getImgcode() {
        return imgcode;
    }

    public void setImgcode(String imgcode) {
        this.imgcode = imgcode;
    }

    public String getQkpwd() {
        return qkpwd;
    }

    public void setQkpwd(String qkpwd) {
        this.qkpwd = qkpwd;
    }

    public String getReqkpwd() {
        return reqkpwd;
    }

    public void setReqkpwd(String reqkpwd) {
        this.reqkpwd = reqkpwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getProxyname() {
        return proxyname;
    }

    public void setProxyname(String proxyname) {
        this.proxyname = proxyname;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsImgCode() {
        return isImgCode;
    }

    public void setIsImgCode(String isImgCode) {
        this.isImgCode = isImgCode;
    }

    @Override
    public String getCagent() {
        return cagent;
    }

    @Override
    public void setCagent(String cagent) {
        this.cagent = cagent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getSimgcode() {
        return simgcode;
    }

    public void setSimgcode(String simgcode) {
        this.simgcode = simgcode;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getProxyId() {
        return proxyId;
    }

    public void setProxyId(Integer proxyId) {
        this.proxyId = proxyId;
    }

    public Integer getTopUid() {
        return topUid;
    }

    public void setTopUid(Integer topUid) {
        this.topUid = topUid;
    }

    public String getLoginmobile() {
        return loginmobile;
    }

    public void setLoginmobile(String loginmobile) {
        this.loginmobile = loginmobile;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "RegisterVO{" +
                "userName='" + userName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", passWord='" + passWord + '\'' +
                ", repassWord='" + repassWord + '\'' +
                ", reguuid='" + reguuid + '\'' +
                ", imgcode='" + imgcode + '\'' +
                ", qkpwd='" + qkpwd + '\'' +
                ", reqkpwd='" + reqkpwd + '\'' +
                ", cagent='" + cagent + '\'' +
                ", realName='" + realName + '\'' +
                ", proxyname='" + proxyname + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", remark='" + remark + '\'' +
                ", isImgCode='" + isImgCode + '\'' +
                ", referer='" + referer + '\'' +
                ", simgcode='" + simgcode + '\'' +
                ", registerIp='" + registerIp + '\'' +
                ", typeId='" + typeId + '\'' +
                ", proxyId=" + proxyId +
                ", topUid=" + topUid +
                ", loginmobile='" + loginmobile + '\'' +
                ", cid=" + cid +
                ", uid=" + uid +
                '}';
    }
}
