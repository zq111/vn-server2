package com.framework.Exception;

import com.framework.constant.StatusEnum;
import lombok.Data;

/**
 * token异常信息
 */
@Data
public class TokenInvalidException extends BaseException{

    private Integer status = StatusEnum.STATUS_INVALIDTOKEN.getCode();

    public TokenInvalidException(String msg) {
        super(msg);
    }

}
