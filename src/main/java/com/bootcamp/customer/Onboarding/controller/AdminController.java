package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AdminDto>> getAllUsers() {
        List<AdminDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/filter")
    public ResponseEntity<List<AdminDto>> getUsersByType(@RequestParam int customerType) {
        List<AdminDto> users = userService.getUsersByType(customerType);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/users/document-status")
    public ResponseEntity<List<AdminDto>> getUsersByDocumentStatus(@RequestParam boolean isVerified) {
        List<AdminDto> users = userService.getUsersByDocumentStatus(isVerified);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}