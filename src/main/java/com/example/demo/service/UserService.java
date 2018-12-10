package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
}
