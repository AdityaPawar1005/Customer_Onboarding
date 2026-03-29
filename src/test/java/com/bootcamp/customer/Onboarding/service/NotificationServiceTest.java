package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.NotificationRepository;
import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.EmailService;
import com.bootcamp.customer.Onboarding.Service.NotificationService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Notification;
import com.bootcamp.customer.Onboarding.model.NotificationId;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private UserPlansRepository userPlansRepository;

    @Mock
    private EmailService emailService;

    private User user;
    private Plans plan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        plan = new Plans();
        plan.setPlan_id(1L);
        plan.setPlan_name("Basic Plan");
        plan.setValidity_days(30);
    }

    @Test
    void createNotification_shouldCreateNotification() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Notification notification = notificationService.createNotification(1L, 1L);

        assertNotNull(notification);
        assertEquals(user, notification.getUser());
        assertEquals(plan, notification.getPlan());
        assertTrue(notification.isStatus());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void createNotification_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.createNotification(1L, 1L);
        });

        assertEquals("User not found for userId :1", exception.getMessage());
    }

    @Test
    void createNotification_planNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.createNotification(1L, 1L);
        });

        assertEquals("Plan not found for planId :1", exception.getMessage());
    }

    @Test
    void notificationSentForUser_shouldSendEmail() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPlansRepository.findPlanIdsByUserId(1L)).thenReturn(List.of(1L));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(notificationRepository.findById(new NotificationId(1L, 1L)))
                .thenReturn(Optional.of(new Notification(new NotificationId(1L, 1L), user, plan, true)));

        notificationService.notificationSentForUser(1L);

        verify(emailService, times(1)).sendEmailNotification(user.getEmail(), plan);
    }

    @Test
    void notificationSentForUser_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.notificationSentForUser(1L);
        });

        assertEquals("User not found for userId :1", exception.getMessage());
    }

    @Test
    void notificationSentForUser_planNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPlansRepository.findPlanIdsByUserId(1L)).thenReturn(List.of(1L));
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.notificationSentForUser(1L);
        });

        assertEquals("Plan not found for userId :1", exception.getMessage());
    }

    @Test
    void documentVerificationSuccess_shouldSendEmail() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        notificationService.documentVerificationSuccess(1L);

        verify(emailService, times(1)).sendDocumentNotification(user.getEmail());
    }

    @Test
    void documentVerificationSuccess_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.documentVerificationSuccess(1L);
        });

        assertEquals("User not found for document verification userId: 1", exception.getMessage());
    }
}
