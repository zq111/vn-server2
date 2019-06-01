package com.framework.Exception;

import com.framework.constant.StatusEnum;
import lombok.Data;

/**
 * 未登录异常
 */
@Data
public class LoginFilterException extends BaseException{

    private Integer status = StatusEnum.STATUS_NOLOGIN.getCode();

    public LoginFilterException(String msg) {
        super(msg);
    }

}
