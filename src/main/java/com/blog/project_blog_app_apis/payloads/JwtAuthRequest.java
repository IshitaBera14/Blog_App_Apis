package com.blog.project_blog_app_apis.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtAuthRequest {
    private String username;
    private String password;

}




