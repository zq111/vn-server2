package com.framework.constant;

/**
 * @author binhao.guo
 * @version 1.0
 * @Date 2018/12/3
 */
public enum  StatusEnum {
    STATUS_SUCCESS(200,"SUCCESS"),
    STATUS_NOLOGIN(201,"未登陆"),
    STATUS_INVALIDTOKEN(202,"token异常"),
    PASSWORD_WRONG(203,"密码错误"),
    INNER_ERROR(204,"方法内部执行错误"),
    NO_BEANS(205,"目标业务模块不存在"),
    NO_METHOD(206,"目标方法不存在"),
    PARAMS_FILE(207,"参数校验失败"),
    NO_CHANNEL(208,"非法的请求渠道"),
    OTHER_ERROR(500,"服务器异常"),

    JWT_ERRCODE_NULL(2021,"token不存在"),
    JWT_ERRCODE_EXPIRE(2022,"token过期"),
    JWT_ERRCODE_FAIL(2023,"token验证失败");

    private int code;
    private String desc;

    private StatusEnum(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static String getDesc(int code) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getCode() == code) {
                return  statusEnum.desc;
            }
        }

        return null;
    }
}
