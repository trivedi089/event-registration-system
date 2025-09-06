package com.event_registration_system.controller;

import com.event_registration_system.entities.Event;
import com.event_registration_system.entities.Registration;
import com.event_registration_system.entities.User;
import com.event_registration_system.repository.EventRepository;
import com.event_registration_system.repository.RegistrationRepository;
import com.event_registration_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    // Register a user for an event
    @PostMapping("/register/{userId}/{eventId}")
    public Registration registerUserForEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setStatus("CONFIRMED");

        return registrationRepository.save(registration);
    }

    // Get all registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    // Get registrations by user
    @GetMapping("/user/{userId}")
    public List<Registration> getRegistrationsByUser(@PathVariable Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    // Cancel a registration
    @PutMapping("/cancel/{registrationId}")
    public Registration cancelRegistration(@PathVariable Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        registration.setStatus("CANCELLED");
        return registrationRepository.save(registration);
    }
}