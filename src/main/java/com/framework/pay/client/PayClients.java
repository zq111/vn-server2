package com.framework.pay.client;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.framework.conf.properties.SystemProperties;
import com.framework.utils.SpringContextHolder;

/**
 * ali/wx支付客户端
 */
public class PayClients {

    public static AlipayClient alipayClient;
    public static AlipayClient wechatClient;
    static {
        SystemProperties properties = SpringContextHolder.getBean("systemProperties");
        //实例化客户端
        String serverUrl = properties.getServerUrl();
        String appId = properties.getAppId();
        String privateKey = properties.getPrivateKey();
        String format = properties.getFormat();
        String charset = properties.getCharest();
        String aliPayPublicKey = properties.getAliPyaPublicKey();
        String signType = properties.getSignType();
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, aliPayPublicKey, signType);
        PayClients.alipayClient = alipayClient;
    }
}
