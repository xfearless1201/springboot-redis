/****************************************************************** 
 *
 *    Powered By tianxia-online. 
 *
 *    Copyright (c) 2018-2020 Digital Telemedia 天下网络 
 *    http://www.d-telemedia.com/ 
 *
 *    Package:     com.sky.pay.util 
 *
 *    Filename:    Base64Util.java 
 *
 *    Description: TODO(用一句话描述该文件做什么) 
 *
 *    Copyright:   Copyright (c) 2018-2020 
 *
 *    Company:     天下网络科技 
 *
 *    @author:     Hardy 
 *
 *    @version:    1.0.0 
 *
 *    Create at:   2018年08月31日 15:11 
 *
 *    Revision: 
 *
 *    2018/8/31 15:11 
 *        - first revision 
 *
 *****************************************************************/
package com.tx.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 *  * @ClassName Base64Util
 *  * @Description base64工具类
 *  * @Author Hardy
 *  * @Date 2018年08月31日 15:11
 *  * @Version 1.0.0
 *  
 **/
public class Base64Utils {

    //日志
    public static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    static final String CHARSET_UTF_8="UTF-8";

    /**
     * 功能描述:
     * BASE64加密算法
     * @Author: Hardy
     * @Date: 2018年08月31日 15:14:29
     * @param data
     * @return: java.lang.String
     **/
    private String encryptBASE64(byte[] data) throws Exception{
        try {
            String encode = new BASE64Encoder().encode(data);
            return encode;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Base64加密异常:"+e.getMessage());
            throw new Exception("Base64加密异常!");
        }
    }

    /**
     * 功能描述:
     * BASE64解密算法
     * @Author: Hardy
     * @Date: 2018年08月31日 15:14:39
     * @param data
     * @return: byte[]
     **/
    private byte[] decryptBASE64(String data) throws Exception {
        try {
            byte[] code = new BASE64Decoder().decodeBuffer(data);
            return code;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Base64解密异常:"+e.getMessage());
            throw new Exception("Base64解密异常!");
        }
    }

    /**
     * 功能描述:
     * 加密
     * @Author: Hardy
     * @Date: 2018年08月31日 15:23:11
     * @param source 待加密原串
     * @return: java.lang.String
     **/
    public static String encryptStr(String source) throws Exception{
        try {
            Base64Utils base = new Base64Utils();
            byte[] strByte = source.getBytes(CHARSET_UTF_8);
            String str = base.encryptBASE64(strByte);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 待解密原串
     * @Author: Hardy
     * @Date: 2018年08月31日 15:23:52
     * @param source
     * @return: java.lang.String
     **/
    public static String decryptStr(String source) throws Exception{
        try {
            Base64Utils base = new Base64Utils();
            byte[] strByte = base.decryptBASE64(source);
            String str = new String(strByte);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String str = "我们是冠军!";
        try {
            String encryptStr = encryptStr(str);
            System.out.println("加密后结果:"+encryptStr);
            String decryptStr = decryptStr(encryptStr);
            System.out.println("解密后结果:"+decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
