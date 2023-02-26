package com.RSVP.rsvp.config;


import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(1)
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}
