package com.RSVP.rsvp.service;

import com.RSVP.rsvp.model.Connection;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;

import java.util.Map;

public interface UserService {

    public User get(String username);

    public User getByOpenId(String openId);

    public User update(User user);

    public User create(User user);

    public Role getRole(String authority);

    public Connection getConnection(String username);

    public Map<String, Object> list(Map<String, Object> params);

    public User get(Long userId);

    public String delete(Long userId);

}
