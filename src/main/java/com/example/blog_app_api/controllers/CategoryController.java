package com.example.blog_app_api.controllers;

import com.example.blog_app_api.entity.Category;
import com.example.blog_app_api.responseDto.ApiResponse;
import com.example.blog_app_api.requestDto.CategoryRequestDto;
import com.example.blog_app_api.responseDto.CategoryResponseDto;
import com.example.blog_app_api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    //create
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryDto)
    {
        Category createdCategory = categoryService.createCategory(modelMapper.map(categoryDto, Category.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdCategory,CategoryResponseDto.class));
    }
    //update
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid  @RequestBody CategoryRequestDto categoryDto, @PathVariable("id") int id)
    {
         Category updateCategory =  categoryService.updateCategory(modelMapper.map(categoryDto,Category.class),id);
         return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(updateCategory,CategoryResponseDto.class));
    }
    //delete
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("category deleted",true));
    }

    //get
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id)
    {
        Category categoryDto = categoryService.getCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(categoryDto, CategoryResponseDto.class));
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory()
    {
        List<Category> list = categoryService.getCategoryList();
        List<CategoryResponseDto> list1 = list.stream().map((data)->modelMapper.map(data,CategoryResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(list1);
    }


}
