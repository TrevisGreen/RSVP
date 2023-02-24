package com.RSVP.rsvp.service.impl;

import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SocialUserDetailsServiceImpl extends BaseService implements SocialUserDetailsServiceImpl {

    private final UserDetailsService userDetailsService;

    @Autowired
    private UserDao userDao;

    public SocialUserDetailsServiceImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        log.debug("Loading user by user id : {}", userId);

        // UserDetails user = userDetailsService.loadUserByUsername(userId);
        User user = userDao.get(userId);
        log.debug("Found user details: {}",user);

        return (SocialUserDetails) user
    }
}
