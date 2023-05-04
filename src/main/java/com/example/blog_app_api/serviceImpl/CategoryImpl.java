package com.example.blog_app_api.serviceImpl;

import com.example.blog_app_api.entity.Category;
import com.example.blog_app_api.exceptions.ResourceNotFoundException;
import com.example.blog_app_api.requestDto.CategoryRequestDto;
import com.example.blog_app_api.repositories.CategoryRepository;
import com.example.blog_app_api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Category createCategory(Category category) {
        Category addedCat = categoryRepository.save(category);
        return addedCat;
    }

    @Override
    public Category updateCategory(Category category, int id) {
        Category cat = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Categroy Id",id));
        cat.setTitle(category.getTitle());
        cat.setDescription(category.getDescription());

        Category updatedCat = categoryRepository.save(cat);
        return updatedCat;
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category ","id",id));
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category ","id",id));
        return category;
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
