package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;

public interface UsersService {
    Users save(UserRegistrationDTO registrationDTO);

}
