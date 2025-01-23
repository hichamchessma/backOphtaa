package com.ophta.service;

import com.ophta.entity.Medecin;
import com.ophta.repository.MedecinRepository;
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
class MedecinServiceTest {

    @Mock
    private MedecinRepository medecinRepository;

    @InjectMocks
    private MedecinService medecinService;

    private Medecin medecin;

    @BeforeEach
    void setUp() {
        medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Smith");
        medecin.setPrenom("John");
        medecin.setSpecialite("Ophtalmologie");
        // Ajoutez d'autres initialisations nécessaires pour le médecin
    }

    @Test
    void findAll_ShouldReturnListOfMedecins() {
        // Arrange
        List<Medecin> expectedMedecins = Arrays.asList(medecin);
        when(medecinRepository.findAll()).thenReturn(expectedMedecins);

        // Act
        List<Medecin> actualMedecins = medecinService.findAll();

        // Assert
        assertThat(actualMedecins).isNotEmpty();
        assertThat(actualMedecins).hasSize(1);
        assertThat(actualMedecins.get(0).getNom()).isEqualTo("Smith");
        assertThat(actualMedecins.get(0).getSpecialite()).isEqualTo("Ophtalmologie");
        verify(medecinRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenMedecinExists_ShouldReturnMedecin() {
        // Arrange
        when(medecinRepository.findById(1L)).thenReturn(Optional.of(medecin));

        // Act
        Optional<Medecin> result = medecinService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getNom()).isEqualTo("Smith");
        assertThat(result.get().getSpecialite()).isEqualTo("Ophtalmologie");
        verify(medecinRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenMedecinDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(medecinRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Medecin> result = medecinService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(medecinRepository, times(1)).findById(99L);
    }

    @Test
    void save_ShouldReturnSavedMedecin() {
        // Arrange
        when(medecinRepository.save(any(Medecin.class))).thenReturn(medecin);

        // Act
        Medecin savedMedecin = medecinService.save(medecin);

        // Assert
        assertThat(savedMedecin).isNotNull();
        assertThat(savedMedecin.getId()).isEqualTo(1L);
        assertThat(savedMedecin.getNom()).isEqualTo("Smith");
        assertThat(savedMedecin.getSpecialite()).isEqualTo("Ophtalmologie");
        verify(medecinRepository, times(1)).save(any(Medecin.class));
    }

    @Test
    void deleteById_ShouldDeleteMedecin() {
        // Arrange
        doNothing().when(medecinRepository).deleteById(1L);

        // Act
        medecinService.deleteById(1L);

        // Assert
        verify(medecinRepository, times(1)).deleteById(1L);
    }
}
