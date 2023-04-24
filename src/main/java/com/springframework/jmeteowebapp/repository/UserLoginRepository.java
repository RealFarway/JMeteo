package com.springframework.jmeteowebapp.repository;

import com.springframework.jmeteowebapp.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByLoginIp(String loginIp);
    UserLogin findByUsername(String username);
}
