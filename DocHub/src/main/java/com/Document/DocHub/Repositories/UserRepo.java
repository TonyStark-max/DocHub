package com.Document.DocHub.Repositories;

import com.Document.DocHub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> deleteByName(String name);
}
