package com.ophta.service;

import com.ophta.entity.DossierMedical;
import com.ophta.repository.DossierMedicalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierMedicalServiceTest {

    @Mock
    private DossierMedicalRepository dossierMedicalRepository;

    @InjectMocks
    private DossierMedicalService dossierMedicalService;

    private DossierMedical dossierMedical;

    @BeforeEach
    void setUp() {
        dossierMedical = new DossierMedical();
        dossierMedical.setId(1L);
        // Ajoutez d'autres initialisations nécessaires pour le dossier médical
    }

    @Test
    void findAll_ShouldReturnListOfDossiersMedicaux() {
        // Arrange
        List<DossierMedical> expectedDossiers = Arrays.asList(dossierMedical);
        when(dossierMedicalRepository.findAll()).thenReturn(expectedDossiers);

        // Act
        List<DossierMedical> actualDossiers = dossierMedicalService.findAll();

        // Assert
        assertThat(actualDossiers).isNotEmpty();
        assertThat(actualDossiers).hasSize(1);
        verify(dossierMedicalRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenDossierExists_ShouldReturnDossier() {
        // Arrange
        when(dossierMedicalRepository.findById(1L)).thenReturn(Optional.of(dossierMedical));

        // Act
        Optional<DossierMedical> result = dossierMedicalService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(dossierMedicalRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenDossierDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(dossierMedicalRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<DossierMedical> result = dossierMedicalService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(dossierMedicalRepository, times(1)).findById(99L);
    }

    @Test
    void save_ShouldReturnSavedDossier() {
        // Arrange
        when(dossierMedicalRepository.save(any(DossierMedical.class))).thenReturn(dossierMedical);

        // Act
        DossierMedical savedDossier = dossierMedicalService.save(dossierMedical);

        // Assert
        assertThat(savedDossier).isNotNull();
        assertThat(savedDossier.getId()).isEqualTo(1L);
        verify(dossierMedicalRepository, times(1)).save(any(DossierMedical.class));
    }

    @Test
    void deleteById_ShouldDeleteDossier() {
        // Arrange
        doNothing().when(dossierMedicalRepository).deleteById(1L);

        // Act
        dossierMedicalService.deleteById(1L);

        // Assert
        verify(dossierMedicalRepository, times(1)).deleteById(1L);
    }
}
