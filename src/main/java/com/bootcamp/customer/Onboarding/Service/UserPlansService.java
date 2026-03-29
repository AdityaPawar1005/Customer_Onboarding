package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.NotificationRepository;
import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.model.Notification;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserPlansService {

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlansRepository plansRepository;

    @Autowired
    private NotificationService notificationService;

    public void addPlansToUser(Long userId, List<Long> planIds) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        for (Long plandId : planIds) {
            Plans plan = plansRepository.findById(plandId).orElseThrow(
                    () -> new RuntimeException("Plan not found")
            );

            UserPlans userPlans = new UserPlans();
            userPlans.setUser(user);
            userPlans.setPlan(plan);
            userPlans.setActivation_date(new Date());
            userPlans.setExpiration_date(new Date(System.currentTimeMillis()+(plan.getValidity_days()*24*60*60*1000L)));
            userPlansRepository.save(userPlans);
        }
    }
    public void addPlanToUser(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        Plans plan = plansRepository.findById(planId).orElseThrow(
                () -> new RuntimeException("Plan not found")
        );

        UserPlans userPlans = new UserPlans();
        userPlans.setUser(user);
        userPlans.setPlan(plan);
        userPlans.setActivation_date(new Date());
        userPlans.setExpiration_date(new Date(System.currentTimeMillis() + (plan.getValidity_days() * 24 * 60 * 60 * 1000L)));
        userPlansRepository.save(userPlans);

        // adding data into the Notification repository
        notificationService.createNotification(userId,planId);

    }
    public Optional<UserPlans> getUserPlans(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User Not Found")
        );

        return userPlansRepository.findByUser(user);
    }

}