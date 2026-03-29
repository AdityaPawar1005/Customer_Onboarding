package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlansController.class)
public class PlansControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlansService plansService;

    private Plans plan1;
    private Plans plan2;

    @BeforeEach
    public void setUp() {
        // Creating mock data for testing
        plan1 = new Plans(1L, "Plan A", "Description A", 199.99, 30, null);
        plan2 = new Plans(2L, "Plan B", "Description B", 299.99, 60, null);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simulating an authenticated user
    public void testGetAllPlans() throws Exception {
        List<Plans> allPlans = Arrays.asList(plan1, plan2);

        // Mocking the service call
        when(plansService.getAllPlans()).thenReturn(allPlans);

        // Performing the GET request and checking response
        mockMvc.perform(get("/user/plans"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].plan_name", is("Plan A")))
                .andExpect(jsonPath("$[1].plan_name", is("Plan B")));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simulating an authenticated user
    public void testGetPlanById() throws Exception {
        // Mocking the service call
        when(plansService.getPlanById(1L)).thenReturn(plan1);

        // Performing the GET request for plan ID 1
        mockMvc.perform(get("/user/plans/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.plan_name", is("Plan A")))
                .andExpect(jsonPath("$.price", is(199.99)))
                .andExpect(jsonPath("$.validity_days", is(30)));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simulating an authenticated user
    public void testGetPlanByIdNotFound() throws Exception {
        // Mocking the service call to return null when no plan is found
        when(plansService.getPlanById(anyLong())).thenReturn(null);

        // Performing the GET request for an invalid plan ID
        mockMvc.perform(get("/user/plans/999"))
                .andExpect(status().isNotFound());
    }
}
