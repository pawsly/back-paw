package com.example.pawsly.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByEmail(String email);
    boolean existsByUserid(String userid);
}