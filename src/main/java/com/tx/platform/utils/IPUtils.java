package com.tx.platform.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * @ClassName IPUtils
 * @Description IP工具类
 * @Author Hardy
 * @Date 2018年12月11日 18:37
 * @Version 1.0.0
 *  
 **/
public class IPUtils {

    private static final Logger log = LoggerFactory.getLogger(IPUtils.class);
    private static StringBuilder sb = new StringBuilder();

    public static String getAddress(HttpServletRequest request) {
        /*
         * String ip = request.getHeader("x-forwarded-for"); if(ip == null ||
         * ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip =
         * request.getHeader("Proxy-Client-IP"); } if(ip == null || ip.length() == 0 ||
         * "unknown".equalsIgnoreCase(ip)) { ip =
         * request.getHeader("WL-Proxy-Client-IP"); } if(ip == null || ip.length() == 0
         * || "unknown".equalsIgnoreCase(ip)) { ip = request.getRemoteAddr(); }
         * if(ip.indexOf(",") !=-1){ //多级反向代理，截取有效IP ip =ip.split(",")[0]; }
         * if("0:0:0:0:0:0:0:1".equals(ip)){ ip="0.0.0.0"; } return ip;
         */

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static String getIp(HttpServletRequest request) {
        String ipAddress = getAddress(request);
        if (StringUtils.isBlank(ipAddress)) {
            return "127.0.0.1";
        }
        return ipAddress;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String address = "未知";
        try {
            /*String address = "未知";
            IP.enableFileWatch = false; // 默认值为：false，如果为true将会检查ip库文件的变化自动reload数据
            IP.load("/IPAddress.dat");
            String ipads[] = IP.find(IPTools.getIp(request));
            StringBuffer s = new StringBuffer();
            for (int i = 1; i < ipads.length; i++) {
                if (ipads[i] != null && !"".equals(ipads[i])) {
                    if (i > 1) {
                        s.append(",");
                    }
                    s.append(ipads[i]);
                }
            }*/
            /*IPAddressUtils ip1 = new IPAddressUtils();
            ip1.init();
            String address = ip1.getIPLocation(IPTools.getIp(request)).getCountry();*/
            //获取登录IP地址
            String reg6 = "(?i)^((([\\da-f]{1,4}:){7}[\\da-f]{1,4})|(([\\da-f]{1,4}:){1,7}:)|(([\\da-f]{1,4}:){6}:[\\da-f]{1,4})|(([\\da-f]{1,4}:){5}(:[\\da-f]{1,4}){1,2})|(([\\da-f]{1,4}:){4}(:[\\da-f]{1,4}){1,3})|(([\\da-f]{1,4}:){3}(:[\\da-f]{1,4}){1,4})|(([\\da-f]{1,4}:){2}(:[\\da-f]{1,4}){1,5})|([\\da-f]{1,4}:(:[\\da-f]{1,4}){1,6})|(:(:[\\da-f]{1,4}){1,7})|(([\\da-f]{1,4}:){6}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){4}(:[\\da-f]{1,4}){0,1}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){3}(:[\\da-f]{1,4}){0,2}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){2}(:[\\da-f]{1,4}){0,3}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|([\\da-f]{1,4}:(:[\\da-f]{1,4}){0,4}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(:(:[\\da-f]{1,4}){0,5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}))$";
            if (getIp(request).matches(reg6)) {
                address = "ip6:" + getIp(request).toString();
            } else {
                IPLocaltionUtils location = IPLocaltionUtils.getInstance();
                address = location.findLocation(IPUtils.getIp(request));
            }

        } catch (Exception e) {
        }
        return address;
    }
    /**
     * 从ip的字符串形式得到字节数组形式
     *
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = null;
        try {
            String reg6 = "(?i)^((([\\da-f]{1,4}:){7}[\\da-f]{1,4})|(([\\da-f]{1,4}:){1,7}:)|(([\\da-f]{1,4}:){6}:[\\da-f]{1,4})|(([\\da-f]{1,4}:){5}(:[\\da-f]{1,4}){1,2})|(([\\da-f]{1,4}:){4}(:[\\da-f]{1,4}){1,3})|(([\\da-f]{1,4}:){3}(:[\\da-f]{1,4}){1,4})|(([\\da-f]{1,4}:){2}(:[\\da-f]{1,4}){1,5})|([\\da-f]{1,4}:(:[\\da-f]{1,4}){1,6})|(:(:[\\da-f]{1,4}){1,7})|(([\\da-f]{1,4}:){6}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){4}(:[\\da-f]{1,4}){0,1}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){3}(:[\\da-f]{1,4}){0,2}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([\\da-f]{1,4}:){2}(:[\\da-f]{1,4}){0,3}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|([\\da-f]{1,4}:(:[\\da-f]{1,4}){0,4}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(:(:[\\da-f]{1,4}){0,5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}))$";
            if(ip.matches(reg6)) {
                ret = IPConvert.toByte(ip);
            }
            else {
                ret  = new byte[4];
                StringTokenizer st = new StringTokenizer(ip, ".");
                ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
                ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
                ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
                ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            }
        } catch (Exception e) {
            log.error("从ip的字符串形式得到字节数组形式报错" + e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 字节数组IP转String
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
        sb.delete(0, sb.length());
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     *
     * @param b        字节数组
     * @param offset   要转换的起始位置
     * @param len      要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }
}
