package com.event_registration_system.controller;

import com.event_registration_system.entities.Event;
import com.event_registration_system.entities.Registration;
import com.event_registration_system.entities.User;
import com.event_registration_system.repository.EventRepository;
import com.event_registration_system.repository.RegistrationRepository;
import com.event_registration_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)  // disables Spring Security filters
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationRepository registrationRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private EventRepository eventRepository;

    @Test
    void testRegisterUserForEvent() throws Exception {
        User user = new User(1L, "John", "john@example.com", "pass", Set.of("USER"));
        Event event = new Event(1L, "Conference", LocalDate.of(2025, 9, 10), "Delhi", null);
        Registration registration = new Registration(1L, user, event, "CONFIRMED");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        mockMvc.perform(post("/api/registrations/register/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.user.name").value("John"))
                .andExpect(jsonPath("$.event.name").value("Conference"));
    }

    @Test
    void testGetAllRegistrations() throws Exception {
        User user = new User(1L, "Alice", "alice@example.com", "pass", Set.of("USER"));
        Event event = new Event(1L, "Meetup", LocalDate.of(2025, 9, 12), "Mumbai", null);
        Registration reg = new Registration(1L, user, event, "CONFIRMED");

        Mockito.when(registrationRepository.findAll()).thenReturn(List.of(reg));

        mockMvc.perform(get("/api/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMED"))
                .andExpect(jsonPath("$[0].user.name").value("Alice"))
                .andExpect(jsonPath("$[0].event.name").value("Meetup"));
    }

    @Test
    void testGetRegistrationsByUser() throws Exception {
        User user = new User(1L, "John", "john@example.com", "pass", Set.of("USER"));
        Event event = new Event(2L, "Workshop", LocalDate.of(2025, 9, 15), "Bangalore", null);
        Registration reg = new Registration(2L, user, event, "CONFIRMED");

        Mockito.when(registrationRepository.findByUserId(1L)).thenReturn(List.of(reg));

        mockMvc.perform(get("/api/registrations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMED"))
                .andExpect(jsonPath("$[0].event.name").value("Workshop"));
    }

    @Test
    void testCancelRegistration() throws Exception {
        User user = new User(1L, "John", "john@example.com", "pass", Set.of("USER"));
        Event event = new Event(3L, "Hackathon", LocalDate.of(2025, 9, 20), "Hyderabad", null);
        Registration reg = new Registration(3L, user, event, "CONFIRMED");

        Mockito.when(registrationRepository.findById(3L)).thenReturn(Optional.of(reg));
        reg.setStatus("CANCELLED");
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(reg);

        mockMvc.perform(put("/api/registrations/cancel/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"))
                .andExpect(jsonPath("$.event.name").value("Hackathon"));
    }
}