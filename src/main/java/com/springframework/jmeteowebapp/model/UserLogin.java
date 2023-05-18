package com.springframework.jmeteowebapp.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "UserLogin")
public class UserLogin {

    @Id
    @Column(name = "login_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_username")
    private String username;

    @Column(name = "login_time")
    private Timestamp loginTime;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_success")
    private boolean loginSuccess;

    public UserLogin(){

    }

    public UserLogin(String username, Timestamp loginTime, String loginIP, boolean loginSuccess) {
        this.username = username;
        this.loginTime = loginTime;
        this.loginIp = loginIP;
        this.loginSuccess = loginSuccess;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}