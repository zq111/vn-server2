package com.framework.Exception.handler;

import com.framework.constant.Constant;
import com.framework.constant.StatusEnum;
import com.framework.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 * 因为程序异常已经在ResponseAspect里面处理过了
 * 此处处理的只是映射错误异常
 *
 * 若要全局映射异常生效
 * 需要再application.yml中禁用SpringBoot原有的处理方式:
 * spring.mvc.throw-exception-if-no-handler-found = true
 * spring.resources.add-mappings = false
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Map<String,Object> errorHandler(Exception e) {

        final Map<String, Object> map = new HashMap<>();

        map.put(Constant.STATUS_STR,StatusEnum.OTHER_ERROR.getCode());
        //如果是路径映射错误,直接返回给前端
        if (e instanceof NoHandlerFoundException) {
            map.put(Constant.MESSAGE_STR,String.format("请求地址{%s}错误,请检查后再次尝试!", SpringContextHolder.getRequest().getRequestURI()));
        } else {
            map.put(Constant.MESSAGE_STR,"服务器异常!");
        }
        log.error("Exception {}",e.getStackTrace()[0].toString());
        return map;
    }

}
