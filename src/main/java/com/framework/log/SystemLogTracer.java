package com.framework.log;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by victor on 4/28/16.
 */
@Data
public class SystemLogTracer {

    private String event;
    private Map result = new HashMap();
    private String user;
    private long timestamp;
    private String device;
    private String network;
    private String bucket;
    private String deviceId;
    private String ip;
    private String vc;
    private String id;
    private String packageName;
    private String timezone;
    private boolean debug;
    private String abtest;
    private String region;

    public static final String toJson(SystemLogTracer tracer) {
        return JSON.toJSONString(tracer);
    }


    public void addParam(String key, Object val) {
        this.result.put(key, val);
    }

}
