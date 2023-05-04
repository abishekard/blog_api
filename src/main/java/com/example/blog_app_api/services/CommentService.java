package com.example.blog_app_api.services;

import com.example.blog_app_api.entity.Comment;
import com.example.blog_app_api.requestDto.CommentRequestDto;

public interface CommentService {

    Comment createComment(Comment comment, int postId,int userId);
    void deleteComment(int commentId);
}
