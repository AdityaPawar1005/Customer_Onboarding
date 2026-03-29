package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;

@Entity
public class AdhaarData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 14)
    private String aadhaarNumber;
    public AdhaarData() {}
    public AdhaarData(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
