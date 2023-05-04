package com.example.blog_app_api;

import com.example.blog_app_api.entity.Role;
import com.example.blog_app_api.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static com.example.blog_app_api.config.AppConstants.ROLE_ADMIN;
import static com.example.blog_app_api.config.AppConstants.ROLE_USER;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		Role role1 = new Role();
		role1.setId(ROLE_ADMIN);
		role1.setName("ROLE_ADMIN");
		Role role2 = new Role();
		role2.setId(ROLE_USER);
		role2.setName("ROLE_USER");
		List<Role> list = List.of(role1,role2);
		roleRepository.saveAll(list);
	}
}
