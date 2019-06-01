package com.framework.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


public interface BaseMapper<Model, ModelExample, ID extends Serializable> {


    /**
     * 通过例子查询记录数量
     *
     * @param example
     * @return
     */
    int countByExample(ModelExample example);

    /**
     * 通过例子进行删除
     *
     * @param example
     * @return
     */
    int deleteByExample(ModelExample example);

    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 新增一条记录
     *
     * @param record
     * @return
     */
    int insert(Model record);

    /**
     * 动态新增一条记录
     *
     * @param record
     * @return
     */
    int insertSelective(Model record);

    /**
     * 通过例子查询列表
     *
     * @param example
     * @return
     */
    List<Model> selectByExample(ModelExample example);

    /**
     * 通过主键查询一条记录
     *
     * @param id
     * @return
     */
    Model selectByPrimaryKey(ID id);

    /**
     * 通过例子进行动态更新
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Model record, @Param("example") ModelExample example);

    /**
     * 通过例子进行更新
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Model record, @Param("example") ModelExample example);

    /**
     * 通过主键动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Model record);

    /**
     * 通过主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Model record);
}
