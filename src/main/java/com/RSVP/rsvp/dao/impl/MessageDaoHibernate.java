package com.RSVP.rsvp.dao.impl;


import com.RSVP.rsvp.dao.BaseDao;
import com.RSVP.rsvp.dao.MessageDao;
import com.RSVP.rsvp.model.Message;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
@Transactional
public class MessageDaoHibernate extends BaseDao implements MessageDao {

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        log.debug("Message list");
        Criteria criteria = currentSession().createCriteria(Message.class);
        Criteria countCriteria = currentSession().createCriteria(Message.class);
        if (params.containsKey("filter")) {
            String filter = (String) params.get("filter");
            Disjunction properties = Restrictions.disjunction();
            properties.add(Restrictions.ilike("name", filter, MatchMode.ANYWHERE));
            properties.add(Restrictions.ilike("subject", filter, MatchMode.ANYWHERE));
            properties.add(Restrictions.ilike("content", filter, MatchMode.ANYWHERE));
            criteria.add(properties);
            countCriteria.add(properties);
        }
        if (params.containsKey("max")) {
            criteria.setMaxResults((Integer) params.get("max"));
            if (params.containsKey("offset")) {
                criteria.setFirstResult((Integer) params.get("offset"));
            }
        }
        if (params.containsKey("sort")) {
            if (((String) params.get("order")).equals("desc")) {
                criteria.addOrder(Order.desc((String) params.get("sort")));
                params.put("order", "asc");
            } else {
                criteria.addOrder(Order.asc((String) params.get("sort")));
                params.put("order", "desc");
            }
        }
        params.put("list", criteria.list());
        countCriteria.setProjection(Projections.rowCount());
        if (!countCriteria.list().isEmpty()) {
            params.put("totalItems", countCriteria.list().get(0));
        } else {
            params.put("totalItems", 0L);
        }
        return params;

    }

    @Transactional(readOnly = true)
    @Override
    public Message get(Long messageId) {
        return (Message) currentSession().get(Message.class, messageId);
    }

    @Override
    public Message update(Message message) {
        currentSession().update(message);
        return message;
    }

    @Override
    public Message get(String name) {
        Query query = currentSession().getNamedQuery("findMessageByName");
        query.setString("name", name);
        return (Message) query.uniqueResult();
    }

    @Override
    public Message create(Message message) {
        currentSession().save(message);
        return message;
    }
}
