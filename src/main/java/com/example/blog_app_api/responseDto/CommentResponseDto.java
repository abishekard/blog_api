package com.example.blog_app_api.responseDto;

import com.example.blog_app_api.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private int id;
    private String content;
    private UserResponseDto user;
}
