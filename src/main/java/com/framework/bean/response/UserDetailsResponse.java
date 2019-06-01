package com.framework.bean.response;

import lombok.Data;

@Data
public class UserDetailsResponse {

    /**
     * 用户uuid
     */
    private String uuid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别 0男 1女
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

}
