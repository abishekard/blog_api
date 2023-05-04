package com.example.blog_app_api.repositories;


import com.example.blog_app_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
}
