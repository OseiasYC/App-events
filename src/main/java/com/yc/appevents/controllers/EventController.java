package com.yc.appevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventController {

    @RequestMapping("/register")
    public String form(){
        return "event/formEvent";
    }
}
