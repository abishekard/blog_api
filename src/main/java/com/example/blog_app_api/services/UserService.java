package com.example.blog_app_api.services;

import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.EmailAlreadyExistsException;

import java.util.List;

public interface UserService {

    Users registerUser(Users user) throws EmailAlreadyExistsException;
    Users createUser(Users user);
    Users updateUser(Users user,Integer userId);
    Users getUserByUserId(Integer userId);
    Users getUserByEmail(String email);
    void deleteUser(Integer userId);
    List<Users>  getAll();

}
