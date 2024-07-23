package com.blog.project_blog_app_apis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}