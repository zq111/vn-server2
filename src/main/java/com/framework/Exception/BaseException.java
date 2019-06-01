package com.framework.Exception;

import com.framework.constant.StatusEnum;
import lombok.Data;

@Data
public class BaseException extends Exception{

    private Integer status = StatusEnum.STATUS_SUCCESS.getCode();

    public BaseException(String msg) {
        super(msg);
    }
}
