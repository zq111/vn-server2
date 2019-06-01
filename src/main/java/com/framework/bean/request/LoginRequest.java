package com.framework.bean.request;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class LoginRequest {

    /**
     * 登录用户名称
     */
    @NotNull(message = "用户名不允许为空")
    private String username;

    /**
     * 登录用户密码
     */
    @NotNull(message = "密码不允许为空")
    private String password;

}
