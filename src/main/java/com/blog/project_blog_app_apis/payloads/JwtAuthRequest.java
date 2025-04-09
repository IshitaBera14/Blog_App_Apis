package com.blog.project_blog_app_apis.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest
{
    private String username;

    private String password;

}
