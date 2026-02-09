package com.java.pip.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.pip.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Set<Role> findByNameIn(Set<String> names);

	Optional<Role> findByName(String name);
}