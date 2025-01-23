package com.ophta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ophta.entity.Patient;
import com.ophta.service.PatientService;
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
public class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private Patient patient;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        objectMapper = new ObjectMapper();

        patient = new Patient();
        patient.setId(1L);
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setEmail("john.doe@example.com");
        patient.setTelephone("0123456789");
    }

    @Test
    void getAllPatients_ShouldReturnListOfPatients() throws Exception {
        // Arrange
        List<Patient> patients = Arrays.asList(patient);
        when(patientService.findAll()).thenReturn(patients);

        // Act & Assert
        mockMvc.perform(get("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("Doe"))
                .andExpect(jsonPath("$[0].prenom").value("John"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].telephone").value("0123456789"));
    }

    @Test
    void getPatientById_WhenPatientExists_ShouldReturnPatient() throws Exception {
        // Arrange
        when(patientService.findById(1L)).thenReturn(Optional.of(patient));

        // Act & Assert
        mockMvc.perform(get("/api/patient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void getPatientById_WhenPatientDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(patientService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/patient/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPatient_ShouldReturnCreatedPatient() throws Exception {
        // Arrange
        when(patientService.save(any(Patient.class))).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Doe\",\"prenom\":\"John\",\"email\":\"john.doe@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void updatePatient_WhenPatientExists_ShouldReturnUpdatedPatient() throws Exception {
        // Arrange
        when(patientService.findById(1L)).thenReturn(Optional.of(patient));
        when(patientService.save(any(Patient.class))).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(put("/api/patient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Doe\",\"prenom\":\"John\",\"email\":\"john.doe@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    @Test
    void updatePatient_WhenPatientDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(patientService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/patient/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Doe\",\"prenom\":\"John\",\"email\":\"john.doe@example.com\",\"telephone\":\"0123456789\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePatient_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientService.findById(patientId)).thenReturn(Optional.of(patient));
        doNothing().when(patientService).deleteById(patientId);

        // Act & Assert
        mockMvc.perform(delete("/api/patient/{id}", patientId))
                .andExpect(status().isNoContent());

        // Verify
        verify(patientService, times(1)).findById(patientId);
        verify(patientService, times(1)).deleteById(patientId);
    }
}
