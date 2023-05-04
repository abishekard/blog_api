package com.example.blog_app_api.serviceImpl;

import com.example.blog_app_api.entity.Category;
import com.example.blog_app_api.entity.Post;
import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.ResourceNotFoundException;
import com.example.blog_app_api.responseDto.PostResponseDto;
import com.example.blog_app_api.responseDto.PostResponse;
import com.example.blog_app_api.repositories.CategoryRepository;
import com.example.blog_app_api.repositories.PostRepository;
import com.example.blog_app_api.repositories.UserRepository;
import com.example.blog_app_api.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostIml implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Post createPost(Post newPost, int userId, int categoryId) {
        Users users = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        newPost.setImageName("default.png");
        newPost.setAddedDate(new Date());
        newPost.setUser(users);
        newPost.setCategory(category);
        Post savedPost = postRepository.save(newPost);
        return savedPost;
    }

    @Override
    public Post updatePost(Post newPost, int postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
       // post.setImageName(newPost.getImageName());
        Post updatedPost = postRepository.save(post);
        return updatedPost;
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).descending());
        Page<Post> postPage = postRepository.findAll(p);
        List<Post> allPost = postPage.getContent();
        List<PostResponseDto> list = allPost.stream().map((data)->modelMapper.map(data, PostResponseDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(list);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public Post getPostById(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        return post;
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> postList = postRepository.findByCategory(category);
        //List<PostDto> list = postList.stream().map((data)->modelMapper.map(data,PostDto.class)).collect(Collectors.toList());
        return postList;
    }

    @Override
    public List<Post> getPostByUser(int userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        List<Post> postList = postRepository.findByUser(user);
       // List<PostDto> list = postList.stream().map((data)->modelMapper.map(data,PostDto.class)).collect(Collectors.toList());
        return postList;
    }

    @Override
    public List<Post> searchPost(String keyWord) {
        List<Post> postList = postRepository.findByTitleContaining(keyWord);
        //List<PostDto> list = postList.stream().map((data)->modelMapper.map(data,PostDto.class)).collect(Collectors.toList());
        return postList;
    }
}
