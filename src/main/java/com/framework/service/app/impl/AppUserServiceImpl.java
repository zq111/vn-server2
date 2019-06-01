package com.framework.service.app.impl;


import com.framework.annotations.TokenValidate;
import com.framework.bean.request.BaseRequest;
import com.framework.bean.request.LoginRequest;
import com.framework.bean.response.UserDetailsResponse;
import com.framework.bean.response.UserResponse;
import com.framework.service.app.AppUserService;
import com.framework.utils.GsonTools;
import com.framework.utils.jwt.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Override
    public UserResponse login(String reqJson) throws Exception {
        LoginRequest req = GsonTools.str2T(reqJson,LoginRequest.class);
        UserResponse resp = new UserResponse();

        if (req.getUsername().equals("admin") && req.getPassword().equals("123456")) {
            String tokenb = JwtUtils.createJWT("111", "admin", 10000000);
            resp.setToken(tokenb);
            resp.setFlag(true);
            resp.setMsg("登录成功");
        } else {
            resp.setFlag(false);
            resp.setMsg("登录失败");
        }

        return resp;
    }

    @Override
    @TokenValidate
    public UserDetailsResponse getUserDetais(String reqJson) throws Exception {
        BaseRequest req = GsonTools.str2T(reqJson,BaseRequest.class);
        UserDetailsResponse resp = new UserDetailsResponse();
        //详装取到数据了,这里暂时略过,主要验证tokenValidation的使用

        resp.setUsername("admin");
        resp.setAge("20");
        resp.setSex("0");
        resp.setUuid("admin");
        return resp;
    }
}
