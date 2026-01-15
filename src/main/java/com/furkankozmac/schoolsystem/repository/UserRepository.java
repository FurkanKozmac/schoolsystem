package com.furkankozmac.schoolsystem.repository;

import com.furkankozmac.schoolsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Since we use Email as the login ID
    Optional<User> findByEmail(String email);
}
