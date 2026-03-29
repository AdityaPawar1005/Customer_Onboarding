package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;

@Entity
public class Document {
    @Id
    private Long userId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;
    private String type;
    private boolean status;
    public Document(Long userId, User user, String type) {
        this.userId = userId;
        this.user = user;
        this.type = type;
        this.status = false;
    }

    public Document() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Document{" +
                "userId=" + userId +
                ", user=" + user +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}