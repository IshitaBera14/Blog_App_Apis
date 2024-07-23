package com.blog.project_blog_app_apis.controllers;

import com.blog.project_blog_app_apis.payloads.ApiResponse;
import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("/")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto a)
    {
        return categoryService.createCategory(a);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto a ,@PathVariable Integer id)
    {
        return categoryService.updateCategory(a,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable() Integer id)
    {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/")
    public List<CategoryDto> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable("categoryId") Integer id)
    {
        return categoryService.getCategoryById(id);
    }

}
