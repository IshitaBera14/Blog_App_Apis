package com.blog.project_blog_app_apis.repositories;

import com.blog.project_blog_app_apis.entities.Category;
import com.blog.project_blog_app_apis.entities.Post;
import com.blog.project_blog_app_apis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);

}
