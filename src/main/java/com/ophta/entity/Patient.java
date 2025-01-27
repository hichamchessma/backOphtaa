package com.ophta.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Entity
@Data
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false, name = "date_maissance")
    private LocalDate dateMaissance;

    @Column(length = 20)
    private String telephone;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 255)
    private String adresse;

    @Column(name = "numero_securite_sociale", length = 50)
    private String numeroSecuriteSociale;

    @Column(name = "dernier_rdv")
    private LocalDate dernierRdv;

    @Column(name = "prochain_adv")
    private LocalDate prochainAdv;

    @ElementCollection
    @CollectionTable(name = "patient_antecedents", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "antecedent")
    private List<String> antecedents;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
