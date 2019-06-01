package com.framework.dao.mybatis.generator;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 *  mybatis generator 自动生成代码插件
 *  启动主类
 */
public class MybatisGenerator {


    public static void main(String[] args) throws Exception{

        Resource resource = new ClassPathResource("/mybatis/generator/generatorConfig.xml");

        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resource.getInputStream());
        CustomShellCallback callback = new CustomShellCallback();
        Generator myBatisGenerator = new Generator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
