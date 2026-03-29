package com.bootcamp.customer.Onboarding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
//import lombok.*;

import java.util.List;


@Entity


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private int customerType;
    @ManyToOne
    @JoinColumn(name = "customerType", referencedColumnName = "customerTypeId", insertable = false, updatable = false)
    private CustomerType customerTypeEntity;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserPlans> userPlans;
    public User(){}

    public User(Long userId, String username, String passwordHash, String email, String phoneNumber, int customerType, CustomerType customerTypeEntity, List<UserPlans> userPlans) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
        this.customerTypeEntity = customerTypeEntity;
        this.userPlans = userPlans;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public CustomerType getCustomerTypeEntity() {
        return customerTypeEntity;
    }

    public void setCustomerTypeEntity(CustomerType customerTypeEntity) {
        this.customerTypeEntity = customerTypeEntity;
    }

    public List<UserPlans> getUserPlans() {
        return userPlans;
    }

    public void setUserPlans(List<UserPlans> userPlans) {
        this.userPlans = userPlans;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", customerType=" + customerType +
                ", customerTypeEntity=" + customerTypeEntity +
                ", userPlans=" + userPlans +
                '}';
    }
}
