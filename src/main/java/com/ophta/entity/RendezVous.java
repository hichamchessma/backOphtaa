package com.ophta.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
@Table(name = "RendezVous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false, length = 100)
    private String patientName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Long doctorId;

    public enum AppointmentStatus {
        CONFIRME, EN_ATTENTE, ANNULE
    }
}
