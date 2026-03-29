
package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.*;
import com.bootcamp.customer.Onboarding.Service.EmailService;
import com.bootcamp.customer.Onboarding.Service.OtpService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.exceptions.ValidationException;
import com.bootcamp.customer.Onboarding.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @Mock
    private UserPlansRepository userPlansRepository;

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private OtpService otpService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");
        user.setCustomerType(1);
    }

    @Test
    void registerUser_shouldSaveUser() {
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser("testUser", "password", "test@example.com", "1234567890", 1);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendEmail("test@example.com", "testUser");
    }

    @Test
    void registerUser_usernameIsNull_shouldThrowException() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(null, "password", "test@example.com", "1234567890", 1);
        });
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    void loginUser_shouldReturnUser() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPasswordHash())).thenReturn(true);

        User loggedInUser = userService.loginUser("testUser", "password");

        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getUsername());
    }

    @Test
    void loginUser_invalidPassword_shouldReturnNull() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", user.getPasswordHash())).thenReturn(false);

        User loggedInUser = userService.loginUser("testUser", "wrongPassword");

        assertNull(loggedInUser);
    }

    @Test
    void checkIfUserExists_shouldReturnTrue() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        boolean exists = userService.checkIfUSerExists("test@example.com");

        assertTrue(exists);
    }

    @Test
    void sendOtp_shouldReturnTrue() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        boolean result = userService.sendOtp("testUser");

        assertTrue(result);
        verify(otpService, times(1)).generateAndSendOtp("test@example.com", "testUser");
    }

    @Test
    void updatePassword_shouldReturnTrue() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

        boolean result = userService.updatePassword("testUser", "newPassword");

        assertTrue(result);
        assertEquals("hashedNewPassword", user.getPasswordHash());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updatePassword_userNotFound_shouldReturnFalse() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        boolean result = userService.updatePassword("testUser", "newPassword");

        assertFalse(result);
    }

    @Test
    void authenticate_shouldReturnUserDetailsDTO() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPasswordHash())).thenReturn(true);
        when(userPlansRepository.findPlansByUserId(1L)).thenReturn(List.of(new Plans()));
        when(documentRepository.isDocumentVerified(1L)).thenReturn(true); // Mock document verification

        UserDetailsDTO userDetails = userService.authenticate("testUser", "password");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void authenticate_invalidUser_shouldReturnNull() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        UserDetailsDTO userDetails = userService.authenticate("testUser", "wrongPassword");

        assertNull(userDetails);
    }
}
