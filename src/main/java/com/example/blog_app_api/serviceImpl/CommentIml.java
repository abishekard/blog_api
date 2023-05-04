package com.example.blog_app_api.serviceImpl;

import com.example.blog_app_api.entity.Comment;
import com.example.blog_app_api.entity.Post;
import com.example.blog_app_api.entity.Users;
import com.example.blog_app_api.exceptions.ResourceNotFoundException;
import com.example.blog_app_api.repositories.UserRepository;
import com.example.blog_app_api.requestDto.CommentRequestDto;
import com.example.blog_app_api.repositories.CommentRepository;
import com.example.blog_app_api.repositories.PostRepository;
import com.example.blog_app_api.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentIml implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Comment createComment(Comment comment, int postId,int userId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        Users user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    @Override
    public void deleteComment(int commentId) {
        Comment com = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
        commentRepository.delete(com);
    }
}
