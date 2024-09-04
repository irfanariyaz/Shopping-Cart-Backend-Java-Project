package com.irfanacode.dreamshops.repository;

import com.irfanacode.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
