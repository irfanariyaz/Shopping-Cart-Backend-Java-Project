package com.irfanacode.dreamshops.repository;

import com.irfanacode.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String role);
}
