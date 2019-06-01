package com.framework.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Table   : t_system_menu
 * Date    : 2018-09-18
 * Author  : framework 
 */
@Data
public class SystemMenu implements Serializable {
    /** 
     * 这是注释
     * (主键ID)
     * (无默认值)
     */
    private String uuid;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String isLeaf;

    /** 
     * 这是注释
     * (可选项)
     * (无默认值)
     */
    private Integer level;

    /** 
     * 这是注释
     * (可选项)
     * (无默认值)
     */
    private String menuIcon;

    /** 
     * 这是注释
     * (可选项)
     * (无默认值)
     */
    private String menuName;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String menuRemark;

    /** 
     * 这是注释
     * (可选项)
     * (无默认值)
     */
    private String menuUrl;

    /** 
     * (可选项)
     * (无默认值)
     */
    private String parentUuid;

    /** 
     * 这是注释
     * (可选项)
     * (无默认值)
     */
    private Integer sortNum;

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
    private String isDelete;

    /** 
     * (可选项)
     * (无默认值)
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}