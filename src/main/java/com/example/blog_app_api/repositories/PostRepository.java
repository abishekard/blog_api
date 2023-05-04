package com.example.blog_app_api.repositories;

import com.example.blog_app_api.entity.Category;
import com.example.blog_app_api.entity.Post;
import com.example.blog_app_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(Users user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
