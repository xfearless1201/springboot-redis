package com.tx.platform.vo;

import com.tx.platform.commons.BaseVO;

/**
 *  @ClassName StandLetterVO
 *  @Description 站内行VO
 *  @Author Hardy
 *  @Date 2018年12月12日 14:38
 *  @Version 1.0.0
 *  
 **/
public class StandLetterVO extends BaseVO {
    private static final long serialVersionUID = -5256367889095703158L;

    private String bdate;

    private String edate;

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    @Override
    public String toString() {
        return "StandLetterVO{" +
                "bdate='" + bdate + '\'' +
                ", edate='" + edate + '\'' +
                '}';
    }
}
