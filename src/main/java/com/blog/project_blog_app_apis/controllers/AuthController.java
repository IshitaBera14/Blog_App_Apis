package com.blog.project_blog_app_apis.controllers;

import com.blog.project_blog_app_apis.payloads.JwtAuthRequest;
import com.blog.project_blog_app_apis.payloads.JwtAuthResponse;
import com.blog.project_blog_app_apis.payloads.UserDto;
import com.blog.project_blog_app_apis.security.JwtTokenHelper;
import com.blog.project_blog_app_apis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper JwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody JwtAuthRequest request) {
        UserDetails userDetail = userDetailsService.loadUserByUsername(request.getUsername());

// Optional: Debug print roles
        System.out.println("Authorities for user: " + request.getUsername());
        for (var authority : userDetail.getAuthorities()) {
            System.out.println("Role: " + authority.getAuthority());
        }


        try {
            // Load user by email first
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Manually match password
            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Incorrect password. Please try again."));
            }

            // Authenticate if everything is fine
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JwtTokenHelper.generateToken(userDetails);

            return ResponseEntity.ok(new JwtAuthResponse(token));

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with email: " + request.getUsername()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Something went wrong!"));
        }
    }



    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}