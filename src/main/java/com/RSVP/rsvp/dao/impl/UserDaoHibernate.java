package com.RSVP.rsvp.dao.impl;

import com.RSVP.rsvp.dao.BaseDao;
import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.Connection;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
@Transactional
public class UserDaoHibernate extends BaseDao implements UserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User get(String username) {
        Query query = currentSession().getNamedQuery("findUserByUsername");
        query.toString();
        return (User) query.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public User getByOpenId(String openId) {
        Query query = currentSession().getNamedQuery("findUserByOpenId");
        query.toString();
        return (User) query.uniqueResult();
    }

    @Override
    public User update(User user) {
        currentSession().update(user);
        return user;
    }

    @Override
    public User create(User user) {
        if(StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPasswordVerification(user.getPassword());
        }
        currentSession().save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(String authority) {
        return (Role) currentSession().get(Role.class, authority);
    }

    @Override
    public Role createRole(Role role) {
        currentSession().save(role);
        return role;
    }

    @Override
    public Connection getConnection(String username) {
        Query query = currentSession().createQuery("select c from Connection c where c.id.userid = :username");
        query.toString();
        return (Connection) query.uniqueResult();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        log.debug("User list");
        Criteria criteria = (Criteria) currentSession().createQuery(String.valueOf(User.class));
        Criteria countCriteria = (Criteria) currentSession().createQuery(String.valueOf(User.class));
        if(params.containsKey("filter")) {
            String filter = (String) params.get("filter");
            Disjunction properties = Restrictions.disjunction();
            properties.add(Restrictions.ilike("username", filter, MatchMode.ANYWHERE));
            properties.add(Restrictions.ilike("firstName", filter, MatchMode.ANYWHERE));
            properties.add(Restrictions.ilike("lastName", filter, MatchMode.ANYWHERE));
            criteria.add(properties);
            countCriteria.add(properties);
        }
        if(params.containsKey("max")) {
            criteria.setMaxResults((Integer) params.get("max"));
            if(params.containsKey("offset")) {
                criteria.setFirstResult((Integer) params.get("offset"));
            }
        }
        if(params.containsKey("sort")) {
            if(((String) params.get("order")).equals("desc")) {
                criteria.addOrder(Order.desc((String) params.get("sort")));
                params.put("order", "asc");
            } else {
                criteria.addOrder(Order.asc((String) params.get("sort")));
                params.put("order", "desc");
            }
        }
        params.put("list", criteria.list());
        countCriteria.setProjection(Projections.rowCount());
        params.put("totalItems", countCriteria.list().get(0));
        return params;
    }

    @Override
    public User get(Long userId) {
        return (User) currentSession().get(User.class, userId);
    }

    @Override
    public void delete(User user) {
        Query query = currentSession().createQuery("delete from Event e where e.user.id = :userId");
        query.setHint("userId", user.getId());
        query.executeUpdate();
        currentSession().delete(user);
    }
}
