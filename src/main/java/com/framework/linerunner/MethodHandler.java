package com.framework.linerunner;

import com.framework.Exception.CommonException;
import com.framework.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@Order(1)
public class MethodHandler implements CommandLineRunner {

    /**
     * 处理器映射对象
     */
    private static final Map<String,Map<String,Method>> handlerMappingMap = new HashMap<>(128);

    public static Method getMethod(String serviceName,String methodName) throws Exception{

        //首字母大写转换
        char[] chars = serviceName.toCharArray();
        chars[0] = (char)(chars[0] - 32);
        serviceName = new String(chars);

        //查询对应模块
        Map<String, Method> service = handlerMappingMap.get(serviceName);

        if (null == service) {
            throw  new CommonException("目标模块不存在");
        }

        //查询对应方法
        Method method = service.get(methodName);

        if (null == method) {
            throw new CommonException("目标方法不存在");
        }

        return method;
    }


    /**
     * 初始化请求handler对象
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //获取所有添加@Service的bean对象
        Map<String, Object> beans = SpringContextHolder.getApplicationContext().getBeansWithAnnotation(Service.class);

        beans.values().forEach(value -> {
            //获取bean名称
            String serviceName = value.getClass().getSimpleName().split("\\$")[0];

            Map<String,Method> handler = new HashMap<>();
            //获取除了排除了继承的所有方法
            Method[] methods = value.getClass().getDeclaredMethods();

            Arrays.asList(methods).forEach(method -> {

                handler.put(method.getName(),method);


                if (log.isDebugEnabled()) {
                    log.info("--初始化处理器{}下接口:{}",serviceName,method.getName());
                }

            });

            handlerMappingMap.put(serviceName,handler);
        });

        if (log.isDebugEnabled()) {
            log.info("--初始化处理器完成");
        }
    }
}
