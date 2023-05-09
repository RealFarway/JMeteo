package com.springframework.jmeteowebapp.handler;

import com.springframework.jmeteowebapp.model.UserLogin;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.UserLoginRepository;
import com.springframework.jmeteowebapp.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    private UserLoginRepository userLoginRepository;
    @Autowired
    @Lazy
    private UserServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.web.authentication.AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        // Save a log of the successful login
        UserLogin log = new UserLogin(authentication.getName(), new Timestamp(System.currentTimeMillis()), request.getRemoteAddr(), true);
        userLoginRepository.save(log);

        // Retrieve user ID
        Long userId = ((Users) authentication.getPrincipal()).getId();

        // Construct the personalized URL.
        String targetUrl = "/user/" + userId;

        // Redirect the user to their personalized URL.
        response.sendRedirect(targetUrl);
    }
}
