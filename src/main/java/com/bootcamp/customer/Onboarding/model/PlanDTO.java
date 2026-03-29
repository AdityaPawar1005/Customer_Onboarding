package com.bootcamp.customer.Onboarding.model;

public class PlanDTO {
    private String planName;
    private String planDescription;
    private double price;
    private int validityDays;

    // Constructors
    public PlanDTO() {}

    public PlanDTO(String planName, String planDescription, double price, int validityDays) {
        this.planName = planName;
        this.planDescription = planDescription;
        this.price = price;
        this.validityDays = validityDays;
    }

    // Getters and Setters
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }
}
