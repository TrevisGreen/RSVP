package com.RSVP.rsvp.service;

import com.RSVP.rsvp.model.Event;

import java.io.IOException;
import java.util.Map;

public interface EventService {

    public Map<String, Object> list(Map<String, Object> params);

    public Event createOrUpdate(Event event) throws IOException;

    public Event get(String eventId);

    public Event getByCode(String code);

    public String delete(String eventId, String name);

    public boolean isNotUniqueCode(String code);
}
