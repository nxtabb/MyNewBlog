package com.hrbeu.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname CharacterFilter
 * @Description TODO
 * @Date 2021/4/14 10:40
 * @Created by nxt
 */
public class CharacterFilter implements Filter {
    private static final Logger logger = Logger.getLogger(CharacterFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("characterFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("characterFilter working");
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("characterFilter destroy");
    }
}
