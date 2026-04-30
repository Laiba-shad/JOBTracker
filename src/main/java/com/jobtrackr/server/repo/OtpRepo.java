package com.jobtrackr.server.repo;

import com.jobtrackr.server.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OtpRepo extends JpaRepository<OtpToken, Long> {
Optional<OtpToken> findTopByEmailOrderByCreatedAtDesc(String email);
    @Modifying
    @Transactional
    @Query("UPDATE OtpToken o SET o.used = true WHERE o.email = :email AND o.used = false")
    void invalidateAllByEmail(String email);
}
