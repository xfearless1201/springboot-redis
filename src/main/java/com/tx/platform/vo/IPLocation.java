package com.tx.platform.vo;

/**
 *  *  @ClassName IPLocationVO
 *  *  @Description TODO(这里用一句话描述这个类的作用)
 *  *  @Author Hardy
 *  *  @Date 2018年12月11日 18:46
 *  *  @Version 1.0.0
 *  
 **/
public class IPLocation {
    /**
     * 国家
     */
    private String country;
    /**
     * 区域 - 省份 + 城市
     */
    private String area;

    public IPLocation() {
        country = area = "";
    }

    public synchronized IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        ret.country = country;
        ret.area = area;
        return ret;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        String city = "";
        if(country != null){
            String[] array = country.split("省");
            if(array != null && array.length > 1){
                city =  array[1];
            } else {
                city = country;
            }
            if(city.length() > 3){
                city.replace("内蒙古", "");
            }
        }
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        //如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
        if(area.trim().equals("CZ88.NET")){
            this.area="本机或本网络";
        }else{
            this.area = area;
        }
    }
}
