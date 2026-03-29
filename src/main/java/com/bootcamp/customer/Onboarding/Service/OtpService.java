package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;




@Service
public class OtpService {


    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    private final Map<String, OtpData> otpStore = new HashMap<>();

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;
    private static final long OTP_VALIDITY_MS = TimeUnit.MINUTES.toMillis(2);

    public boolean generateAndSendOtp(String email, String username) {
        String otp = generateOtp();
        otpStore.put(email, new OtpData(otp, System.currentTimeMillis()));
        System.out.println(otpStore.get(email));

        try {
            emailService.sendOtpEmail(email, username, otp);
            return true;
        } catch (Exception e) {
            System.out.println("Error sending the email:");
            e.printStackTrace();
            return false;
        }
    }



    public boolean verifyOtp(String email, String otp){
        OtpData otpData = otpStore.get(email);
        System.out.println(email + otp);
        if(otpData!= null && otpData.getOtp().equals(otp)){
            if(System.currentTimeMillis() - otpData.getCreationTime() <= OTP_VALIDITY_MS){
                otpStore.remove(email);
                System.out.println("otp is verified");
                return true;
            }
        }
        System.out.println("otp is invalid");
        return false;
    }

    public String generateOtp(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for(int i=0;i<OTP_LENGTH;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }


    public static class OtpData {
        private final String otp;
        private final long creationTime;

        public OtpData(String otp, long creationTime) {
            this.otp = otp;
            this.creationTime = creationTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}