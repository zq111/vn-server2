package com.framework.pay.service;


import com.framework.bean.response.CommonResponse;

public interface BasePayService {

    /**
     * 初始化APP支付，获取支付参数
     * @param reqJson
     * @return
     * @throws Exception
     */
    CommonResponse initNativePay(String reqJson) throws Exception;


    /**
     * 初始化Web支付,获取支付参数
     * @param reqJson
     * @return
     * @throws Exception
     */
    CommonResponse initWapPay(String reqJson) throws Exception;

    /**
     * 支付完成后，第三方支付平台的支付结果通知入口
     * @return
     */
    String payNotify();

    /**
     * 申请退款后，退款结果通知入口
     *
     * @return
     */
    String refundNotify();
}
