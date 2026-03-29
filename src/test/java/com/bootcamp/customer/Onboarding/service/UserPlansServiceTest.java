
package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.NotificationService;
import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserPlansServiceTest {

    @InjectMocks
    private UserPlansService userPlansService;

    @Mock
    private UserPlansRepository userPlansRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private NotificationService notificationService;

    private User user;
    private Plans plan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        plan = new Plans();
        plan.setPlan_id(1L);
        plan.setValidity_days(30);
    }

    @Test
    void addPlansToUser_shouldSaveUserPlans() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));

        userPlansService.addPlansToUser(1L, Arrays.asList(1L));

        verify(userPlansRepository, times(1)).save(any(UserPlans.class));
    }

    @Test
    void addPlanToUser_shouldSaveUserPlanAndCreateNotification() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));

        userPlansService.addPlanToUser(1L, 1L);

        verify(userPlansRepository, times(1)).save(any(UserPlans.class));

        verify(notificationService, times(1)).createNotification(1L, 1L); // Verify notification service call
    }

    @Test
    void addPlansToUser_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlansToUser(1L, Arrays.asList(1L));
        });

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void addPlanToUser_planNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlanToUser(1L, 1L);
        });

        assertEquals("Plan not found", exception.getMessage());
    }

    @Test
    void getUserPlans_shouldReturnUserPlans() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPlansRepository.findByUser(user)).thenReturn(Optional.of(new UserPlans()));

        Optional<UserPlans> result = userPlansService.getUserPlans(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void getUserPlans_userNotFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.getUserPlans(1L);
        });

        assertEquals("User Not Found", exception.getMessage());
    }
}
