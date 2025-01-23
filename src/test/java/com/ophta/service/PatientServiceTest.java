package com.ophta.service;

import com.ophta.entity.Patient;
import com.ophta.repository.PatientRepository;
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
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setNom("Doe");
        patient.setPrenom("John");
        // Ajoutez d'autres initialisations nécessaires pour le patient
    }

    @Test
    void findAll_ShouldReturnListOfPatients() {
        // Arrange
        List<Patient> expectedPatients = Arrays.asList(patient);
        when(patientRepository.findAll()).thenReturn(expectedPatients);

        // Act
        List<Patient> actualPatients = patientService.findAll();

        // Assert
        assertThat(actualPatients).isNotEmpty();
        assertThat(actualPatients).hasSize(1);
        assertThat(actualPatients.get(0).getNom()).isEqualTo("Doe");
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenPatientExists_ShouldReturnPatient() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getNom()).isEqualTo("Doe");
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenPatientDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(patientRepository, times(1)).findById(99L);
    }

    @Test
    void save_ShouldReturnSavedPatient() {
        // Arrange
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient savedPatient = patientService.save(patient);

        // Assert
        assertThat(savedPatient).isNotNull();
        assertThat(savedPatient.getId()).isEqualTo(1L);
        assertThat(savedPatient.getNom()).isEqualTo("Doe");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void deleteById_ShouldDeletePatient() {
        // Arrange
        doNothing().when(patientRepository).deleteById(1L);

        // Act
        patientService.deleteById(1L);

        // Assert
        verify(patientRepository, times(1)).deleteById(1L);
    }
}
