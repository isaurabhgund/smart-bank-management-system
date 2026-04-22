package com.demo.service;

import com.demo.model.User;

public interface UserService {

    User register(User user);

    User getUserByEmail(String email);
}