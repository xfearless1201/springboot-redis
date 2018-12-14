package com.tx.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 *  *  @ClassName DESUtils
 *  *  @Description DES加密工具类
 *  *  @Author Hardy
 *  *  @Date 2018年12月08日 18:20
 *  *  @Version 1.0.0
 *  
 **/
public class DESUtils {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(DESUtils.class);

    /** des加密key **/
    private static final String DES_KEY = "tianxia88";

    private static DESUtils desUtils;

    static{
        desUtils = new DESUtils();
    }


    /**
     * 功能描述:
     * DES加密工具类
     * @Author: Hardy
     * @Date: 2018年12月06日 14:31:25
     * @param encryptStr 待加密串
     * @return: java.lang.String
     **/
    public static String desEncrypt(String encryptStr)throws Exception{
        try {
            return desUtils.base64Encode(desUtils.desEncrypt(encryptStr.getBytes())).replaceAll("\\s*", "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("DES加密异常:{}",e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * DES解密工具类
     * @Author: Hardy
     * @Date: 2018年12月06日 14:32:16
     * @param decryptStr
     * @return: java.lang.String
     **/
    public static String desDecrypt(String decryptStr)throws Exception{
        byte[] result = new byte[0];
        try {
            result = desUtils.base64Decode(decryptStr);
            return new String(desUtils.desDecrypt(result));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("DES解密异常:{}",e.getMessage());
            throw new Exception(e.getMessage());
        }
    }



    /**
     * 功能描述:
     * DES加密
     * @Author: Hardy
     * @Date: 2018年12月06日 14:36:07
     * @param plainText
     * @return: byte[]
     **/
    private byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    /**
     * 功能描述:
     * DES解密
     * @Author: Hardy
     * @Date: 2018年12月06日 14:36:21
     * @param encryptText
     * @return: byte[]
     **/
    private byte[] desDecrypt(byte[] encryptText) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    /**
     * 功能描述:
     * Byte数组转String类型
     * @Author: Hardy
     * @Date: 2018年12月06日 14:37:14
     * @param s
     * @return: java.lang.String
     **/
    private String base64Encode(byte[] s) throws Exception{
        if (s == null)
            return null;
        BASE64Encoder b = new sun.misc.BASE64Encoder();
        return b.encode(s);
    }

    /**
     * 功能描述:
     * String类型转Byte数组
     * @Author: Hardy
     * @Date: 2018年12月06日 14:37:31
     * @param s
     * @return: byte[]
     **/
    private byte[] base64Decode(String s) throws IOException {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return b;
    }

}
