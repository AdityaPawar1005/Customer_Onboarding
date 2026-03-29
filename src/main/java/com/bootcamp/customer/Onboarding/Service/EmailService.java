package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.constants.emailConstants;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class EmailService {

    @Autowired
    private MailjetClient mailjetClient;

    public void sendEmail(String to, String userName) {
        // Create JSON object for email
        JSONObject email = new JSONObject();
        email.put("From", new JSONObject().put("Email", emailConstants.EMAIL_FROM));
        email.put("To", new JSONArray().put(new JSONObject().put("Email", to)));
        email.put("Subject", "Welcome to Our Company, " + userName + "!");

        // Create HTML content for the email with a warm welcome message
        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h1 style='color: #4CAF50;'>Welcome to Unitel, " + userName + "!</h1>"
                + "<p>Dear " + userName + ",</p>"
                + "<p>We are thrilled to have you join our community. At our company, we are committed to providing you with the best user service and experience possible.</p>"
                + "<p style='color: #2196F3;'>Our team is dedicated to ensuring that you have everything you need to succeed and enjoy your time with us.</p>"
                + "<p>If you have any questions or need assistance, please do not hesitate to reach out to our support team. We are here to help you every step of the way.</p>"
                + "<p>Thank you for choosing us. We look forward to a wonderful journey together!</p>"
                + "<p>Best regards,</p>"
                + "<p>Unitel</p>"
                + "</body>"
                + "</html>";

        email.put("HTMLPart", htmlContent);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(email));

        try {
            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() == 200) {
                System.out.println("Email sent successfully.");
            } else {
                String responseBody = response.getData().toString();
                throw new RuntimeException("Failed to send email. Status code: " + response.getStatus() + ", Response body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }


    public void sendEmailNotification(String to, Plans planData) {

        LocalDate activationDate = LocalDate.now(); // Assuming activation date is today
        LocalDate expirationDate = activationDate.plusDays(planData.getValidity_days());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Create JSON object for email
        JSONObject email = new JSONObject();
        email.put("From", new JSONObject().put("Email", emailConstants.EMAIL_FROM));
        email.put("To", new JSONArray().put(new JSONObject().put("Email", to)));
        email.put("Subject", "Your New Plan: " + planData.getPlan_name());

        // Create HTML content for the email with colors
        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h1 style='color: #4CAF50;'>Welcome to Your New Plan!</h1>"
                + "<p>Dear Customer,</p>"
                + "<p>We are excited to introduce you to your new plan:</p>"
                + "<h2 style='color: #2196F3;'>" + planData.getPlan_name() + "</h2>"
                + "<p><strong>Description:</strong> " + planData.getPlan_description() + "</p>"
                + "<p><strong>Price:</strong> <span style='color: #FF5722;'>$" + planData.getPrice() + "</span></p>"
                + "<p><strong>Validity:</strong> " + planData.getValidity_days() + " days</p>"
                + "<p><strong>Valid Till:</strong> <span style='color: red;'>" + expirationDate.format(formatter) + "</span></p>"
                + "<p>Thank you for choosing our service</p>"
                + "<p>Your Plan is now activated!!</p>"
                + "<p>Best regards,</p>"
                + "<p>Unitel</p>"
                + "</body>"
                + "</html>";

        email.put("HTMLPart", htmlContent);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(email));

        try {
            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() == 200) {
                System.out.println("Email sent successfully for service activation.");
            } else {
                String responseBody = response.getData().toString();
                throw new RuntimeException("Failed to send email. Status code: " + response.getStatus() + ", Response body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }

    public void sendDocumentNotification(String to) {
        // Create JSON object for email
        JSONObject emailContent = new JSONObject();
        emailContent.put("From", new JSONObject().put("Email", emailConstants.EMAIL_FROM));
        emailContent.put("To", new JSONArray().put(new JSONObject().put("Email", to)));
        emailContent.put("Subject", "Document Verification Successful");

        // Create HTML content for the email with colors
        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h1 style='color: #4CAF50;'>Document Verification Successful!</h1>"
                + "<p>Dear Customer,</p>"
                + "<p>We are pleased to inform you that your documents have been successfully verified.</p>"
                + "<p style='color: #2196F3;'>Thank you for completing the verification process.</p>"
                + "<p>If you have any questions or need further assistance, please do not hesitate to contact us.</p>"
                + "<p>Best regards,</p>"
                + "<p>Unitel</p>"
                + "</body>"
                + "</html>";

        emailContent.put("HTMLPart", htmlContent);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(emailContent));

        try {
            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() == 200) {
                System.out.println("Email sent successfully for document verification");
            } else {
                String responseBody = response.getData().toString();
                throw new RuntimeException("Failed to send email. Status code: " + response.getStatus() + ", Response body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }

    public void sendOtpEmail(String to, String userName, String otp) {

        JSONObject emailContent = new JSONObject();
        emailContent.put("From", new JSONObject().put("Email", emailConstants.EMAIL_FROM));
        emailContent.put("To", new JSONArray().put(new JSONObject().put("Email", to)));
        emailContent.put("Subject", "OTP for Login");

        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h1 style='color: #4CAF50;'>Your OTP Code</h1>"
                + "<p>Dear " + userName + ",</p>"
                + "<p>Thank you for using our service. Your OTP code is: <strong>" + otp + "</strong></p>"
                + "<p>This code is valid for 2 minutes. Please use it to complete your verification.</p>"
                + "<p>If you did not request this OTP, please ignore this email.</p>"
                + "<p>Best regards,</p>"
                + "<p>Unitel</p>"
                + "</body>"
                + "</html>";

        emailContent.put("HTMLPart", htmlContent);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(emailContent));

        try {
            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() == 200) {
                System.out.println("Email sent successfully for document verification");
            } else {
                String responseBody = response.getData().toString();
                throw new RuntimeException("Failed to send email. Status code: " + response.getStatus() + ", Response body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }


    }

}
