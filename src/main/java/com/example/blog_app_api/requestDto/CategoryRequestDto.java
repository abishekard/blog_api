package com.example.blog_app_api.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {


    @NotEmpty
    @Size(min = 4,message = "minimum size of title should be 4")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "minimum size of should be 10")
    private String description;
}
