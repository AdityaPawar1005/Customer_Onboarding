package com.bootcamp.customer.Onboarding.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.*;

import java.util.List;

@Entity


public class CustomerType {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int customerTypeId;
        private String typeName;
        private String description;
    public CustomerType(){}
    public CustomerType(int customerTypeId, String typeName, String description) {
        this.customerTypeId = customerTypeId;
        this.typeName = typeName;
        this.description = description;
    }

    public int getCustomerTypeId() {

        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
