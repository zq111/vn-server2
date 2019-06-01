package com.framework.controller;

import com.framework.constant.Constant;
import com.framework.linerunner.MethodHandler;
import com.framework.utils.GsonTools;
import com.framework.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * API接口,通过@PathVariable注解获取请求渠道,请求模块,请求方法,
 * 通过ApplicationContext构建模块对象
 * 通过反射执行请求方法
 * 将返回结果封装为Map返回至前端
 */

@Slf4j
@RestController
@RequestMapping(Constant.API_MAPPER)
public class AdapterController {

    @RequestMapping(value = Constant.METHOD_MAPPER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object invokeApi(@PathVariable String channel, @PathVariable String serviceName, @PathVariable String methodName, @RequestBody(required = false) String body) throws Exception {

        //获取bean对象
        serviceName = serviceName.concat(Constant.SERVICE_SUF);
        Object service = SpringContextHolder.getBean(serviceName);

        //通过handler处理器映射,获取method对象
        Method method = MethodHandler.getMethod(serviceName, methodName);

        //验证接口方法是否只有一个参数
        Class<?>[] parameterTypes = method.getParameterTypes();

        //转换json到参数对象
        Object parameter = GsonTools.str2T(body, parameterTypes[0]);

        //执行方法调用,返回调用结果
        return method.invoke(service, parameter);
    }
}
