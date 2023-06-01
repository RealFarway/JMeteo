package com.springframework.jmeteowebapp.repository;

import com.springframework.jmeteowebapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    public Users findByUsername(String username);
    public Optional<Users> findById(Long id);
    public List<Users> findAll();
    public void deleteById(Long id);
}