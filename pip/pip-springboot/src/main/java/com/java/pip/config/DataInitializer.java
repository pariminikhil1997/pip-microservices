package com.java.pip.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.java.pip.entity.Role;
import com.java.pip.entity.User;
import com.java.pip.repository.RoleRepository;
import com.java.pip.repository.UserRepository;

@Configuration
public class DataInitializer {
	
	@Bean
	CommandLineRunner initData(RoleRepository roleRepo,UserRepository userRepo,
			                   PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole = roleRepo.findByName("ROLE_ADMIN")
					         .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
			
			Role userRole = roleRepo.findByName("ROLE_USER")
                            .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));
			
			if (userRepo.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin@123"));
                admin.setRoles(Set.of(adminRole, userRole));

                userRepo.save(admin);
            }
			
		};
	}
}