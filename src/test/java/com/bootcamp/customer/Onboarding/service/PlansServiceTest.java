package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PlansServiceTest {

    @InjectMocks
    private PlansService plansService;

    @Mock
    private PlansRepository plansRepository;

    private Plans plan1;
    private Plans plan2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize mock data
        plan1 = new Plans(1L, "Plan A", "Description A", 199.99, 30, null);
        plan2 = new Plans(2L, "Plan B", "Description B", 299.99, 60, null);
    }

    @Test
    public void testGetAllPlans() {
        // Given
        List<Plans> allPlans = Arrays.asList(plan1, plan2);
        when(plansRepository.findAll()).thenReturn(allPlans);

        // When
        List<Plans> result = plansService.getAllPlans();

        // Then
        assertEquals(2, result.size());
        assertEquals("Plan A", result.get(0).getPlan_name());
        assertEquals("Plan B", result.get(1).getPlan_name());
    }

    @Test
    public void testGetPlanByIdFound() {
        // Given
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan1));

        // When
        Plans result = plansService.getPlanById(1L);

        // Then
        assertEquals("Plan A", result.getPlan_name());
        assertEquals(199.99, result.getPrice());
        assertEquals(30, result.getValidity_days());
    }

    @Test
    public void testGetPlanByIdNotFound() {
        // Given
        when(plansRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Plans result = plansService.getPlanById(999L);

        // Then
        assertNull(result);
    }
}
