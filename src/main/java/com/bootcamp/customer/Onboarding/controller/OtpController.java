package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.OtpService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;
    @Autowired
    private UserService userService;

//    @PostMapping("/generate")
//    public String generateOtp(@RequestParam String email){
//        otpService.generateAndSendOtp(email);
//        return "OTP send to " + email;
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp){
//        User user = otpService.verifyOtp(email, otp);
//        if (user != null) {
//            return ResponseEntity.ok(user);  // User authenticated and OTP verified
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired OTP");
//        }
//    }


}
