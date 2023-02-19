package com.lee.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class BFilterDemo implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 放行前逻辑，对request数据进行处理
        System.out.println("...BFilterDemo...");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        // 放行后逻辑，对response数据进行处理
        System.out.println("...BFilterDemo...");
    }

    @Override
    public void destroy() {

    }
}
