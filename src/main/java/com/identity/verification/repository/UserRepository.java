package com.identity.verification.repository;


import com.identity.verification.entity.User;
import com.identity.verification.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNin(String nin);
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "u.phoneNumber LIKE %:keyword% OR " +
            "u.nin LIKE %:keyword%")
    List<User> searchUsers(@Param("keyword") String keyword);
    List<User> findByStatus(VerificationStatus status);




}
