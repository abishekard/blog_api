package com.example.blog_app_api.controllers;

import com.example.blog_app_api.entity.Comment;
import com.example.blog_app_api.responseDto.ApiResponse;
import com.example.blog_app_api.requestDto.CommentRequestDto;
import com.example.blog_app_api.responseDto.CommentResponseDto;
import com.example.blog_app_api.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentDto, @PathVariable int postId,@PathVariable int userId)
    {
        Comment createdComment  = commentService.createComment(modelMapper.map(commentDto, Comment.class),postId,userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdComment, CommentResponseDto.class));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int postId)
    {
        commentService.deleteComment(postId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("comment deleted",true));
    }
}
