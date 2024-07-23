package com.blog.project_blog_app_apis.repositories;

import com.blog.project_blog_app_apis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
