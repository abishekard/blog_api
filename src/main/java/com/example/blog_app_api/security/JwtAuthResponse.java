package com.example.blog_app_api.security;

import com.example.blog_app_api.responseDto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String token;
    private UserResponseDto user;
}
