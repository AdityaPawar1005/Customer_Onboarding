package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.PlanType;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/plans")
    public class PlansController {

    @Autowired
    private PlansService plansService;

    @GetMapping("/all")
    public List<Plans> getAllPlans(){
        return plansService.getAllPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plans> getPlanById(@PathVariable Long id) {
        Plans plan = plansService.getPlanById(id);
        if (plan == null) {
            return ResponseEntity.notFound().build(); // Return 404 if plan is not found
        }
        return ResponseEntity.ok(plan); // Return 200 OK with the plan if found
    }

    @GetMapping("/types")
    public List<PlanType> getAllPlanTypes() {
        return plansService.getAllPlanTypes();
    }


}

/*
    @GetMapping("/{id}")
        public Plans getPlanById(@PathVariable Long id){
            return plansService.getPlanById(id);
        }
    }
*/


