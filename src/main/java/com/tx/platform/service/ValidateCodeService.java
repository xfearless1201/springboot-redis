package com.tx.platform.service;

import java.io.OutputStream;

/**
 *  @ClassName ValidateCodeService
 *  @Description 生成验证码接口
 *  @Author Hardy
 *  @Date 2018年12月12日 15:57
 *  @Version 1.0.0
 *  
 **/
public interface ValidateCodeService {

    //生成验证码
    public String generatorValidateCode(int width, int height, int codeCount, int lineCount) throws Exception;


    public void write(String path) throws Exception;


    public void write(OutputStream sos) throws Exception;
}
