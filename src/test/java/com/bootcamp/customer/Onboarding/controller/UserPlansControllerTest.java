package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlansControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserPlansService userPlansService;

    @InjectMocks
    private UserPlansController userPlansController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userPlansController).build();
    }

    @Test
    public void testAddPlansToUser() throws Exception {
        Long userId = 1L;
        List<Long> plansIds = Arrays.asList(1L, 2L, 3L);

        // Mock the service call
        doNothing().when(userPlansService).addPlansToUser(userId, plansIds);

        // Perform the request
        mockMvc.perform(post("/user/user-plans/addPlans")
                        .param("userId", userId.toString())
                        .param("plansIds", "1", "2", "3"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPlanToUser() throws Exception {
        Long userId = 1L;
        Long planId = 1L;

        // Mock the service call
        doNothing().when(userPlansService).addPlanToUser(userId, planId);

        // Perform the request
        mockMvc.perform(post("/user/user-plans/add")
                        .param("userId", userId.toString())
                        .param("planId", planId.toString()))
                .andExpect(status().isOk());
    }
}
