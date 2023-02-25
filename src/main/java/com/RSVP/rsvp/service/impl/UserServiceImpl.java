package com.RSVP.rsvp.service.impl;


import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.Connection;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.BaseService;
import com.RSVP.rsvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Transactional
    @Override
    public User getByOpenId(String openId) {
        return userDao.getByOpenId(openId);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public Role getRole(String authority) {
        return userDao.getRole(authority);
    }

    @Override
    public Connection getConnection(String username) {
        return userDao.getConnection(username);
    }

    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        return userDao.list(params);
    }

    @Override
    public String delete(long userId) {
        User user = userDao.get(userId);
        userDao.delete(user);
        return user.getUsername();
    }
}
