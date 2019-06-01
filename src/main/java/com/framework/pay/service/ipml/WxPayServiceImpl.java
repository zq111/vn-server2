package com.framework.pay.service.ipml;

import com.framework.bean.response.CommonResponse;
import com.framework.pay.service.BasePayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WxPayServiceImpl implements BasePayService{

    @Override
    public CommonResponse initNativePay(String reqJson) throws Exception {
        return null;
    }

    @Override
    public CommonResponse initWapPay(String reqJson) throws Exception {
        return null;
    }

    @Override
    public String payNotify() {
        return null;
    }

    @Override
    public String refundNotify() {
        return null;
    }
}
