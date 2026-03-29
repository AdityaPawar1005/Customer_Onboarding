package com.bootcamp.customer.Onboarding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity

public class UserPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_plan_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private Plans plan;

    private Date activation_date;
    private Date expiration_date;

    public UserPlans(Long user_plan_id, User user, Plans plan, Date activation_date, Date expiration_date) {
        this.user_plan_id = user_plan_id;
        this.user = user;
        this.plan = plan;
        this.activation_date = activation_date;
        this.expiration_date = expiration_date;
    }

    public UserPlans() {
    }

    public Long getUser_plan_id() {
        return user_plan_id;
    }

    public void setUser_plan_id(Long user_plan_id) {
        this.user_plan_id = user_plan_id;
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

    public Date getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(Date activation_date) {
        this.activation_date = activation_date;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }
}