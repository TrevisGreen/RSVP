package com.RSVP.rsvp.service;

import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Party;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.utils.NotEnoughSeatsException;

import java.util.List;

public interface PartyService {

    public Party create(Party party) throws NotEnoughSeatsException;

    public List<Party> findAllByEvent(Event event, User user);
}
