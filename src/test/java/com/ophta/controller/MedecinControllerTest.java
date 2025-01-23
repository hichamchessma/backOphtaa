package com.ophta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ophta.entity.Medecin;
import com.ophta.service.MedecinService;
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
public class MedecinControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedecinService medecinService;

    @InjectMocks
    private MedecinController medecinController;

    private Medecin medecin;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medecinController).build();
        objectMapper = new ObjectMapper();

        medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Smith");
        medecin.setPrenom("John");
        medecin.setSpecialite("Ophtalmologie");
        medecin.setEmail("john.smith@example.com");
        medecin.setTelephone("0123456789");
    }

    @Test
    void getAllMedecins_ShouldReturnListOfMedecins() throws Exception {
        // Arrange
        List<Medecin> medecins = Arrays.asList(medecin);
        when(medecinService.findAll()).thenReturn(medecins);

        // Act & Assert
        mockMvc.perform(get("/api/medecin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("Smith"))
                .andExpect(jsonPath("$[0].prenom").value("John"))
                .andExpect(jsonPath("$[0].specialite").value("Ophtalmologie"))
                .andExpect(jsonPath("$[0].email").value("john.smith@example.com"))
                .andExpect(jsonPath("$[0].telephone").value("0123456789"));
    }

    @Test
    void getMedecinById_WhenMedecinExists_ShouldReturnMedecin() throws Exception {
        // Arrange
        when(medecinService.findById(1L)).thenReturn(Optional.of(medecin));

        // Act & Assert
        mockMvc.perform(get("/api/medecin/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Smith"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.specialite").value("Ophtalmologie"))
                .andExpect(jsonPath("$.email").value("john.smith@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void getMedecinById_WhenMedecinDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(medecinService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/medecin/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMedecin_ShouldReturnCreatedMedecin() throws Exception {
        // Arrange
        when(medecinService.save(any(Medecin.class))).thenReturn(medecin);

        // Act & Assert
        mockMvc.perform(post("/api/medecin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Smith\",\"prenom\":\"John\",\"specialite\":\"Ophtalmologie\",\"email\":\"john.smith@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Smith"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.specialite").value("Ophtalmologie"))
                .andExpect(jsonPath("$.email").value("john.smith@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void updateMedecin_WhenMedecinExists_ShouldReturnUpdatedMedecin() throws Exception {
        // Arrange
        when(medecinService.findById(1L)).thenReturn(Optional.of(medecin));
        when(medecinService.save(any(Medecin.class))).thenReturn(medecin);

        // Act & Assert
        mockMvc.perform(put("/api/medecin/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Smith\",\"prenom\":\"John\",\"specialite\":\"Ophtalmologie\",\"email\":\"john.smith@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Smith"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.specialite").value("Ophtalmologie"))
                .andExpect(jsonPath("$.email").value("john.smith@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void updateMedecin_WhenMedecinDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(medecinService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/medecin/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Smith\",\"prenom\":\"John\",\"specialite\":\"Ophtalmologie\",\"email\":\"john.smith@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMedecin_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long medecinId = 1L;
        when(medecinService.findById(medecinId)).thenReturn(Optional.of(medecin));
        doNothing().when(medecinService).deleteById(medecinId);

        // Act & Assert
        mockMvc.perform(delete("/api/medecin/{id}", medecinId))
                .andExpect(status().isNoContent());

        // Verify
        verify(medecinService, times(1)).findById(medecinId);
        verify(medecinService, times(1)).deleteById(medecinId);
    }
}
