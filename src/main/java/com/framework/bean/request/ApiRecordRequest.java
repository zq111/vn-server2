package com.framework.bean.request;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class ApiRecordRequest {

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    /**
     * 每页展示数量
     */
    @NotNull(message = "每页展示数量不能为空")
    private Integer pageSize;

}
