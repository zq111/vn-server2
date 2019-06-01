package com.framework.dao.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.StringUtils;

/**
 * 生成数据库字段注释
 * 类名前加自定义注释
 * 注释增加数据字段是否为必填项
 * 注释增加数据字段是否有默认值
 * 注释增加属性是否为主健
 */
public class RemarksCommentGenerator extends DefaultCommentGenerator {

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * Table   : " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * Date    : " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        topLevelClass.addJavaDocLine(" * Author  : framework ");
        topLevelClass.addJavaDocLine(" */");
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        /**
         * 获取数据库字段注释
         */
        String remark = introspectedColumn.getRemarks();

        field.addJavaDocLine("/** ");
        if(!StringUtils.isEmpty(remark)) {
            field.addJavaDocLine(" * ".concat(remark));
        }

        String columnName = introspectedColumn.getActualColumnName();
        List<IntrospectedColumn> primaryKey = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn pk : primaryKey) {
            if (columnName.equals(pk.getActualColumnName())) {
                field.addJavaDocLine(" * (主键ID)");
                continue;    //主健属性上无需生明可选项跟必填项介绍
            }

            field.addJavaDocLine(introspectedColumn.isNullable() ? " * (可选项)" : " * (必填项)");
        }
        String defaultValue = introspectedColumn.getDefaultValue();
        field.addJavaDocLine(null != defaultValue ? " * (默认值为: " + defaultValue + ")" : " * (无默认值)");
        field.addJavaDocLine(" */");
    }
}