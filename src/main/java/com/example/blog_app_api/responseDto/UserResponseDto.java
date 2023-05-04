package com.example.blog_app_api.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private int id;
    private String name;
    private String email;
    private String about;
    private List<RoleDto> roles;
}
