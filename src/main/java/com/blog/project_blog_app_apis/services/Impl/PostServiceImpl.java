package com.blog.project_blog_app_apis.services.Impl;


import com.blog.project_blog_app_apis.entities.Category;
import com.blog.project_blog_app_apis.entities.Post;
import com.blog.project_blog_app_apis.entities.User;
import com.blog.project_blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.payloads.PostDto;
import com.blog.project_blog_app_apis.payloads.PostResponse;
import com.blog.project_blog_app_apis.repositories.CategoryRepo;
import com.blog.project_blog_app_apis.repositories.PostRepo;
import com.blog.project_blog_app_apis.repositories.UserRepo;
import com.blog.project_blog_app_apis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto , Integer userId , Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","cid",categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost , PostDto.class);


    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post p = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        p.setTitle(postDto.getTitle());
        p.setContent(postDto.getContent());
        p.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(p);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post p = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        this.postRepo.delete(p);

    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> p = this.postRepo.findAll();
        List<PostDto> pd = p.stream().map((singlePost)->this.modelMapper.map(singlePost,PostDto.class)).collect(Collectors.toList());
        return pd;
    }

    @Override
    public List<PostDto> getAllPostsByPagination(Integer pageNumber, Integer pageSize) {

        Pageable p = PageRequest.of(pageNumber , pageSize);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();
        List<PostDto> pd = allPost.stream().map((singlePost)->this.modelMapper.map(singlePost,PostDto.class)).collect(Collectors.toList());
        return pd;
    }

    @Override
    public PostResponse getAllPostsByPaginationResponse(Integer pageNumber, Integer pageSize) {

        Pageable p = PageRequest.of(pageNumber , pageSize);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();
        List<PostDto> pd = allPost.stream().map((singlePost)->this.modelMapper.map(singlePost,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(pd);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getAllPostsBySorting(Integer pageNumber, Integer pageSize , String sortBy ,String sortDir ) {

        // Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort = Sort.by(sortBy).ascending();
        }
        else
        {
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();
        List<PostDto> pd = allPost.stream().map((singlePost)->this.modelMapper.map(singlePost,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(pd);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer id) {

        Post p = this.postRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("post", "postId", id));
        return  this.modelMapper.map(p,PostDto.class);


    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category cat =this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category", "category Id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos =posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user =this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos =posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> postDto =posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDto;
    }
}
