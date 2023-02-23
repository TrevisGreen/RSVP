package com.RSVP.rsvp.dao;

import com.RSVP.rsvp.model.Connection;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;

import java.util.Map;

public interface UserDao {

    public User get(String username);

    public User getByOpenId(String openId);

    public User update(User user);

    public Role getRole(String authority);

    public Role createRole(Role role);

    public User create(User user);

    public Connection getConnection(String username);

    public Map<String, Object> list(Map<String, Object> params);

    public User get(Long userId);

    public void delete(User user);
}
