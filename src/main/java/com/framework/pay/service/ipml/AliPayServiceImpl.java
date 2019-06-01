package com.framework.pay.service.ipml;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.framework.bean.response.CommonResponse;
import com.framework.pay.client.PayClients;
import com.framework.pay.service.BasePayService;
import com.framework.utils.SpringContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class AliPayServiceImpl implements BasePayService{

    @Override
    public CommonResponse initNativePay(String reqJson) throws Exception {
        CommonResponse resp = new CommonResponse();
        //此处从请求中获取订单号
        //封装请求参数
        //加密生成对应的支付签名
        //返回给客户端,用以拉取支付
        AlipayClient alipayClient = PayClients.alipayClient;
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("test");
        model.setSubject("App Java");
        model.setOutTradeNo("11");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("111");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            resp.setMsg(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public CommonResponse initWapPay(String reqJson) throws Exception {
        //参见初始化APP支付
        return null;
    }

    @Override
    public String payNotify() {
        //通知结果
        String  result = "fail";

        //获取通知请求中的参数
        Map<String, String[]> parameterMap = SpringContextHolder.getRequest().getParameterMap();
        //此处解析并执行相应的业务处理逻辑

        return result;
    }

    @Override
    public String refundNotify() {

        //退款通知参见支付通知
        return null;
    }
}
