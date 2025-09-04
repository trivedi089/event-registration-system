package com.event_registration_system.services;

import com.event_registration_system.entities.Event;
import com.event_registration_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    //Creating Event
    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

}
