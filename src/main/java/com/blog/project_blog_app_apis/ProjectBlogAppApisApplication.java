package com.blog.project_blog_app_apis;

import com.blog.project_blog_app_apis.config.AppConstants;
import com.blog.project_blog_app_apis.entities.Role;
import com.blog.project_blog_app_apis.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
public class ProjectBlogAppApisApplication  {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;


	public static void main(String[] args) {
		SpringApplication.run(ProjectBlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	public void run(String... args) throws Exception
	{
		System.out.println(this.passwordEncoder.encode("xyz"));
		try
		{
			Role role = new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_NORMAL);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}


}
