//package com.framework.conf.aspect;
//
//import com.framework.Exception.BaseException;
//import com.framework.Exception.CommonException;
//import com.framework.constant.Constant;
//import com.framework.constant.StatusEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.BeansException;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.util.StringUtils;
//
///**
// * 响应包装类
// * 将处理结果包装为map
// * 将异常信息包装为状态码
// */
//@Slf4j//引入日志
//@Aspect//声明切面
//@Component//声明组件
//@Order(2)//指定多个切面执行顺序
//@Transactional//声明事务
//public class ResponseHandlerAspect {
//
//    //定义切点
//    @Pointcut("execution(public * com.framework.controller.*.*(..))")
//    public void apiLog() {
//    }
//
//    //定义切面
//    @Around("apiLog()")
//    public Map<String, Object> around(ProceedingJoinPoint pjp) throws Exception{
//
//        //获取AdapterController的请求参数,分别是channel,module,method,requestDate
//        Object[] args = pjp.getArgs();
//
//        //String channel = args[0].toString();
//
//        //封装响应结果
//        Map<String, Object> map = new HashMap<>();
//        Integer status = StatusEnum.STATUS_SUCCESS.getCode();
//        String  message = StatusEnum.STATUS_SUCCESS.getDesc();
//        Object data = null;
//        try {
//
//            //如果请求来源为空,或者请求来源不是预定义的请求来源,则抛出异常
////            if (StringUtils.isEmpty(channel) || (!channel.equals(Constant.API_CHANNEL_ADMIN)
////                    && !channel.equals(Constant.API_CHANNEL_ANDRIID) && !channel.equals(Constant.API_CHANNEL_IOS)
////                    && !channel.equals(Constant.API_CHANNEL_WEB))) {
////                throw new CommonException(StatusEnum.NO_CHANNEL.getDesc());
////            }
//
//            //执行真正的请求
//            data =  pjp.proceed();
//
//        } catch (CommonException e) {
//            status = StatusEnum.NO_CHANNEL.getCode();
//            message = e.getMessage();
//        } catch (BeansException e) {
//            status = StatusEnum.NO_BEANS.getCode();
//            message = StatusEnum.NO_BEANS.getDesc();
//        } catch (NoSuchMethodException e) {
//            status = StatusEnum.NO_METHOD.getCode();
//            message = StatusEnum.NO_METHOD.getDesc();
//        } catch (InvocationTargetException e) {
//
//            if (e.getTargetException() instanceof BaseException) {
//
//                //使用多态,运行时获取对应的exception,将exception对应的status状态码与错误信息返回给前端
//                BaseException baseException = (BaseException) e.getTargetException();
//                status = baseException.getStatus();
//                message = e.getTargetException().getMessage();
//
//            } else {
//
//                //其他错误
//                status = StatusEnum.INNER_ERROR.getCode();
//                message = StatusEnum.INNER_ERROR.getDesc();
//
//                //如果是其他错误,或者exception,将exception携带至前端,以便调试
//                map.put(Constant.EXCEPTION_STR, e.getTargetException().getStackTrace());
//
//                log.error("InvocationTargetException {}", e);
//            }
//
//        } catch (Exception e) {
//
//            status = StatusEnum.OTHER_ERROR.getCode();
//            message = StatusEnum.OTHER_ERROR.getDesc();
//
//            //如果是其他错误,或者exception,将exception携带至前端,以便调试
//            map.put(Constant.EXCEPTION_STR, e.getStackTrace());
//
//            log.error("Exception {}", e);
//        }catch (Throwable throwable) {
//            throwable.printStackTrace();
//            if (log.isDebugEnabled()) {
//                log.error("保存记录出错 {}",throwable);
//            }
//            map.put(Constant.STATUS_STR,500);
//            map.put(Constant.MESSAGE_STR,"请求异常,Throwable错误");
//            return map;
//        }
//        map.put(Constant.STATUS_STR,status);
//        map.put(Constant.MESSAGE_STR,message);
//        map.put(Constant.DATA_STR,data);
//
//        return map;
//    }
//}