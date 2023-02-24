package com.RSVP.rsvp.utils;


import com.RSVP.rsvp.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        log.info("User {} has logged in!", username);
        User user = (User) authentication.getPrincipal();
        request.getSession().setAttribute(Constants.LOGGED_USER, user);
    }
}
