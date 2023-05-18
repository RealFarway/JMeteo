package com.springframework.jmeteowebapp.handler;

import com.springframework.jmeteowebapp.model.UserLogin;
import com.springframework.jmeteowebapp.repository.UserLoginRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Save a log of the failed login attempt
        UserLogin log = new UserLogin(request.getParameter("username"), new Timestamp(System.currentTimeMillis()), request.getRemoteAddr(), false);
        userLoginRepository.save(log);

        response.sendRedirect("login?error");
    }
}
