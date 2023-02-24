package com.RSVP.rsvp.service.impl;

import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;

import java.util.List;

public class UserDetailsServiceImpl <OpenIDAuthenticationToken extends Authentication> extends BaseService implements UserDetailsService,
        AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.debug("loadUserByUsername: {}", username);
        User user = userDao.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user "
                    + username);
        }
        return (UserDetails) user;
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
            throws UsernameNotFoundException {
        log.debug("loadUserDetails: {}", token);
        String username = token.getIdentityUrl();
        String email = "";
        User user = userDao.getByOpenId(username);
        log.debug("Found user : {}", user);
        if (user == null) {
            log.debug("Looking for email attribute");
            List<OpenIDAttribute> attrs = token.getAttributes();
            for (OpenIDAttribute attr : attrs) {
                log.debug("Attr: {}", attr.getName());
                if (attr.getName().equals("email")) {
                    email = attr.getValues().get(0);
                }
            }
            log.debug("Looking for email {}", email);
            user = userDao.get(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "Could not find user " + email);
            }
            user.setOpenId(username);
            userDao.update(user);
        }
        log.debug("Returning user: {}", user);
        return (UserDetails) user;
    }
}
