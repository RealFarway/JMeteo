package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Role;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository) {
//        super();
        this.usersRepository = usersRepository;
    }

    @Override
    public Users save(UserRegistrationDTO registrationDTO) {
        Users user = new Users(registrationDTO.getName(), registrationDTO.getSurname(),
                registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        return usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");

        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
