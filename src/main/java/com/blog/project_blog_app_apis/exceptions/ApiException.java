package com.blog.project_blog_app_apis.exceptions;

public class ApiException extends RuntimeException
{
    public ApiException(String message)
    {
        super(message);
    }

    public ApiException()
    {
        super();
    }
}
