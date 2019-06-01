package com.framework.conf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置文件,从application获取
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "system.properties")
@EnableConfigurationProperties(SystemProperties.class)
public class SystemProperties {

    /**
     * 初始化后台管理员用户名
     */
    private String username;

    /**
     * 初始化后台管理员密码
     */
    private String password;

    /**
     * 支付宝支付地址
     */
    private String  serverUrl;

    /**
     * 商户APPID
     */
    private String  appId;

    /**
     * 私钥
     */
    private String  privateKey;

    /**
     * 公钥
     */
    private String  aliPyaPublicKey;

    /**
     * 格式化
     */
    private String  format;

    /**
     * 编码
     */
    private String  charest;

    /**
     * 签名方法
     */
    private String  signType;

}
