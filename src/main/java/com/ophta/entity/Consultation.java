package com.ophta.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "Consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "date_consultation")
    private LocalDateTime dateConsultation;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "traitement_prescrit", columnDefinition = "TEXT")
    private String traitementPrescrit;

    @ManyToOne
    @JoinColumn(name = "dossier_medical_id")
    private DossierMedical dossierMedical;

    @OneToOne
    @JoinColumn(name = "rendezvous_id", unique = true)
    private RendezVous rendezVous;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
