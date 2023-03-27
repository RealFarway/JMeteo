package com.springframework.jmeteowebapp.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class UserLogin {

    @Id
    @Column(name = "login_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "login_time")
    private Timestamp loginTime;

    @Column(name = "login_success")
    private boolean loginSuccess;

    public UserLogin(User user, Timestamp loginTime, boolean loginSuccess) {
        this.user = user;
        this.loginTime = loginTime;
        this.loginSuccess = loginSuccess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}