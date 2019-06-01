package com.framework.Exception;

import com.framework.constant.StatusEnum;
import lombok.Data;


/**
 * 密码错误异常
 */
@Data
public class PasswordException extends BaseException{

    private Integer status = StatusEnum.PASSWORD_WRONG.getCode();

    public PasswordException(String msg) {
        super(msg);
    }

}
