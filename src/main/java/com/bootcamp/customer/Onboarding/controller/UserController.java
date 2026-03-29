package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.OtpService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser( @RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone, @RequestParam int customerType ){
        try {
            if(!userService.checkIfUSerExists(email)) {
                userService.registerUser(username, password, email, phone, customerType);
                return new ResponseEntity<>("Registration Successfully", HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("User Already exists", HttpStatus.CONFLICT);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    /*public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }*/

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        System.out.println("Received username: " + username);

        boolean otp = userService.sendOtp(username);

            if (otp) {
                return new ResponseEntity<>("Otp sent to your Mail", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid user", HttpStatus.BAD_REQUEST);
            }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String otp = request.get("otp");
        boolean isValid = otpService.verifyOtp(email, otp);
        if (isValid) {
            return new ResponseEntity<>("OTP verified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired OTP", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");

        boolean isUpdated = userService.updatePassword(username, newPassword);
        if (isUpdated) {
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found or password update failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile")
    public @ResponseBody ResponseEntity<UserDetailsDTO> profileUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        UserDetailsDTO user = userService.authenticate(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}