package com.blog.project_blog_app_apis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private List<PostDto> content;    //post nu content
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private Boolean lastPage;

}
