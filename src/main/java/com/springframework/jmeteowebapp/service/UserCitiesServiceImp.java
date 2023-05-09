package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCitiesServiceImp implements UserCitiesService{

    @Autowired
    private UsersRepository usersRepository;

    public Optional<Users> getUserById(Long userId) {
        return usersRepository.findById(userId);
    }
}
