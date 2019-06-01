package com.framework.service.admin.impl;

import com.framework.bean.request.BaseRequest;
import com.framework.bean.request.LoginRequest;
import com.framework.bean.response.CommonResponse;

import com.framework.service.admin.AdminUserService;
import com.framework.utils.GsonTools;
import com.framework.utils.SpringContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {



    public CommonResponse login(LoginRequest req) throws Exception {
        CommonResponse resp = new CommonResponse();
        resp.setMsg("登录成功");
        return resp;
    }

    @Override
    public CommonResponse logout(BaseRequest req) throws Exception {
        CommonResponse resp = new CommonResponse();
        resp.setFlag(true);
        resp.setMsg("退出成功!");
        return resp;
    }

}
