package com.framework.linerunner;

import com.framework.conf.properties.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 初始化系统用户
 * SpringApplication.callRunners方法负责加载Runner,callRunner负责执行,以下为CallRunner部分源码,
 * 很清楚的看到先加载所有Runner然后根据Order里面的值排序,然后依次执行,可以看到实现ApplicationRunner与CommandLineRunner效果是一样的
 * 		runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
 *      runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
 *      AnnotationAwareOrderComparator.sort(runners);
 */
@Slf4j
@Component
@Order(0)
@Transactional
public class InitSystemUserLineRunner implements CommandLineRunner {

    private final SystemProperties systemProperties;

    @Autowired
    public InitSystemUserLineRunner(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        String username = systemProperties.getUsername();
        String password = systemProperties.getPassword();

        System.out.println(username + "\r\n" + password);
        if (log.isDebugEnabled()) {
            log.debug("初始化后台管理用户完成");
        }
    }
}
