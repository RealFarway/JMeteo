package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Users save(UserRegistrationDTO registrationDTO);
    void updateUser(Users user);
    void updatePassword(Users user, String oldPassword, String newPassword, String confirmPassword) throws Exception;
    Users loadUserByUsername(String username) throws UsernameNotFoundException;
    Optional<Users> getUserById(Long userId);
}
