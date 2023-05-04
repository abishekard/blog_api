package com.example.blog_app_api.requestDto;

import com.example.blog_app_api.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @NotEmpty
    @Size(min=4,message = "Username must be min of 4 character")
    private String name;
    @Email(message = "Email is not valid !!")
    private String email;
    @NotEmpty
    @Size(min=3,max=10,message = "Password must be min of 3 char and max of 10 char")
    private String password;
    @NotEmpty
    private String about;

}
