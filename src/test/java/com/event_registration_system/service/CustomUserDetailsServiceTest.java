package com.event_registration_system.service;

import com.event_registration_system.entities.User;
import com.event_registration_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    //Load User By Name :: User Exist
    @Test
    void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setId(1L);
        user.setEmail("alice@example.com");
        user.setPassword("encodedPass");
        user.setRoles(Set.of("USER"));

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("alice@example.com");

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByEmail("alice@example.com");
    }

    //Load User By Name :: User does not Exist
    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail("bob@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("bob@example.com"));

        verify(userRepository, times(1)).findByEmail("bob@example.com");
    }
}