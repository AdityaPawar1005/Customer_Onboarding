package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;


@Entity


public class Notification implements Serializable {
    @EmbeddedId
    private NotificationId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @MapsId("planId")
    @JoinColumn(name = "planId", referencedColumnName = "planId")
    private Plans plan;

    @Column(nullable = false)
    private boolean status;

    public Notification() {
    }

    public Notification(NotificationId id, User user, Plans plan, boolean status) {
        this.id = id;
        this.user = user;
        this.plan = plan;
        this.status = status;
    }

    public NotificationId getId() {
        return id;
    }

    public void setId(NotificationId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plans getPlan() {
        return plan;
    }

    public void setPlan(Plans plan) {
        this.plan = plan;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}