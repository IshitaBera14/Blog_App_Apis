package com.blog.project_blog_app_apis.security;

import com.blog.project_blog_app_apis.entities.User;
import com.blog.project_blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog.project_blog_app_apis.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService
{
    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User" , "email : " + username , 0));
        return user;

    }
}
