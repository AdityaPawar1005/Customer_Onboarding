package com.bootcamp.customer.Onboarding.service;

import com.bootcamp.customer.Onboarding.Service.EmailService;
import com.bootcamp.customer.Onboarding.constants.emailConstants;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private MailjetClient mailjetClient;

    @Mock
    private MailjetResponse mailjetResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail_Success() throws Exception {
        String to = "test@example.com";
        String userName = "testUser";

        // Mocking Mailjet response
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        when(mailjetResponse.getStatus()).thenReturn(200);

        emailService.sendEmail(to, userName);
        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }


    @Test
    public void testSendEmailNotification_Success() throws Exception {
        String to = "test@example.com";
        Plans planData = new Plans();
        planData.setPlan_name("Basic Plan");
        planData.setPlan_description("This is a basic plan.");
        planData.setPrice(9.99);
        planData.setValidity_days(30);

        // Mocking Mailjet response
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        when(mailjetResponse.getStatus()).thenReturn(200);

        emailService.sendEmailNotification(to, planData);
        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }



    @Test
    public void testSendDocumentNotification_Success() throws Exception {
        String to = "test@example.com";

        // Mocking Mailjet response
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        when(mailjetResponse.getStatus()).thenReturn(200);

        emailService.sendDocumentNotification(to);
        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }




    @Test
    public void testSendOtpEmail_Success() throws Exception {
        String to = "test@example.com";
        String userName = "testUser";
        String otp = "123456";

        // Mocking Mailjet response
        when(mailjetClient.post(any(MailjetRequest.class))).thenReturn(mailjetResponse);
        when(mailjetResponse.getStatus()).thenReturn(200);

        emailService.sendOtpEmail(to, userName, otp);
        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));
    }


}
