package com.tx.platform.test;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {

    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //用的时候在打开
        String path=null;//"D:\\txPlatform-code-workspace\\txPlatform-2.1\\src\\main\\resources\\conf\\generator.xml";
        File configFile = new File(path);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    } 
    public static void main(String[] args) throws Exception {
        try {
            GeneratorTest generator = new GeneratorTest();
            generator.generator();
            System.err.println("生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
