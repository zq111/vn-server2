package com.framework.service.admin.impl;

import com.framework.bean.request.ApiRecordRequest;
import com.framework.dao.mybatis.mapper.SystemRecordMapper;
import com.framework.dao.mybatis.model.SystemRecord;
import com.framework.dao.mybatis.model.SystemRecordExample;
import com.framework.service.admin.SystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemServiceImpl implements SystemService{

    @Autowired
    private SystemRecordMapper recordMapper;

    @Override
    public PageInfo<List<SystemRecord>> apiRecord(ApiRecordRequest req) throws Exception {

        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        SystemRecordExample example = new SystemRecordExample();
        SystemRecordExample.Criteria criteria = example.createCriteria();

        //条件查询可以类似下面的写法
        //criteria.andChannelIsNull();
        criteria.andChannelEqualTo("web");

        List<SystemRecord> records = recordMapper.selectByExample(example);
        PageInfo<List<SystemRecord>> pi = new PageInfo(records);
        return pi;
    }
}
