package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Role;
import com.springframework.jmeteowebapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> loadRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
