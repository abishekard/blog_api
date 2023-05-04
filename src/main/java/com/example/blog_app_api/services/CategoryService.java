package com.example.blog_app_api.services;

import com.example.blog_app_api.entity.Category;
import com.example.blog_app_api.requestDto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    Category updateCategory(Category category, int id);
    void deleteCategory(int id);
    Category getCategory(int id);
    List<Category> getCategoryList();
}
