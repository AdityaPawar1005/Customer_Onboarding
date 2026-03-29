package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.AdhaarData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdhaarRepository extends JpaRepository<AdhaarData,Long> {
    Optional<AdhaarData> findByAadhaarNumber(String aadhaarNumber);
    boolean existsByAadhaarNumber(String aadhaarNumber);

}
