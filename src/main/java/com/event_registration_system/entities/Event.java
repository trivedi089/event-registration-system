package com.event_registration_system.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Event name is mandatory")
    @Size(max = 100, message = "Event name can be max 100 characters")
    private String name;

    @Future(message = "Event date must be in the future")
    private LocalDate date;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Size(max = 500, message = "Description can be max 500 characters")
    private String description;
}