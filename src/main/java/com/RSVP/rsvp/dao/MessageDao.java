package com.RSVP.rsvp.dao;

import com.RSVP.rsvp.model.Message;

import java.util.Map;

public interface MessageDao {

    public Map<String,Object> list(Map<String, Object> params);

    public Message get(Long messageId);

    public Message update(Message message);

    public Message get(String name);

    public Message create(Message message);
}
