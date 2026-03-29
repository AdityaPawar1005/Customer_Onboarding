package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUserId(Long userId);
    Optional<Document> findById(Long userId);
    void deleteById(Long userId);
    @Query("SELECT COUNT(up) > 0 FROM Document up WHERE up.user.userId = :userId AND up.status = true" )
    boolean isDocumentVerified(@Param("userId") Long userId);

    //admin
    List<Document> findByStatus(boolean status);
}
