package com.blog.project_blog_app_apis.services;

import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.payloads.UserDto;

import java.util.List;

public interface CategoryService
{
    CategoryDto createCategory(CategoryDto a);

    CategoryDto updateCategory(CategoryDto a , Integer id);

    public void deleteCategory(Integer id);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Integer id);





}


// interface in andar By default public hoy aetale lakhvani jarur nathi.