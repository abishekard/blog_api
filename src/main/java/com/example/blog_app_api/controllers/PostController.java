package com.example.blog_app_api.controllers;

import com.example.blog_app_api.config.AppConstants;
import com.example.blog_app_api.entity.Post;
import com.example.blog_app_api.requestDto.PostRequestDto;
import com.example.blog_app_api.responseDto.ApiResponse;
import com.example.blog_app_api.responseDto.PostResponseDto;
import com.example.blog_app_api.responseDto.PostResponse;
import com.example.blog_app_api.services.FileService;
import com.example.blog_app_api.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postDto, @PathVariable("userId") int userId, @PathVariable("categoryId") int categoryId) {
        Post createdPost = postService.createPost(modelMapper.map(postDto,Post.class), userId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(createdPost,PostRequestDto.class));
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<?> getPostByUser(@PathVariable int userId) {
        List<Post> list = postService.getPostByUser(userId);
        List<PostResponseDto> posts = list.stream().map((data)->modelMapper.map(data,PostResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<?> getPostByCategory(@PathVariable int categoryId) {
        List<Post> posts = postService.getPostByCategory(categoryId);
        List<PostResponseDto> list = posts.stream().map((data)->modelMapper.map(data,PostResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/post/getAll")
    public ResponseEntity<?> getAllPost(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable int postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(post,PostResponseDto.class));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Post deleted", true));
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable int postId, @RequestBody PostRequestDto postDto) {
        Post updatedPost = postService.updatePost(modelMapper.map(postDto,Post.class), postId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedPost,PostResponseDto.class));
    }

    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<?> searchPostByTitle(@PathVariable("keyword") String keyword) {
        List<Post> result = postService.searchPost(keyword);
        List<PostResponseDto> list = result.stream().map((data)->modelMapper.map(data, PostResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int postId) throws IOException {
        Post post = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        post.setImageName(fileName);
        Post updatedPost = postService.updatePost(post, postId);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updatedPost, PostResponseDto.class));
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
