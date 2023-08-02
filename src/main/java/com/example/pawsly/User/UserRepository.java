package com.example.pawsly.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByUserkey(UUID userkey);
    Optional<User> findByEmail(String email);
    boolean existsByUserid(String userid);
}