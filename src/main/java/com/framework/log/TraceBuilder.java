package com.framework.log;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by victor on 5/3/16.
 */
public class TraceBuilder {

    private SystemLogTracer tracer;

    public TraceBuilder() {
        tracer = new SystemLogTracer();
        tracer.setTimestamp(System.currentTimeMillis());
    }

    public TraceBuilder(String event) {
        this();
        event(event);
    }

    public TraceBuilder debug(boolean debug) {
        tracer.setDebug(debug);
        return this;
    }

    public TraceBuilder debug(HttpServletRequest request) {
        boolean debug = false;
        if (request.getParameter("isDebug") != null) {
            debug = Boolean.parseBoolean(request.getParameter("isDebug"));
        }
        tracer.setDebug(debug);
        return this;
    }

    public TraceBuilder event(String event) {
        tracer.setEvent(event);
        return this;
    }

    public TraceBuilder param(String key, String val) {
        tracer.addParam(key, val);
        return this;
    }

    public TraceBuilder user(HttpServletRequest request) {
        String user = null;
        if (request.getParameter("uid") != null) {
            user = request.getParameter("uid");
        } else {
            user = request.getParameter("udid");
        }
        tracer.setUser(user);
        tracer.setDeviceId(request.getParameter("udid"));
        return this;
    }

    public TraceBuilder user(String id) {
        tracer.setUser(id);
        return this;
    }

    public TraceBuilder id(HttpServletRequest request) {
        String id = null;
        if (request.getParameter("idfa") != null) {
            id = request.getParameter("idfa");
        }
        tracer.setId(id);
        return this;
    }

    public TraceBuilder packageName(HttpServletRequest request) {
        String packageName = null;
        if (request.getParameter("from") != null) {
            packageName = request.getParameter("from");
        }
        tracer.setPackageName(packageName);
        return this;
    }

    public TraceBuilder bucket(HttpServletRequest request) {
        if (request.getParameter("bucket") != null) {
            tracer.setBucket(request.getParameter("bucket"));
        }
        return this;
    }

    public TraceBuilder abtest(HttpServletRequest request) {
        if (request.getParameter("ab_test") != null) {
            tracer.setAbtest(request.getParameter("ab_test"));
        }
        return this;
    }

    public TraceBuilder bucket(String bucket) {
        tracer.setBucket(bucket);
        return this;
    }

    public TraceBuilder ip(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                tracer.setIp(ip.substring(0, index));
            } else {
                tracer.setIp(ip);
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            tracer.setIp(ip);
        }
        return this;
    }

    public TraceBuilder vc(HttpServletRequest request) {
        if (request.getParameter("vc") != null) {
            tracer.setVc(request.getParameter("vc"));
        }
        return this;
    }


    public TraceBuilder device(HttpServletRequest request) {
        if (request.getParameter("dm") != null) {
            tracer.setDevice(request.getParameter("dm"));
        }
        return this;
    }

    public TraceBuilder region(HttpServletRequest request) {
        if (request.getParameter("region") != null) {
            tracer.setRegion(request.getParameter("region"));
        }
        return this;
    }

    public TraceBuilder netWork(HttpServletRequest request) {
        if (request.getParameter("nw") != null) {
            tracer.setNetwork(request.getParameter("nw"));
        }
        return this;
    }

    public TraceBuilder tz(HttpServletRequest request) {
        if (request.getParameter("tz") != null) {
            tracer.setTimezone(request.getParameter("tz"));
        }
        return this;
    }


    public SystemLogTracer build() {
        return tracer;
    }

    public static SystemLogTracer build(HttpServletRequest request, String event) {
        SystemLogTracer tracer = new TraceBuilder(event).user(request).debug(request)
                .bucket(request).ip(request).vc(request).id(request).device(request).packageName(request)
                .netWork(request).tz(request).abtest(request).region(request).build();
        return tracer;
    }
}
