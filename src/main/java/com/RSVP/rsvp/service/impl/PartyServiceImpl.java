package com.RSVP.rsvp.service.impl;


import com.RSVP.rsvp.dao.EventDao;
import com.RSVP.rsvp.dao.PartyDao;
import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Party;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.BaseService;
import com.RSVP.rsvp.service.PartyService;
import com.RSVP.rsvp.utils.NotEnoughSeatsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PartyServiceImpl  extends BaseService implements PartyService {

    @Autowired
    private PartyDao partyDao;
    @Autowired
    EventDao eventDao;

    @Override
    public Party create(Party party) throws NotEnoughSeatsException {
        Event event = eventDao.get(party.getEvent().getId());
        if(event.getSeats() > 0) {
            Integer allotedSeats = partyDao.getAllotedSeats(party);
            log.debug("AllotedSeats: {} | {} | {}", new Object[] {allotedSeats, party.getSeats(), event.getSeats()});
            if((allotedSeats + party.getSeats()) > event.getSeats()) {
                throw new NotEnoughSeatsException("There's only " + (event.getSeats() - allotedSeats) + "place(s) available.");
            }
        }
        party.setDateCreated(new Date());
        party = partyDao.create(party);
        return party;
    }

    @Override
    public List<Party> findAllByEvent(Event event, User user) {
        boolean isAdmin = false;
        for(Role role : user.getRoles()) {
            if(role.getAuthority().contains("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if(isAdmin || event.getUser().getId().equals(user.getId())) {
            return partyDao.findAllByEvent(event);
        }
        return null;
    }

}
