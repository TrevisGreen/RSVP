package com.RSVP.rsvp.web;


import com.RSVP.rsvp.model.Event;
import com.RSVP.rsvp.model.Party;
import com.RSVP.rsvp.model.User;
import com.RSVP.rsvp.service.EventService;
import com.RSVP.rsvp.service.PartyService;
import com.RSVP.rsvp.service.UserService;
import com.RSVP.rsvp.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PartyService partyService;

    @RequestMapping(method = RequestMethod.GET)
    public String profile(Model model, Principal principal,
                          @RequestParam(required = false) Integer max,
                          @RequestParam(required = false) Integer offset,
                          @RequestParam(required = false) String filter,
                          @RequestParam(required = false) Long page,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) String order) {

        Map<String, Object> params = new HashMap<>();
        if(max != null) {
            params.put("max", max);
            if(offset != null) {
                params.put("offset", offset);
            }
        } else {
            params.put("max", 10);
            params.put("offset", 0);
        }
        if(StringUtils.isNotBlank(filter)) {
            params.put("filter", filter);
        }

        if(StringUtils.isNotBlank(sort)) {
            params.put("sort", sort);
        } else {
            params.put("sort", "name");
        }
        if(StringUtils.isNotBlank(order)) {
            params.put("order", order);
        } else {
            params.put("order", "asc");
        }
        params.put("order2", params.get("order"));

        params.put("mine", Boolean.TRUE);
        params.put("principal", principal.getName());

        params = eventService.list(params);

        this.paginate(params, model, page);

        model.addAllAtrributes(params);

        return "home/profile";
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public String show(@PathVariable String eventId, @ModelAttribute("event")Event event, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        if(event == null || !StringUtils.isNotBlank(event.getName())) {
            event = eventService.get(eventId);
            model.addAttribute("event", event);
        }

        if(principal != null) {
            User user = userService.get(principal.getName());
            if(user.getId().equals(event.getUser().getId())) {
                model.addAttribute("owner", Boolean.TRUE);
            }
            List<Party> parties = partyService.findAllByEvent(event, user);
            model.addAttribute("parties", parties);
        }

        return "home/show";
    }

    @RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
    public String edit(@PathVariable String eventId, Model model, Principal principal) {
        Event event = eventService.get(eventId);
        model.addAttribute("event", event);

        return "home/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Event event, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model, HttpServletRequest request) {
        String back = "home.edit";
        if(request.getParameterMap().containsKey("cancel")) {
            return back;
        }
        if(bindingResult.hasErrors()) {
            log.warn("Could not create event {}", bindingResult.getAllErrors());
            return back;
        }

        try {
            User user = userService.get(principal.getName());
            event.setUser(user);
            event.setStatus(Constants.PUBLISHED);

            event = eventService.createOrUpdate(event);

            redirectAttributes.addFlashAttribute("event", event);
            redirectAttributes.addFlashAttribute("successMessage", "event.update");
            redirectAttributes.addFlashAttribute("successMessageAttrs", event.getName());

            return "redirect:/profile/" + event.getId();
        } catch (Exception e) {
            log.error("Could not update event", e);

            model.addAttribute("errorMessage", "event.not.updated");
            model.addAttribute("errorMessageAttr", e.getMessage());

            return back;
        }
    }

}
