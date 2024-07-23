package com.blog.project_blog_app_apis.services.Impl;

import com.blog.project_blog_app_apis.entities.User;
import com.blog.project_blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog.project_blog_app_apis.payloads.UserDto;
import com.blog.project_blog_app_apis.repositories.UserRepo;
import com.blog.project_blog_app_apis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {                //UserDto = class name

        User user = dtoToUser(userDto);
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos =users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto,User.class);
        return user;

    /*  User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        return user;*/

    }

    public UserDto userToDto(User user)
    {
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;

     /*   UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;*/
    }
}
