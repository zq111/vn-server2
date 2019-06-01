package com.framework.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Table   : t_system_record
 * Date    : 2018-12-04
 * Author  : framework 
 */
@Data
public class SystemRecord implements Serializable {
    /** 
     * (主键ID)
     * (无默认值)
     */
    private String uuid;

    /** 
     * (可选项)
     * (无默认值)
     */
    private Date createDate;

    /** 
     * (可选项)
     * (无默认值)
     */
    private Date deleteDate;

    /** 
     * (可选项)
     * (无默认值)
     */
    private Integer isDelete;

    /** 
     * (可选项)
     * (无默认值)
     */
    private Date updateDate;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String channel;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String costTime;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String memberuuid;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String method;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String module;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String reqData;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String respData;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String sourceIp;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String status;

    private static final long serialVersionUID = 1L;
}