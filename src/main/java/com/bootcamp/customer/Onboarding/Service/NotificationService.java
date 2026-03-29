package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.NotificationRepository;
import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlansRepository plansRepository;

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Autowired
    private EmailService emailService;


    public Notification createNotification(Long userId, Long plan_Id){
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Plans> plansOpt = plansRepository.findById(plan_Id);

        if(!userOpt.isPresent()){
            throw new ResourceNotFoundException("User not found for userId :"  + userId);
        }

        if(!plansOpt.isPresent()){
            throw new ResourceNotFoundException("Plan not found for planId :"  + plan_Id);
        }

        User user = userOpt.get();
        Plans plans = plansOpt.get();
        System.out.println(user.getUserId());
        System.out.println(plans.getPlan_id());

        NotificationId notificationId = new NotificationId(userId,plan_Id);
        Notification notification = new Notification(notificationId,user,plans,true);
        System.out.println("Notification created");
        return notificationRepository.save(notification);

    }

    public void notificationSentForUser(Long userId){
        Optional<User> userOpt = userRepository.findById(userId);

        if(!userOpt.isPresent()){
            throw new ResourceNotFoundException("User not found for userId :"  + userId);
        }


        List<Long> userPlansList = userPlansRepository.findPlanIdsByUserId(userId);
        System.out.println(userPlansList);

        for(Long userPlanId: userPlansList){
            Optional<Plans> plans = plansRepository.findById(userPlanId);

            if(!plans.isPresent())  {
                throw new ResourceNotFoundException("Plan not found for userId :"  + userId);
            }
            NotificationId notificationId = new NotificationId(userId,userPlanId);
            Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                    ()-> new ResourceNotFoundException("Error while sending the notification for user: " + userId+ " planId: " + userPlanId)
            );

            if(notification.isStatus()){
                //checking the notification status for the user and sending the mail
                //To send the mail we need a user email and plan data
                emailService.sendEmailNotification(userOpt.get().getEmail(),plans.get());
                notification.setStatus(true);
                notificationRepository.save(notification);
            }
        }

    }

    public void documentVerificationSuccess(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (!userOpt.isPresent()) {
            throw new ResourceNotFoundException("User not found for document verification userId: " + userId);
        }

        User user = userOpt.get();
        emailService.sendDocumentNotification(user.getEmail());

    }





}