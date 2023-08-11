package com.example.pawsly.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserKey(UUID userKey);//게시판
    Optional<User> findByEmail(String email);
    Optional<User> findByUserid(String userid);
    boolean existsByEmail(String email);
    boolean existsByUserid(String userid);
}