package com.framework.pay.controller;

import com.framework.pay.service.BasePayService;
import com.framework.utils.SpringContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;


/**
 * 支付通知接口
 * 包括支付成功通知 退款成功通知
 * 返回SUCCESS通知第三方支付机构收到支付通知
 */
@RestController
@RequestMapping("payNotify")
public class PayController {

    /**
     * methodName: payNotify 支付通知 refundNotify 退款通知
     * payWay支付渠道wx/ali
     *
     * @param methodName
     * @param payWay
     * @return
     */
    @RequestMapping("{methodName}/{payWay}")
    public String payNotify(@PathVariable String methodName, @PathVariable String payWay) {
        BasePayService payService;
        String result = "fail";
        try {
            //根据支付渠道获取实际对应的Bean
            String className = payWay + "PayServiceImpl";
            payService = SpringContextHolder.getBean(className);
            //根据回调参数
            Method method = payService.getClass().getMethod(methodName, String.class);
            result = (String) method.invoke(payService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
