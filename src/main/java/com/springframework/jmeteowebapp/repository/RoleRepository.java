package com.springframework.jmeteowebapp.repository;

import com.springframework.jmeteowebapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findFirstByName(String name);
}
