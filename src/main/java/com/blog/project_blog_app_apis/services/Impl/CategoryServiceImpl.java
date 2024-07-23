package com.blog.project_blog_app_apis.services.Impl;

import com.blog.project_blog_app_apis.entities.Category;
import com.blog.project_blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.repositories.CategoryRepo;
import com.blog.project_blog_app_apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto a) {

        Category cat = this.modelMapper.map(a,Category.class);
        Category addData = this.categoryRepo.save(cat);

        return this.modelMapper.map(addData , CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto a, Integer cid) {

        Category cat = this.categoryRepo.findById(cid)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",cid));

        cat.setCategoryTitle(a.getCategoryTitle());
        cat.setCategoryDescription(a.getCategoryDescription());

        Category update = this.categoryRepo.save(cat);
        return this.modelMapper.map(update , CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {

        Category cat = this.categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",id));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((cat) ->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos ;
    }

    @Override
    public void deleteCategory(Integer id) {

        Category cat = this.categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",id));

        this.categoryRepo.delete(cat);

    }
}
