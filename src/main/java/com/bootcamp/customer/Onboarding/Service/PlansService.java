package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.PlanTypeRepository;
import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.model.PlanType;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlansService {

    @Autowired
    private PlansRepository plansRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired   // in PlansService.java
    private PlanTypeRepository planTypeRepository;
    public List<PlanType> getAllPlanTypes() {
        return planTypeRepository.findAll();
    }

    public List<Plans> getAllPlans(){
        return plansRepository.findAll();
    }
    public Plans getPlanById(Long id){
        return plansRepository.findById(id).orElse(null);
    }

}
