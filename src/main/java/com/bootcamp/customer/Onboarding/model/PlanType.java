package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity

public class PlanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plan_type_id;
    private String plan_type_name;
    private String plan_type_description;
    public PlanType(){}

    public PlanType(Long plan_type_id, String plan_type_name, String plan_type_description) {
        this.plan_type_id = plan_type_id;
        this.plan_type_name = plan_type_name;
        this.plan_type_description = plan_type_description;
    }

    public Long getPlan_type_id() {
        return plan_type_id;
    }

    public void setPlan_type_id(Long plan_type_id) {
        this.plan_type_id = plan_type_id;
    }

    public String getPlan_type_name() {
        return plan_type_name;
    }

    public void setPlan_type_name(String plan_type_name) {
        this.plan_type_name = plan_type_name;
    }

    public String getPlan_type_description() {
        return plan_type_description;
    }

    public void setPlan_type_description(String plan_type_description) {
        this.plan_type_description = plan_type_description;
    }
}