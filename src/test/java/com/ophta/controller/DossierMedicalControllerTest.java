package com.ophta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ophta.entity.DossierMedical;
import com.ophta.service.DossierMedicalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DossierMedicalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DossierMedicalService dossierMedicalService;

    @InjectMocks
    private DossierMedicalController dossierMedicalController;

    private DossierMedical dossierMedical;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dossierMedicalController).build();
        objectMapper = new ObjectMapper();

        dossierMedical = new DossierMedical();
        dossierMedical.setId(1L);
        dossierMedical.setAntecedents("Antécédents de test");
        dossierMedical.setAllergies("Allergies de test");
    }

    @Test
    void getAllDossiersMedicaux_ShouldReturnListOfDossiersMedicaux() throws Exception {
        // Arrange
        List<DossierMedical> dossiersMedicaux = Arrays.asList(dossierMedical);
        when(dossierMedicalService.findAll()).thenReturn(dossiersMedicaux);

        // Act & Assert
        mockMvc.perform(get("/api/dossier-medical")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].antecedents").value("Antécédents de test"))
                .andExpect(jsonPath("$[0].allergies").value("Allergies de test"));
    }

    @Test
    void getDossierMedicalById_WhenDossierExists_ShouldReturnDossier() throws Exception {
        // Arrange
        when(dossierMedicalService.findById(1L)).thenReturn(Optional.of(dossierMedical));

        // Act & Assert
        mockMvc.perform(get("/api/dossier-medical/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.antecedents").value("Antécédents de test"))
                .andExpect(jsonPath("$.allergies").value("Allergies de test"));
    }

    @Test
    void getDossierMedicalById_WhenDossierDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(dossierMedicalService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/dossier-medical/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDossierMedical_ShouldReturnCreatedDossier() throws Exception {
        // Arrange
        when(dossierMedicalService.save(any(DossierMedical.class))).thenReturn(dossierMedical);

        // Act & Assert
        mockMvc.perform(post("/api/dossier-medical")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"antecedents\":\"Antécédents de test\",\"allergies\":\"Allergies de test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.antecedents").value("Antécédents de test"))
                .andExpect(jsonPath("$.allergies").value("Allergies de test"));
    }

    @Test
    void updateDossierMedical_WhenDossierExists_ShouldReturnUpdatedDossier() throws Exception {
        // Arrange
        when(dossierMedicalService.findById(1L)).thenReturn(Optional.of(dossierMedical));
        when(dossierMedicalService.save(any(DossierMedical.class))).thenReturn(dossierMedical);

        // Act & Assert
        mockMvc.perform(put("/api/dossier-medical/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"antecedents\":\"Antécédents de test\",\"allergies\":\"Allergies de test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.antecedents").value("Antécédents de test"))
                .andExpect(jsonPath("$.allergies").value("Allergies de test"));
    }

    @Test
    void updateDossierMedical_WhenDossierDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(dossierMedicalService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/dossier-medical/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"antecedents\":\"Antécédents de test\",\"allergies\":\"Allergies de test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDossierMedical_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long dossierId = 1L;
        when(dossierMedicalService.findById(dossierId)).thenReturn(Optional.of(dossierMedical));
        doNothing().when(dossierMedicalService).deleteById(dossierId);

        // Act & Assert
        mockMvc.perform(delete("/api/dossier-medical/{id}", dossierId))
                .andExpect(status().isNoContent());

        // Verify
        verify(dossierMedicalService, times(1)).findById(dossierId);
        verify(dossierMedicalService, times(1)).deleteById(dossierId);
    }
}
