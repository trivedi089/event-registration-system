package com.event_registration_system.controller;

import com.event_registration_system.entities.Event;
import com.event_registration_system.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc(addFilters = false)  // disables Spring Security filters
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    //Create Event
    @Test
    void testCreateEvent() throws Exception {
        Event event = new Event(1L, "Meetup", LocalDate.of(2025, 9, 12), "Mumbai", null);

        Mockito.when(eventService.createEvent(Mockito.any(Event.class)))
                .thenReturn(event);

        String json = """
                {
                  "name": "Meetup",
                  "date": "2025-09-12",
                  "location": "Mumbai"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Meetup"))
                .andExpect(jsonPath("$.location").value("Mumbai"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        Event e1 = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);
        Event e2 = new Event(2L, "Workshop", LocalDate.of(2025, 9, 15), "Bangalore", null);

        Mockito.when(eventService.getAllEvents()).thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Conference"))
                .andExpect(jsonPath("$[1].location").value("Bangalore"));
    }

    //Get Event By Id :: Found
    @Test
    void testGetEventById_Found() throws Exception {
        Event event = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);

        Mockito.when(eventService.getEventById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Conference"))
                .andExpect(jsonPath("$.location").value("Delhi"));
    }

    //Get Event By Id :: Not Found
    @Test
    void testGetEventById_NotFound() throws Exception {
        Mockito.when(eventService.getEventById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/events/99"))
                .andExpect(status().isNotFound());
    }

    //Update Event
    @Test
    void testUpdateEvent() throws Exception {
        Event updated = new Event(1L, "Updated Conf", LocalDate.of(2025, 9, 20), "Hyderabad", null);

        Mockito.when(eventService.updateEvent(Mockito.eq(1L), Mockito.any(Event.class)))
                .thenReturn(updated);

        String json = """
                {
                  "name": "Updated Conf",
                  "date": "2025-09-20",
                  "location": "Hyderabad"
                }
                """;

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Conf"))
                .andExpect(jsonPath("$.location").value("Hyderabad"));
    }

    //Delete Event
    @Test
    void testDeleteEvent() throws Exception {
        Mockito.doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }
}