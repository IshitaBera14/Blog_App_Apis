package com.blog.project_blog_app_apis.services;

import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.payloads.PostDto;
import com.blog.project_blog_app_apis.payloads.PostResponse;

import java.util.List;

public interface PostService
{
    PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);
    PostDto updatePost(PostDto postDto , Integer id);
    public void deletePost(Integer id);
    List<PostDto> getAllPosts();
    List<PostDto> getAllPostsByPagination(Integer pageNumber,Integer pageSize);
    PostResponse getAllPostsByPaginationResponse(Integer pageNumber, Integer pageSize);
    PostResponse getAllPostsBySorting(Integer pageNumber, Integer pageSize , String sortBy , String sortDir);
    PostDto getPostById(Integer id);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);


}
