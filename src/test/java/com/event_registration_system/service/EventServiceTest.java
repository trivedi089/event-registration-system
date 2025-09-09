package com.event_registration_system.service;

import com.event_registration_system.entities.Event;
import com.event_registration_system.repository.EventRepository;
import com.event_registration_system.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    //Create->Save Event
    @Test
    void testCreateEvent() {
        Event event = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event created = eventService.createEvent(event);

        assertNotNull(created);
        assertEquals("Conference", created.getName());
        verify(eventRepository, times(1)).save(event);
    }

    //Get All Events test
    @Test
    void testGetAllEvents() {
        List<Event> events = List.of(
                new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null),
                new Event(2L, "Meetup", LocalDate.of(2025, 9, 12), "Mumbai", null)
        );

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        assertEquals("Meetup", result.get(1).getName());
    }

    //Event Found by Id
    @Test
    void testGetEventById_Found() {
        Event event = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals("Delhi", result.get().getLocation());
    }

    //Event not found by Id
    @Test
    void testGetEventById_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(1L);

        assertFalse(result.isPresent());
    }

    //Update Event
    @Test
    void testUpdateEvent() {
        Event existing = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);
        Event updated = new Event(1L, "UpdatedConf", LocalDate.of(2025, 9, 15), "Mumbai", null);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(eventRepository.save(any(Event.class))).thenReturn(updated);

        Event result = eventService.updateEvent(1L, updated);

        assertEquals("UpdatedConf", result.getName());
        assertEquals("Mumbai", result.getLocation());
    }

    //Delete Event
    @Test
    void testDeleteEvent() {
        when(eventRepository.existsById(1L)).thenReturn(true);
        eventService.deleteEvent(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }
}