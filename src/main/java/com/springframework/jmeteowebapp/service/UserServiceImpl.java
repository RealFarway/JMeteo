package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.Role;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.RoleRepository;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Users save(UserRegistrationDTO registrationDTO) {
        Users user = new Users(registrationDTO.getName(), registrationDTO.getSurname(),
                registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword()), Arrays.asList(roleRepository.findFirstByName("ROLE_USER")));
        return usersRepository.save(user);
    }

    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return user;
    }

    @Override
    public Optional<Users> getUserById(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);

        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
