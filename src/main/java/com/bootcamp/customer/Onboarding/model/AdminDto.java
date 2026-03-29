package com.bootcamp.customer.Onboarding.model;

public class AdminDto {
    private String username;
    private String email;
    private int customerType;
    private String documentStatus;
    private String planName;

    public AdminDto(String username, String email, int customerType, String documentStatus, String planName) {
        this.username = username;
        this.email = email;
        this.customerType = customerType;
        this.documentStatus = documentStatus;
        this.planName = planName;
    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getCustomerType() { return customerType; }
    public void setCustomerType(int customerType) { this.customerType = customerType; }

    public String getDocumentStatus() { return documentStatus; }
    public void setDocumentStatus(String documentStatus) { this.documentStatus = documentStatus; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
}
