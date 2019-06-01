package com.framework.dao.mybatis.mapper;

import com.framework.dao.mybatis.BaseMapper;
import com.framework.dao.mybatis.model.SystemMenu;
import com.framework.dao.mybatis.model.SystemMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemMenuMapper extends BaseMapper<SystemMenu, SystemMenuExample, String> {
}