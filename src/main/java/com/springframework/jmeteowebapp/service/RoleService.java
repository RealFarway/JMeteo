package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
    Optional<Role> loadRoleByName(String name);
}
