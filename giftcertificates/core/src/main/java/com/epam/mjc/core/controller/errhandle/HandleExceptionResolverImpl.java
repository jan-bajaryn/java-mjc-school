package com.epam.mjc.core.controller.errhandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandleExceptionResolverImpl implements org.springframework.web.servlet.HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(HandleExceptionResolverImpl.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("Exception thrown:", ex);

        return new ModelAndView();
    }
}
