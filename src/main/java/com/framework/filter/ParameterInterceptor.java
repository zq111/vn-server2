package com.framework.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);


    public static String getIpAddr(HttpServletRequest request) {
        //
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info(String.format("request-url : %s?%s", request.getRequestURL(), request.getQueryString()));


        String ip = getIpAddr(request);
        String uid = request.getParameter("uid");
        String packageName = request.getParameter("from");
        String sdkVc = request.getParameter("vc");
        if (StringUtils.isBlank(uid) || StringUtils.isBlank(packageName)) {
            return false;
        }

        String device = request.getParameter("dm");
        String region = request.getParameter("region");
        String netWork = request.getParameter("nw");
        String channel = request.getParameter("lc");
        String timeZone = request.getParameter("tz");
        String aq = request.getParameter("aq");
        String sysVer = request.getParameter("sv");
        String sex = request.getParameter("sex");

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.debug("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
