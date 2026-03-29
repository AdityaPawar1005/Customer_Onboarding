package com.bootcamp.customer.Onboarding.model;

import java.util.List;

public class UserDetailsDTO {
    private String username;
    private String name;
    private String phone;
    private int customerType;
    private String email;

    public UserDetailsDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private List<PlanDTO> plans; // List of plans
    private boolean documentVerification;

    public boolean isDocumentVerification() {
        return documentVerification;
    }

    public void setDocumentVerification(boolean documentVerification) {
        this.documentVerification = documentVerification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public List<PlanDTO> getPlans() {
        return plans;
    }

    public void setPlans(List<PlanDTO> plans) {
        this.plans = plans;
    }

    public UserDetailsDTO(String username, String phone, int customerType, List<PlanDTO> plans, boolean documentVerification,String email) {
        this.username = username;
        this.phone = phone;
        this.customerType = customerType;
        this.plans = plans;
        this.documentVerification = documentVerification;
        this.email=email;
    }
}
