package com.project.auth.repository;

import com.project.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

}
