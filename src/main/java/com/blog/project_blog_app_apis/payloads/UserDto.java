package com.blog.project_blog_app_apis.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {

    private Integer id;

    @NotEmpty
    @Size(min = 3 , message = "User name must be min of 4 character !!")
    private String name;

    @Email(message = "Email address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3 ,max = 10 , message = "Password must be min of 3 char and max of 10 char !! ")
   // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8, 20}$")
    private String password;

    @NotEmpty
    private String about;
}
