package com.framework.bean.response;

import lombok.Data;

import java.util.List;


@Data
public class MenuResponse {

    private String uuid;

    private String menuName;

    private String menuValue;

    private String menuIcon;

    private Integer sortNum;
}
