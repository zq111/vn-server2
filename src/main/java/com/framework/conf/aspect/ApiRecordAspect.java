//package com.framework.conf.aspect;
//
//import com.framework.constant.Constant;
//import com.framework.dao.mybatis.mapper.SystemRecordMapper;
//import com.framework.dao.mybatis.model.SystemRecord;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StopWatch;
//import org.springframework.util.StringUtils;
//
//import java.util.Date;
//import java.util.Map;
//
//import static com.framework.utils.IpUtil.getIpAddr;
//
///**
// * 利用切面,记录用户请求以及响应数据
// * 因为在请求异常时也要记录请求及响应数据,
// * 所以此切面必须放在ExceptionHandlerAspect之前执行
// * 当真正的请求执行完,抛出异常后,则是先由ExceptionHandlerAspect处理
// * 顺序类似intercept -------------->
// * 1 --> 2 --> 真正的执行方法 --> 2 --> 1
// */
//@Slf4j//引入日志
//@Aspect//声明切面
//@Component//声明组件
//@Order(1)//指定多个切面执行顺序
//@Transactional//交给spring处理事务
//public class ApiRecordAspect {
//
//    final SystemRecordMapper recordMapper;
//
//    @Autowired
//    public ApiRecordAspect(SystemRecordMapper recordMapper) {
//        this.recordMapper = recordMapper;
//    }
//
//    //定义切点
//    @Pointcut("execution(public * com.framework.controller.*.*(..))")
//    public void apiLog() {
//    }
//
//    //定义通知
//    @Around("apiLog()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        //创建定时器
////        StopWatch watch = new StopWatch();
////        watch.start();
////
////        //获取AdapterController的请求参数,分别是channel,module,method,requestDate
////        Object[] args = pjp.getArgs();
////
////        String channel = args[0].toString();
////        String module = args[1].toString();
////        String method = args[2].toString();
////        String reqData = StringUtils.isEmpty(args[3]) ? "" : args[3].toString();
////        String ip = getIpAddr();
////
////        //执行真正的请求
////        //此处获取的data已经是经过ResponseHandler处理过的数据,无需再封装处理
//        Object data = pjp.proceed();
////
////        watch.stop();
////        //从计时器获取当前请求执行时间
////        String costTime = String.valueOf(watch.getTotalTimeMillis());
////
////        //记录请求日志
////        SystemRecord record = new SystemRecord();
////        record.setChannel(channel);
////        record.setCostTime(costTime);
////        record.setCreateDate(new Date());
////        record.setIsDelete(0);
////        record.setMethod(method);
////        record.setModule(module);
////        record.setReqData(reqData);
////        record.setRespData(data.toString().length() > 1000 ? "tooLong":data.toString());
////        record.setSourceIp(ip);
////        record.setUpdateDate(new Date());
////
////        Map map = (Map) data;
////        record.setStatus(map.get(Constant.STATUS_STR).toString());
////
////        recordMapper.insert(record);
//
//        return data;
//    }
//}