package com.java.pip.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.pip.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = "roles")
	Optional<User> findById(Long id);
	
	@EntityGraph(attributePaths = "roles")
	Optional<User> findByEmail(String email);
	
	@EntityGraph(attributePaths = "roles")
	Page<User> findAll(Pageable pageable);
}