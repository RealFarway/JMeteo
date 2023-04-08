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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "login_time")
    private Timestamp loginTime;

    @Column(name = "login_success")
    private boolean loginSuccess;

    public UserLogin(){

    }

    public UserLogin(Users user, Timestamp loginTime, boolean loginSuccess) {
        this.user = user;
        this.loginTime = loginTime;
        this.loginSuccess = loginSuccess;
    }

    public Long getId() {
        return id;
    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
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