package com.ophta.service;

import com.ophta.entity.Consultation;
import com.ophta.entity.DossierMedical;
import com.ophta.repository.ConsultationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private ConsultationService consultationService;

    private Consultation consultation;

    @BeforeEach
    void setUp() {
        // Crée une consultation de test
        consultation = new Consultation();
        consultation.setId(1L);
        consultation.setDateConsultation(LocalDateTime.now());
        consultation.setObservations("Observation de test");
        consultation.setTraitementPrescrit("Traitement de test");

        // Crée un DossierMedical associé
        DossierMedical dossierMedical = new DossierMedical();
        dossierMedical.setId(1L);

        consultation.setDossierMedical(dossierMedical);
    }

    @Test
    void findAll_ShouldReturnListOfConsultations() {
        // Arrange
        List<Consultation> expectedConsultations = Arrays.asList(consultation);
        when(consultationRepository.findAll()).thenReturn(expectedConsultations);

        // Act
        List<Consultation> actualConsultations = consultationService.findAll();

        // Assert
        assertThat(actualConsultations).isNotEmpty();
        assertThat(actualConsultations).hasSize(1);
        assertThat(actualConsultations.get(0).getObservations()).isEqualTo("Observation de test");
        verify(consultationRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenConsultationExists_ShouldReturnConsultation() {
        // Arrange
        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        // Act
        Optional<Consultation> result = consultationService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getTraitementPrescrit()).isEqualTo("Traitement de test");
        verify(consultationRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenConsultationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(consultationRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Consultation> result = consultationService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(consultationRepository, times(1)).findById(99L);
    }

    @Test
    void save_ShouldReturnSavedConsultation() {
        // Arrange
        when(consultationRepository.save(any(Consultation.class))).thenReturn(consultation);

        // Act
        Consultation savedConsultation = consultationService.save(consultation);

        // Assert
        assertThat(savedConsultation).isNotNull();
        assertThat(savedConsultation.getId()).isEqualTo(1L);
        assertThat(savedConsultation.getObservations()).isEqualTo("Observation de test");
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        // Arrange
        doNothing().when(consultationRepository).deleteById(1L);

        // Act
        consultationService.deleteById(1L);

        // Assert
        verify(consultationRepository, times(1)).deleteById(1L);
    }
}