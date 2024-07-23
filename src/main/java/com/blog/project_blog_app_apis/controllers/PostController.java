package com.blog.project_blog_app_apis.controllers;

import com.blog.project_blog_app_apis.config.AppConstants;
import com.blog.project_blog_app_apis.payloads.ApiResponse;
import com.blog.project_blog_app_apis.payloads.CategoryDto;
import com.blog.project_blog_app_apis.payloads.PostDto;
import com.blog.project_blog_app_apis.payloads.PostResponse;
import com.blog.project_blog_app_apis.services.FileService;
import com.blog.project_blog_app_apis.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {


    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public PostDto createPost( @RequestBody PostDto a,
                                     @PathVariable Integer userId,
                                      @PathVariable Integer categoryId)
    {
        return postService.createPost(a,userId,categoryId);
    }

    @PutMapping("/posts/{postId}")
    public PostDto updatePosts(@RequestBody PostDto postDto , @PathVariable Integer postId)
    {
        return postService.updatePost(postDto,postId);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);

    }

    @GetMapping("/posts")
    public List<PostDto> getAllPosts()
    {
        return postService.getAllPosts();
    }

    @GetMapping("/pagination/posts")
    public List<PostDto> getAllPostsByPagination(@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
                                                 @RequestParam(value = "pageSize",defaultValue = "5" , required = false)Integer pageSize)
    {
        return postService.getAllPostsByPagination(pageNumber,pageSize);
    }

    @GetMapping("/response/pagination/posts")
    public PostResponse getAllPostsByPaginationResponse(@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
                                                              @RequestParam(value = "pageSize",defaultValue = "5" , required = false)Integer pageSize)
    {
        return postService.getAllPostsByPaginationResponse(pageNumber,pageSize);
    }

    @GetMapping("/response/pagination/posts/sort") // u cane directly write a constant value or call from a class
    public PostResponse getAllPostsBySorting(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                             @RequestParam(value = "pageSize",defaultValue = "5" , required = false)Integer pageSize,
                                             @RequestParam(value = "sortBy" , defaultValue = "postId" , required = false) String sortBy,
                                             @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir)
    {
        return postService.getAllPostsBySorting(pageNumber,pageSize,sortBy,sortDir);
    }

    @GetMapping("posts/{id}")
    public PostDto getPostByIdS(@PathVariable Integer id)
    {
        return postService.getPostById(id);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategories(@PathVariable Integer categoryId)
    {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
    {
        List<PostDto> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/search/posts/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords)
    {
        List<PostDto> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

    }

    //image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId)throws IOException  {

        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path , image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto , postId);

        return new ResponseEntity<>(updatePost , HttpStatus.OK);
    }

    //method to serve file
    @GetMapping(value = "/post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path , imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource , response.getOutputStream());
    }


}
