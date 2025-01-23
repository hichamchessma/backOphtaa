package com.ophta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ophta.entity.Notification;
import com.ophta.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private Notification notification;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
        objectMapper = new ObjectMapper();

        notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test notification");
        notification.setDateNotification(LocalDateTime.now());
    }

    @Test
    void getAllNotifications_ShouldReturnListOfNotifications() throws Exception {
        // Arrange
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationService.findAll()).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].message").value("Test notification"));
    }

    @Test
    void getNotificationById_WhenNotificationExists_ShouldReturnNotification() throws Exception {
        // Arrange
        when(notificationService.findById(1L)).thenReturn(Optional.of(notification));

        // Act & Assert
        mockMvc.perform(get("/api/notification/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test notification"));
    }

    @Test
    void getNotificationById_WhenNotificationDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(notificationService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/notification/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNotification_ShouldReturnCreatedNotification() throws Exception {
        // Arrange
        when(notificationService.save(any(Notification.class))).thenReturn(notification);

        // Act & Assert
        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test notification\",\"lu\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test notification"));
    }

    @Test
    void updateNotification_WhenNotificationExists_ShouldReturnUpdatedNotification() throws Exception {
        // Arrange
        when(notificationService.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationService.save(any(Notification.class))).thenReturn(notification);

        // Act & Assert
        mockMvc.perform(put("/api/notification/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test notification\",\"lu\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test notification"));
    }

    @Test
    void updateNotification_WhenNotificationDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(notificationService.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/notification/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test notification\",\"lu\":false}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotification_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long notificationId = 1L;
        when(notificationService.findById(notificationId)).thenReturn(Optional.of(notification));
        doNothing().when(notificationService).deleteById(notificationId);

        // Act & Assert
        mockMvc.perform(delete("/api/notification/{id}", notificationId))
                .andExpect(status().isNoContent());

        // Verify
        verify(notificationService, times(1)).findById(notificationId);
        verify(notificationService, times(1)).deleteById(notificationId);
    }
}
