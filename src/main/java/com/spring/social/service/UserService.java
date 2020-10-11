package com.spring.social.service;


import com.spring.social.model.User;
import com.spring.social.payload.request.LoginRequest;
import com.spring.social.payload.request.UserCreateRequest;
import com.spring.social.payload.response.JwtResponse;

public interface UserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
    User getUser(String id);
    User createUser(UserCreateRequest userCreateRequest);

}
