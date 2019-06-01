//package com.framework.conf.aspect;
//
//
///**
// * 说明:
// *  此切面会切service下包含@TokenValidation注解的方法,在此方法中获取请求header中的token用以校验,
// *  校验通过则放行,失败则返回对应信息,用以对用户登录的验证
// */
//
//import com.framework.Exception.TokenInvalidException;
//import com.framework.constant.StatusEnum;
//import com.framework.utils.jwt.CheckResult;
//import com.framework.utils.jwt.JwtUtils;
//import com.framework.utils.SpringContextHolder;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.lang.annotation.Annotation;
//
//@Slf4j//引入日志
//@Aspect//声明切面
//@Component//声明组件
//@Order(1)//指定多个切面执行顺序
//@Transactional//交给spring处理事务
//public class JwtTokenAspect {
//
//    //定义切点
//    @Pointcut("execution(public * com.framework.service.*.*.*(..)) && @annotation(com.framework.annotations.TokenValidate)")
//    public void tokenValidation() {
//    }
//
//    @Before("tokenValidation()")
//    public void validation(JoinPoint joinPoint)throws Exception{
//        System.out.println("我是前置通知");
//        Object aThis = joinPoint.getThis();// AOP代理类信息
//        joinPoint.getTarget(); // 代理的目标对象
//        Signature signature=joinPoint.getSignature(); //  用的最多，通知的签名
//        System.out.println("代理的方法是 ： "+signature.getName()); //  打印 代理的是哪一个方法
//        // AOP 代理的名字
//        System.out.println("AOP 代理的名字 ： "+signature.getDeclaringTypeName());
//        Class clazz = signature.getDeclaringType();//  AOP代理类的类（class）信息
//
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Annotation[] annotations = methodSignature.getMethod().getAnnotations();
//        for (Annotation annotation : annotations) {
//            System.out.println(annotation.toString());
//        }
//
//        String token = SpringContextHolder.getRequest().getHeader("token");
//        CheckResult validate = JwtUtils.validateJWT(token);
//
//        /**
//         * 验证失败,抛出Token对应的异常信息
//         */
//        if (!validate.isSuccess()) {
//
//            throw new TokenInvalidException(StatusEnum.getDesc(validate.getErrCode()));
//
//        }
//
//    }
//}
