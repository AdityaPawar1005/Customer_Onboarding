package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.OtpService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private OtpService otpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "password123";
        String email = "test@example.com";
        String phone = "1234567890";
        int customerType = 1;

        when(userService.checkIfUSerExists(email)).thenReturn(false);
        when(userService.registerUser(username, password, email, phone, customerType)).thenReturn(new User());

        // Act
        ResponseEntity<String> response = userController.registerUser(username, password, email, phone, customerType);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Registration Successfully", response.getBody());
    }

    @Test
    void testRegisterUser_UserExists() {
        // Arrange
        String email = "test@example.com";
        when(userService.checkIfUSerExists(email)).thenReturn(true);

        // Act
        ResponseEntity<String> response = userController.registerUser("testUser", "password123", email, "1234567890", 1);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User Already exists", response.getBody());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        String username = "testUser";
        String password = "password123";
        User mockUser = new User();
        when(userService.loginUser(username, password)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.loginUser(username, password);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Arrange
        when(userService.loginUser(anyString(), anyString())).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.loginUser("invalidUser", "wrongPassword");

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testUpdatePassword_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "testUser");
        when(userService.sendOtp("testUser")).thenReturn(true);

        // Act
        ResponseEntity<String> response = userController.updatePassword(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Otp sent to your Mail", response.getBody());
    }

    @Test
    void testUpdatePassword_InvalidUser() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "invalidUser");
        when(userService.sendOtp(anyString())).thenReturn(false);

        // Act
        ResponseEntity<String> response = userController.updatePassword(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid user", response.getBody());
    }

    @Test
    void testVerifyOtp_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("otp", "123456");
        when(otpService.verifyOtp("test@example.com", "123456")).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.verifyOtp(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP verified successfully", response.getBody());
    }

    @Test
    void testVerifyOtp_Invalid() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("otp", "wrongOtp");
        when(otpService.verifyOtp(anyString(), anyString())).thenReturn(false);

        // Act
        ResponseEntity<?> response = userController.verifyOtp(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid or expired OTP", response.getBody());
    }

    @Test
    void testChangePassword_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "testUser");
        request.put("password", "newPassword");

        when(userService.updatePassword("testUser", "newPassword")).thenReturn(true);

        // Act
        ResponseEntity<String> response = userController.changePassword(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully", response.getBody());
    }

    @Test
    void testChangePassword_Failure() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "testUser");
        request.put("password", "newPassword");

        when(userService.updatePassword("testUser", "newPassword")).thenReturn(false);

        // Act
        ResponseEntity<String> response = userController.changePassword(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found or password update failed", response.getBody());
    }

    @Test
    void testProfileUser_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "testUser");
        request.put("password", "password123");
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        when(userService.authenticate("testUser", "password123")).thenReturn(userDetailsDTO);

        // Act
        ResponseEntity<UserDetailsDTO> response = userController.profileUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsDTO, response.getBody());
    }

    @Test
    void testProfileUser_Unauthorized() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("username", "testUser");
        request.put("password", "wrongPassword");
        when(userService.authenticate(anyString(), anyString())).thenReturn(null);

        // Act
        ResponseEntity<UserDetailsDTO> response = userController.profileUser(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
