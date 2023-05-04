package com.example.blog_app_api.services;

import com.example.blog_app_api.entity.Post;
import com.example.blog_app_api.responseDto.PostResponse;

import java.util.List;

public interface PostService {

    Post createPost(Post post, int userId, int categoryId);
    Post updatePost(Post post,int postId);
    void deletePost(int postId);
    PostResponse getAllPost(int pageNumber, int pageSize,String sortBy);
    Post getPostById(int postId);
    List<Post> getPostByCategory(int categoryId);
    List<Post> getPostByUser(int userId);
    List<Post> searchPost(String keyWord);
}
