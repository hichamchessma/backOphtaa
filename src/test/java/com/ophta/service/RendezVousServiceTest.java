package com.ophta.service;

import com.ophta.entity.RendezVous;
import com.ophta.repository.RendezVousRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendezVousServiceTest {

    @Mock
    private RendezVousRepository rendezVousRepository;

    @InjectMocks
    private RendezVousService rendezVousService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfRendezVous() {
        // Arrange
        RendezVous rendezVous1 = new RendezVous();
        RendezVous rendezVous2 = new RendezVous();
        List<RendezVous> expectedRendezVous = Arrays.asList(rendezVous1, rendezVous2);
        
        when(rendezVousRepository.findAll()).thenReturn(expectedRendezVous);

        // Act
        List<RendezVous> actualRendezVous = rendezVousService.findAll();

        // Assert
        assertEquals(expectedRendezVous, actualRendezVous);
        verify(rendezVousRepository).findAll();
    }

    @Test
    void findById_WhenRendezVousExists_ShouldReturnRendezVous() {
        // Arrange
        Long id = 1L;
        RendezVous expectedRendezVous = new RendezVous();
        when(rendezVousRepository.findById(id)).thenReturn(Optional.of(expectedRendezVous));

        // Act
        Optional<RendezVous> actualRendezVous = rendezVousService.findById(id);

        // Assert
        assertTrue(actualRendezVous.isPresent());
        assertEquals(expectedRendezVous, actualRendezVous.get());
        verify(rendezVousRepository).findById(id);
    }

    @Test
    void save_ShouldReturnSavedRendezVous() {
        // Arrange
        RendezVous rendezVousToSave = new RendezVous();
        when(rendezVousRepository.save(rendezVousToSave)).thenReturn(rendezVousToSave);

        // Act
        RendezVous savedRendezVous = rendezVousService.save(rendezVousToSave);

        // Assert
        assertEquals(rendezVousToSave, savedRendezVous);
        verify(rendezVousRepository).save(rendezVousToSave);
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;

        // Act
        rendezVousService.deleteById(id);

        // Assert
        verify(rendezVousRepository).deleteById(id);
    }
}
