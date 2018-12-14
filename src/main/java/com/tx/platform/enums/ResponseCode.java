package com.tx.platform.enums;
/**
 * 功能描述:
 * 返回状态码和信息枚举类
 * @Author: Hardy
 * @Date: 2018年12月08日 14:41:37
 **/
public enum ResponseCode {
    SUCCESS("10000","成功"),//成功基本状态,可根据类型,状态码累加
    FAIL("20000","失败"),//失败基本状态,可根据类型,状态码累加
    LOGIN_FAIL("20001","登录失败"),
    REGIS_FAIL("20002","注册失败"),
    DATA_FAIL("20003","数据提交失败"),
    TOKEN_FAIL("20004","token校验失败"),
    USER_FAIL("20004","用户操作失败"),
    VERIFICAT_CODE_FAIL("20005","验证码错误"),
    DOMAIN_FAIL("20006","请求域名错误"),
    PASSWORD_FAIL("20007","密码输入错误"),
    USERNAME_FAIL("20008","用户名输入错"),
    LOGOUT_FAIL("20009","退出失败"),
    COOKIE_FAIL("20010","获取cookie失败"),
    MOBILE_NO_FAIL("20010","手机号码不正确"),
    CAGENT_CODE_FAIL("20010","平台编码错误"),
    REFERRAL_CODE_FAIL("20010","会员推荐码错误"),
    REALNAME_FAIL("20011","真实姓名错误"),
    ERROR("30000","异常"),//异常基本状态,可根据类型,状态码累加
    LOGIN_ERROR("30001","登录异常"),
    REGIS_ERROR("30002","注册异常"),
    DATA_ERROR("30003","数据提交异常"),
    TOKEN_ERROR("30004","token校验异常"),
    USER_ERROR("30005","用户操作异常"),
    LOGOUT_ERROR("30006","退出异常"),
    ;

    private String code;//状态码
    private String msg;//状态描述

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
