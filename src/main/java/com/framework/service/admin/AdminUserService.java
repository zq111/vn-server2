package com.framework.service.admin;

import com.framework.bean.request.BaseRequest;
import com.framework.bean.request.LoginRequest;
import com.framework.bean.response.CommonResponse;

import java.util.List;

public interface AdminUserService {

    /**
     * 登录模拟
     * @param req
     * @return
     * @throws Exception
     */
    CommonResponse login(LoginRequest req) throws Exception;

    /**
     * 登出模拟
     * @param req
     * @return
     * @throws Exception
     */
    CommonResponse logout(BaseRequest req) throws Exception;

}
