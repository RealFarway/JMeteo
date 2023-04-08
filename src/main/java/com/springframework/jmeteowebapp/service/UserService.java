package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Users save(UserRegistrationDTO registrationDTO);


}
