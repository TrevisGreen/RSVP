package com.RSVP.rsvp.dao;

import com.RSVP.rsvp.model.Event;

import java.util.Map;

public interface EventDao {

    public Map<String, Object> list(Map<String, Object> params);

    public Event create(Event event);

    public Event get(String eventId);

    public Event getByCode(String code);

    public void delete(Event event);

    public Event update(Event event);
}
