package com.jobtrackr.server.repo;

import com.jobtrackr.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

    public interface UserRepo  extends JpaRepository<User, Long>{

        Optional<User> findByEmail(String email);
        String getUserPasswordByEmail(String email);
        boolean existsByEmail(String email);
}
