package com.shakti.service;

import com.shakti.model.User;

public interface UserService {

    public User findUserById(Long userId) throws Exception;
}
