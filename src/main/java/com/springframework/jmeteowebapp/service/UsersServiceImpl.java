package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;

public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
//        super();
        this.usersRepository = usersRepository;
    }

    @Override
    public Users save(UserRegistrationDTO registrationDTO) {
        Users user = new Users(registrationDTO.getName(), registrationDTO.getSurname(),
                registrationDTO.getUsername(), registrationDTO.getPassword());
        return usersRepository.save(user);
    }
}
