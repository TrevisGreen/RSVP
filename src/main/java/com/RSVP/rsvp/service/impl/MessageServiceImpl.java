package com.RSVP.rsvp.service.impl;


import com.RSVP.rsvp.dao.MessageDao;
import com.RSVP.rsvp.model.Message;
import com.RSVP.rsvp.service.BaseService;
import com.RSVP.rsvp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class MessageServiceImpl extends BaseService implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Transactional
    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        return messageDao.get(messageId);
    }

    @Override
    public Message update(Message message) {
        return messageDao.update(message);
    }

    @Override
    public Message get(String name) {
        return messageDao.get(name);
    }
}
