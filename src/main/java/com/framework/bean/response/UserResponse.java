package com.framework.bean.response;

import lombok.Data;

@Data
public class UserResponse  extends CommonResponse{

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户token
     */
    private String token;

}
