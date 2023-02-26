package com.RSVP.rsvp.dao;

import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Party;

import java.util.List;

public interface PartyDao {

    public Integer getAllotedSeats(Party party);

    public Party create(Party party);

    public List<Party> findAllByEvent(Event event);
}
