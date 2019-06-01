package com.framework.service.app;

import com.framework.bean.response.UserDetailsResponse;
import com.framework.bean.response.UserResponse;

public interface AppUserService {

    /**
     * 用户登录
     * @param reqJson
     * @return
     * @throws Exception
     */
    UserResponse login(String reqJson) throws Exception;

    /**
     * 根据uuid获取用户详情
     * @param reqJson
     * @return
     * @throws Exception
     */
    UserDetailsResponse getUserDetais(String reqJson) throws Exception;

}
