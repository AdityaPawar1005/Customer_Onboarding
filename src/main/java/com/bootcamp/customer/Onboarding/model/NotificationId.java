package com.bootcamp.customer.Onboarding.model;



import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class NotificationId implements Serializable {
    private Long userId;
    private Long planId;

    public NotificationId(){}
    public NotificationId(Long userId, Long planId) {
    }


    @Override
    public int hashCode() {
        return Objects.hash(userId, planId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NotificationId that = (NotificationId) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(planId, that.planId);
    }



}
