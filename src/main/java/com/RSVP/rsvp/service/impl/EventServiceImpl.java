package com.RSVP.rsvp.service.impl;


import com.RSVP.rsvp.dao.EventDao;
import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.BaseService;
import com.RSVP.rsvp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class EventServiceImpl extends BaseService implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        return eventDao.list(params);
    }

    @Override
    public Event createOrUpdate(Event event) throws IOException {
        Date date = new Date();
        event.setLastUpdated(date);
        if(event.getImageFile() != null && !event.getImageFile().isEmpty()) {
            event.setImageName(event.getImageFile().getOriginalFilename());
            event.setContentType(event.getImageFile().getContentType());
            event.setImageSize(event.getImageFile().getSize());
            event.setImagaData(event.getImageFile().getBytes());
        }
        if(StringUtils.isBlank(event.getId())) {
            event.setDateCreated(date);
            event.setId(UUID.randomUUID().toString());
            event = eventDao.create(event);
        } else {
            event = eventDao.update(event);
        }
        return event;
    }

    @Transactional(readOnly = true)
    @Override
    public Event get(String eventId) {
        return eventDao.get(eventId);
    }

    @Override
    public Event getByCode(String code) {
        return eventDao.getByCode(code);
    }

    @Override
    public String delete(String eventId, String name) {
        User user = userDao.get(name);
        boolean isAdmin = false;
        for(Role role : user.getRoles()) {
            if(role.getAuthority().contains("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        Event event = eventDao.get(eventId);
        if(isAdmin || event.getUser().getId().equals(user.getId())) {
            eventDao.delete(event);
            return event.getName();
        } else {
            throw new RuntimeException("You can't delete an event that doesn't belong to you.");
        }
    }

    @Override
    public boolean isNotUniqueCode(String code) {
        Event event = eventDao.getByCode(code);
        return event != null;
    }
}
