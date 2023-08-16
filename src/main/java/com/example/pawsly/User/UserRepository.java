package com.example.pawsly.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserKey(String userKey);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserid(String userid);
    boolean existsByEmail(String email);
    boolean existsByUserid(String userid);
}