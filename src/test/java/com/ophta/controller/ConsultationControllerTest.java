package com.ophta.controller;

import com.ophta.entity.Consultation;
import com.ophta.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ConsultationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConsultationService consultationService;

    @InjectMocks
    private ConsultationController consultationController;

    private Consultation consultation;

    @BeforeEach
    void setUp() {
        // Initialisation de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();

        // Crée une consultation de test
        consultation = new Consultation();
        consultation.setId(1L);
        consultation.setDateConsultation(LocalDateTime.now());
        consultation.setObservations("Observation de test");
        consultation.setTraitementPrescrit("Traitement de test");
    }

    @Test
    void getAllConsultations_ShouldReturnListOfConsultations() throws Exception {
        // Arrange
        List<Consultation> consultations = Arrays.asList(consultation);
        when(consultationService.findAll()).thenReturn(consultations);

        // Act & Assert
        mockMvc.perform(get("/api/consultation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].observations").value("Observation de test"))
                .andExpect(jsonPath("$[0].traitementPrescrit").value("Traitement de test"));
    }

    @Test
    void getConsultationById_WhenConsultationExists_ShouldReturnConsultation() throws Exception {
        // Arrange
        when(consultationService.findById(1L)).thenReturn(Optional.of(consultation));

        // Act & Assert
        mockMvc.perform(get("/api/consultation/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.observations").value("Observation de test"))
                .andExpect(jsonPath("$.traitementPrescrit").value("Traitement de test"));
    }

    @Test
    void getConsultationById_WhenConsultationDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(consultationService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/consultation/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createConsultation_ShouldReturnCreatedConsultation() throws Exception {
        // Arrange
        when(consultationService.save(any(Consultation.class))).thenReturn(consultation);

        // Act & Assert
        mockMvc.perform(post("/api/consultation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateConsultation\":\"2023-10-25T10:00:00\",\"observations\":\"Observation de test\",\"traitementPrescrit\":\"Traitement de test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.observations").value("Observation de test"))
                .andExpect(jsonPath("$.traitementPrescrit").value("Traitement de test"));
    }

    @Test
    void updateConsultation_WhenConsultationExists_ShouldReturnUpdatedConsultation() throws Exception {
        // Arrange
        when(consultationService.findById(1L)).thenReturn(Optional.of(consultation));
        when(consultationService.save(any(Consultation.class))).thenReturn(consultation);

        // Act & Assert
        mockMvc.perform(put("/api/consultation/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateConsultation\":\"2023-10-25T10:00:00\",\"observations\":\"Observation de test\",\"traitementPrescrit\":\"Traitement de test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.observations").value("Observation de test"))
                .andExpect(jsonPath("$.traitementPrescrit").value("Traitement de test"));
    }

    @Test
    void updateConsultation_WhenConsultationDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(consultationService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/consultation/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateConsultation\":\"2023-10-25T10:00:00\",\"observations\":\"Observation de test\",\"traitementPrescrit\":\"Traitement de test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteConsultation_WhenConsultationExists_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long consultationId = 1L;
        when(consultationService.findById(consultationId)).thenReturn(Optional.of(consultation));
        doNothing().when(consultationService).deleteById(consultationId);

        // Act & Assert
        mockMvc.perform(delete("/api/consultation/{id}", consultationId))
                .andExpect(status().isNoContent());

        // Verify
        verify(consultationService, times(1)).findById(consultationId);
        verify(consultationService, times(1)).deleteById(consultationId);
    }

    @Test
    void deleteConsultation_WhenConsultationDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(consultationService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/consultation/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}