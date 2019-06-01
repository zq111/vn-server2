package com.framework.dao.mybatis.model;

import lombok.Data;

@Data
public class ServerState {

    private long id;
    private String serverIp;
    private int serverLoad;
    private String serverMethod;
    private String serverPort;
    private String serverPass;
    private long gmtModify;
    private long gmtCreate;

}
