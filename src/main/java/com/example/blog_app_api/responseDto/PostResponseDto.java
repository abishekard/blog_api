package com.example.blog_app_api.responseDto;

import com.example.blog_app_api.requestDto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private int id;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryResponseDto category;
    private UserResponseDto user;
    private List<CommentResponseDto> comments;

}
