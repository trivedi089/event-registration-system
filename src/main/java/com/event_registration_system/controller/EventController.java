package com.event_registration_system.controller;

import com.event_registration_system.entities.Event;
import com.event_registration_system.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event){
         return eventService.createEvent(event);
    }
}
