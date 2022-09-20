package com.yc.appevents.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yc.appevents.models.Event;
import com.yc.appevents.repository.EventRepository;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String form(){
        return "event/formEvent";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String form(Event event){
        eventRepository.save(event);
        return "redirect:/register";
    }

    @RequestMapping("/events")
    public ModelAndView eventsList(){
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Event> events = eventRepository.findAll();
        modelAndView.addObject("events", events);
        return modelAndView;
    }

    @RequestMapping("/{code}")
    public ModelAndView eventDetails(@PathVariable("code") long code){
        Event event = eventRepository.findByCode(code);
        ModelAndView modelAndView = new ModelAndView("event/eventDetails");
        modelAndView.addObject("event", event);
        System.out.println("Event" + event);
        return modelAndView;
    }
}
