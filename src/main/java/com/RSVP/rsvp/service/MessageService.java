package com.RSVP.rsvp.service;

import com.RSVP.rsvp.model.Message;

import java.util.Map;

public interface MessageService {

    public Map<String, Object> list(Map<String, Object> params);

    public Message get(Long messageId);

    public Message update(Message message);

    public Message get(String name);
}
