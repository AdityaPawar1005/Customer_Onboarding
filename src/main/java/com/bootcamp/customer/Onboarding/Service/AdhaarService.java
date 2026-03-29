package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.AdhaarRepository;
import com.bootcamp.customer.Onboarding.model.AdhaarData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdhaarService {
    @Autowired
    AdhaarRepository adhaarRepository;
    public boolean isAadhaarExists(String aadhaarNumber) {
        return adhaarRepository.existsByAadhaarNumber(aadhaarNumber);
    }
    public AdhaarData getAadhaar(Long id) {
        return adhaarRepository.findById(id).orElse(null);
    }
}
