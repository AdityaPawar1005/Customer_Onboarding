
package com.bootcamp.customer.Onboarding.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bootcamp.customer.Onboarding.Service.EmailService;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class OtpServiceTest {

    @InjectMocks
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Object getOtpStore() throws NoSuchFieldException, IllegalAccessException {
        Field otpStoreField = OtpService.class.getDeclaredField("otpStore");
        otpStoreField.setAccessible(true);
        return otpStoreField.get(otpService);
    }

    @Test
    public void testGenerateAndSendOtp_Success() {
        // Arrange
        String email = "test@example.com";
        String username = "testUser";
        // Use eq() for actual values and anyString() for OTP
        doNothing().when(emailService).sendOtpEmail(eq(email), eq(username), anyString());

        // Act
        boolean result = otpService.generateAndSendOtp(email, username);

        // Assert
        assertTrue(result);
        try {
            assertNotNull(((java.util.Map<?, ?>) getOtpStore()).get(email)); // Accessing otpStore using reflection
        } catch (Exception e) {
            fail("Failed to access otpStore via reflection");
        }
        verify(emailService, times(1)).sendOtpEmail(eq(email), eq(username), anyString());
    }

    @Test
    public void testVerifyOtp_Success() {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";
        try {
            ((java.util.Map<String, OtpService.OtpData>) getOtpStore()).put(email, new OtpService.OtpData(otp, System.currentTimeMillis()));
        } catch (Exception e) {
            fail("Failed to set OTP in otpStore via reflection");
        }

        // Act
        boolean result = otpService.verifyOtp(email, otp);

        // Assert
        assertTrue(result);
        try {
            assertNull(((java.util.Map<?, ?>) getOtpStore()).get(email)); // OTP should be removed after verification
        } catch (Exception e) {
            fail("Failed to access otpStore via reflection");
        }
    }

    @Test
    public void testVerifyOtp_Failure_DueToExpiry() throws InterruptedException {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";
        try {
            ((java.util.Map<String, OtpService.OtpData>) getOtpStore()).put(email, new OtpService.OtpData(otp, System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(3)));
        } catch (Exception e) {
            fail("Failed to set OTP in otpStore via reflection");
        }

        // Act
        boolean result = otpService.verifyOtp(email, otp);

        // Assert
        assertFalse(result);
        try {
            assertNotNull(((java.util.Map<?, ?>) getOtpStore()).get(email)); // OTP should still be there if expired
        } catch (Exception e) {
            fail("Failed to access otpStore via reflection");
        }
    }
    @Test
    public void testGenerateAndSendOtp_EmailFailure() {
        // Arrange
        String email = "test@example.com";
        String username = "testUser";
        doThrow(new RuntimeException("Email send failed")).when(emailService)
                .sendOtpEmail(eq(email), eq(username), anyString()); // Use eq() for actual values

        // Act
        boolean result = otpService.generateAndSendOtp(email, username);

        // Assert
        assertFalse(result);
        try {
            assertNotNull(((java.util.Map<?, ?>) getOtpStore()).get(email)); // OTP should still be stored
        } catch (Exception e) {
            fail("Failed to access otpStore via reflection");
        }
    }


    @Test
    public void testGenerateOtp_Length() {
        // Act
        String otp = otpService.generateOtp();

        // Assert
        assertEquals(6, otp.length());
    }
}



