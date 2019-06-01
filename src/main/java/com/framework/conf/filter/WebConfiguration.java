package com.framework.conf.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {

        /**
         * 需要登录才能查看的页面配置
         * 支持通配符,以逗号隔开
         */
        StringBuffer includeUriStr = new StringBuffer();
        includeUriStr.append("/main.html");

        /**
         * 将过滤器添加至spring boot容器中
         */
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SystemUserFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("includeUri", includeUriStr.toString());
        registration.setName("SystemUserFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 后台登录过滤器
     */
    public class SystemUserFilter implements Filter {

        private String[] includeUris;

        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) srequest;
            HttpServletResponse response = (HttpServletResponse) sresponse;
            //获取用户请求的uri地址
            String uri = request.getServletPath();

            //判断是否需要登录验证
            if(!isIncludeUriUri(uri) || request.getSession().getAttribute("user")!=null){
                //如果不包含在登录验证uri里面,或者包含用户已经登录,则放行
                filterChain.doFilter(srequest, sresponse);
            }else{
                //否则重定向到登录界面,将来源地址记录至session,以便在登录成功以后直接跳转到登录前要访问的页面
                request.getSession().setAttribute("fromUri",uri);
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // 过滤器初始化,将需要登录验证的url解析成数组形式
            includeUris = filterConfig.getInitParameter("includeUri").split(",");
        }

        private boolean isIncludeUriUri(String uri) {
            if (includeUris == null || includeUris.length <= 0) {
                return false;
            }
            for (String ex : includeUris) {
                uri = uri.trim();
                ex = ex.trim();
                if (uri.toLowerCase().matches(ex.toLowerCase().replace("*",".*")))
                    return true;
            }
            return false;
        }
    }


}
