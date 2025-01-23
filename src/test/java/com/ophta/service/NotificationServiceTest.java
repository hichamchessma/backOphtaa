package com.ophta.service;

import com.ophta.entity.Notification;
import com.ophta.repository.NotificationRepository;
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
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test notification");
        notification.setDateNotification(LocalDateTime.now());
        // Ajoutez d'autres initialisations nécessaires pour la notification
    }

    @Test
    void findAll_ShouldReturnListOfNotifications() {
        // Arrange
        List<Notification> expectedNotifications = Arrays.asList(notification);
        when(notificationRepository.findAll()).thenReturn(expectedNotifications);

        // Act
        List<Notification> actualNotifications = notificationService.findAll();

        // Assert
        assertThat(actualNotifications).isNotEmpty();
        assertThat(actualNotifications).hasSize(1);
        assertThat(actualNotifications.get(0).getMessage()).isEqualTo("Test notification");
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenNotificationExists_ShouldReturnNotification() {
        // Arrange
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Act
        Optional<Notification> result = notificationService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getMessage()).isEqualTo("Test notification");
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenNotificationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Notification> result = notificationService.findById(99L);

        // Assert
        assertThat(result).isEmpty();
        verify(notificationRepository, times(1)).findById(99L);
    }

    @Test
    void save_ShouldReturnSavedNotification() {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        Notification savedNotification = notificationService.save(notification);

        // Assert
        assertThat(savedNotification).isNotNull();
        assertThat(savedNotification.getId()).isEqualTo(1L);
        assertThat(savedNotification.getMessage()).isEqualTo("Test notification");
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void deleteById_ShouldDeleteNotification() {
        // Arrange
        doNothing().when(notificationRepository).deleteById(1L);

        // Act
        notificationService.deleteById(1L);

        // Assert
        verify(notificationRepository, times(1)).deleteById(1L);
    }
}
