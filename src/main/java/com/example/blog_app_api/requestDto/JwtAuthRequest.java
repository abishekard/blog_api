package com.example.blog_app_api.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthRequest {

    private String email;
    private String password;
}
