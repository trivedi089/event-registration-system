package com.event_registration_system.controller;

import com.event_registration_system.entities.Event;
import com.event_registration_system.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    //Creating Event
    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event){
         return eventService.createEvent(event);
    }

    //Get all Events
    @GetMapping("/events")
    public List<Event> eventList(){
        return eventService.findAllEvents();
    }

    //Get Event by Id
    @GetMapping("/events/{id}")
    public Optional<Event> getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }

    //update Event
    @PostMapping("/events/{id}")
    public Optional<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event event){
        return eventService.updateEvent(id,event);
    }

}
