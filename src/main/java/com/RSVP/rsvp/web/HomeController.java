package com.RSVP.rsvp.web;


import com.RSVP.rsvp.model.Connection;
import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.service.EventService;
import com.RSVP.rsvp.service.UserService;
import com.RSVP.rsvp.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(HttpSession session) {
        log.debug("Showing home page");
        if(session.getAttribute("imageUrl") == null && session.getAttribute("noImageUrl") == null) {
            log.debug("Looking for authenticated user");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null && !auth.getName().equals("anonymousUser")) {
                log.debug("Looking for social connection for {}", auth.getName());
                Connection connection = userService.getConnection(auth.getName());
                if(connection != null) {
                    session.setAttribute("imageUrl", connection.getImageUrl());
                } else {
                    session.setAttribute("noImageUrl", Boolean.TRUE);
                }
                return "redirect:/profile";
            }
        }
        return "home/home";
    }

    @RequestMapping(value = "/currentBackground", params = {"backgroundId"}, method = RequestMethod.POST);
    @ResponseBody
    public String setBackground(HttpSession session, @RequestParam Integer backgroundId) {
        session.setAttribute(Constants.BACKGROUND_ID, backgroundId);
        return "OK";
    }

    @RequestMapping(value = "/image/{eventId}", method = RequestMethod.GET)
    public String image(HttpServletRequest request, HttpServletResponse response, @PathVariable String eventId) {
        Event event eventService.get(eventId);
        if(event != null) {
            response.setContentType(event.getContentType());
            response.setContentLength(event.getImageSize().intValue());
            try {
                response.getOutputStream().write(event.getImageData);
            } catch(IOException e) {
                log.error("Could not write image to outputstream");
                throw new RuntimeException("Could not get image", e);
            }
        }
        return null;
    }
}
