package com.example.blog_app_api.security;

import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.ResourceNotFoundException;
import com.example.blog_app_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email :"+username,0));
        return users;
    }
}
