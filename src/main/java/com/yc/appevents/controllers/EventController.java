package com.yc.appevents.controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yc.appevents.models.Event;
import com.yc.appevents.models.Guest;
import com.yc.appevents.repository.EventRepository;
import com.yc.appevents.repository.GuestRepository;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GuestRepository guestRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String form() {
        return "event/formEvent";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String form(@Valid Event event, BindingResult result, RedirectAttributes attributes) {
        if (event.getName().equals("") || event.getTime().equals("") || event.getDate().equals("")
                || event.getPlace().equals("")) {
            attributes.addFlashAttribute("message", "Check the inputs!");
            return "redirect:/register";
        }
        eventRepository.save(event);
        attributes.addFlashAttribute("message", "Event added successfully!");
        return "redirect:/register";
    }

    @RequestMapping("/events")
    public ModelAndView eventsList() {
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Event> events = eventRepository.findAll();
        modelAndView.addObject("events", events);
        return modelAndView;
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ModelAndView eventDetails(@PathVariable("code") long code) {
        Event event = eventRepository.findByCode(code);
        ModelAndView modelAndView = new ModelAndView("event/eventDetails");
        modelAndView.addObject("event", event);

        Iterable<Guest> guests = guestRepository.findByEvent(event);
        modelAndView.addObject("guests", guests);

        return modelAndView;
    }

    @RequestMapping("/delete")
    public String eventDelete(long code) {
        Event event = eventRepository.findByCode(code);
        eventRepository.delete(event);
        return "redirect:/events";
    }

    @RequestMapping("/guestDelete")
    public String guestDelete(String gr){
        Guest guest = guestRepository.findByGr(gr);
        guestRepository.delete(guest);

        Event event = guest.getEvent();
        long longCode = event.getCode();
        String code = "" + longCode;
        return("redirect:/" + code);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.POST)
    public String eventDetailsPOST(@PathVariable("code") long code, @Valid Guest guest, BindingResult result,
            RedirectAttributes attributes) {
        if (guest.getGuestName().equals("") || guest.getGr().equals("")) {
            attributes.addFlashAttribute("message", "Check the inputs!");
            return "redirect:/{code}";
        }
        Event event = eventRepository.findByCode(code);
        guest.setEvent(event);
        guestRepository.save(guest);
        attributes.addFlashAttribute("message", "Guest added!");
        return "redirect:/{code}";
    }
}
