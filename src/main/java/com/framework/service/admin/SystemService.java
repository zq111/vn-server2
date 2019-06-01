package com.framework.service.admin;

import com.framework.bean.request.ApiRecordRequest;
import com.framework.dao.mybatis.model.SystemRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统接口模块
 */
public interface SystemService {

    PageInfo<List<SystemRecord>> apiRecord(ApiRecordRequest req) throws Exception;

}
