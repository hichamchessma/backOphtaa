package com.ophta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ophta.entity.RendezVous;
import com.ophta.service.RendezVousService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RendezVousControllerTest {

    @Mock
    private RendezVousService rendezVousService;

    @InjectMocks
    private RendezVousController rendezVousController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rendezVousController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllRendezVous_ShouldReturnListOfRendezVous() throws Exception {
        // Arrange
        RendezVous rendezVous = new RendezVous();
        List<RendezVous> rendezVousList = Arrays.asList(rendezVous);
        
        when(rendezVousService.findAll()).thenReturn(rendezVousList);

        // Act & Assert
        mockMvc.perform(get("/api/rendezVous")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void getRendezVousById_WhenRendezVousExists_ShouldReturnRendezVous() throws Exception {
        // Arrange
        Long id = 1L;
        RendezVous rendezVous = new RendezVous();
        when(rendezVousService.findById(id)).thenReturn(Optional.of(rendezVous));

        // Act & Assert
        mockMvc.perform(get("/api/rendezVous/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getRendezVousById_WhenRendezVousDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(rendezVousService.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/rendezVous/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRendezVous_ShouldReturnCreatedRendezVous() throws Exception {
        // Arrange
        RendezVous rendezVous = new RendezVous();
        when(rendezVousService.save(any(RendezVous.class))).thenReturn(rendezVous);

        // Act & Assert
        mockMvc.perform(post("/api/rendezVous")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rendezVous)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void updateRendezVous_WhenRendezVousExists_ShouldReturnUpdatedRendezVous() throws Exception {
        // Arrange
        Long id = 1L;
        RendezVous rendezVous = new RendezVous();
        when(rendezVousService.findById(id)).thenReturn(Optional.of(rendezVous));
        when(rendezVousService.save(any(RendezVous.class))).thenReturn(rendezVous);

        // Act & Assert
        mockMvc.perform(put("/api/rendezVous/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rendezVous)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void deleteRendezVous_WhenRendezVousExists_ShouldReturnOk() throws Exception {
        // Arrange
        Long id = 1L;
        RendezVous rendezVous = new RendezVous();
        when(rendezVousService.findById(id)).thenReturn(Optional.of(rendezVous));

        // Act & Assert
        mockMvc.perform(delete("/api/rendezVous/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRendezVous_WhenRendezVousDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(rendezVousService.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/rendezVous/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
