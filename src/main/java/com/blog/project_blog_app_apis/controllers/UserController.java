package com.blog.project_blog_app_apis.controllers;

import com.blog.project_blog_app_apis.payloads.ApiResponse;
import com.blog.project_blog_app_apis.payloads.UserDto;
import com.blog.project_blog_app_apis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable("userId") Integer uId){
        return userService.updateUser(userDto ,uId);
    }

/*    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Integer uId ){
        deleteUser(uId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser1(@PathVariable("userId") Integer uid){
        deleteUser1(uid);
        return new ResponseEntity(Map.of("message","User deleted successfully"), HttpStatus.OK);
    }*/


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUsers(@PathVariable("userId") Integer uid){
        userService.deleteUser(uid);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserByIds(@PathVariable("userId") Integer uid){
        return userService.getUserById(uid);
    }


}
