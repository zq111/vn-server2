package com.framework.Exception;


import com.framework.constant.StatusEnum;
import lombok.Data;

/**
 * 自定义通用异常信息
 */
@Data
public class CommonException extends BaseException{

    private Integer status = StatusEnum.INNER_ERROR.getCode();

    public CommonException(String msg) {
        super(msg);
    }
}
