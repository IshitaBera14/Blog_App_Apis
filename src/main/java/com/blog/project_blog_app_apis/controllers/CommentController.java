package com.blog.project_blog_app_apis.controllers;

import com.blog.project_blog_app_apis.entities.Comment;
import com.blog.project_blog_app_apis.payloads.ApiResponse;
import com.blog.project_blog_app_apis.payloads.CommentDto;
import com.blog.project_blog_app_apis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment , @PathVariable Integer postId)
    {
        CommentDto createComment = this.commentService.createComment(comment , postId);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!" , true), HttpStatus.OK);
    }

}
