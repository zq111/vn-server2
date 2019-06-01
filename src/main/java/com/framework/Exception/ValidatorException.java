package com.framework.Exception;

import com.framework.constant.StatusEnum;
import lombok.Data;

/**
 * 参数校验异常
 */
@Data
public class ValidatorException extends BaseException{

    private Integer status = StatusEnum.PARAMS_FILE.getCode();

    public ValidatorException(String msg) {
        super(msg);
    }

}
