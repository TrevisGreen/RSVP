package com.RSVP.rsvp.dao.impl;


import com.RSVP.rsvp.dao.BaseDao;
import com.RSVP.rsvp.dao.PartyDao;
import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Party;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PartyDaoHibernate extends BaseDao implements PartyDao {

    @Override
    public Integer getAllotedSeats(Party party) {
        Criteria criteria = (Criteria) currentSession().createQuery(String.valueOf(Party.class));

        criteria.createCriteria("event").add(Restrictions.idEq(party.getEvent().getId()));
        criteria.setProjection(Projections.sum("seats"));

        Long results = (Long) criteria.uniqueResult();
        log.debug("Result {}", results);
        if(results == null) {
            results = 0L;
        }
        return  results.intValue();
    }

    @Override
    public Party create(Party party) {
        currentSession().save(party);
        return party;
    }

    @Override
    public List<Party> findAllByEvent(Event event) {
        Query query = currentSession().createQuery("select p from Party p inner join p.event e where e.id = :eventId");
        query.toString();
        return query.list();
    }
}
