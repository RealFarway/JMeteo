package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.exception.UserNotFoundException;
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
import java.util.List;
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

        return user;
    }

    @Override
    public void updateUser(Users user) {
        Optional<Users> existingUserOptional = usersRepository.findById(user.getId());

        if (!existingUserOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Users existingUser = existingUserOptional.get();

        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());

        usersRepository.save(existingUser);
    }

    public void updateRoles(Users user, Collection<Role> roles) throws Exception {
        Optional<Users> existingUserOptional = usersRepository.findById(user.getId());

        if (!existingUserOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Users existingUser = existingUserOptional.get();

        existingUser.setRoles(roles);

        usersRepository.save(existingUser);
    }

    @Override
    public void updatePassword(Users user, String currentPassword, String newPassword, String confirmPassword) throws Exception {
        // Confirm that the current password is correct
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        // Confirm that the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("New password and confirm password do not match");
        }

        // If everything checks out, encode the new password and save it
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    @Override
    public Optional<Users> getUserById(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);

        return user;
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> users = usersRepository.findAll();

        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
