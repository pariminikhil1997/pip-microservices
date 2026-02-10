package com.java.pip.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.pip.client.ProductClient;
import com.java.pip.dto.ProductResponseDTO;
import com.java.pip.dto.UserRequestDTO;
import com.java.pip.dto.UserResponseDTO;
import com.java.pip.dto.UserWithProductResponse;
import com.java.pip.entity.Role;
import com.java.pip.entity.User;
import com.java.pip.exception.ResourceNotFoundException;
import com.java.pip.repository.RoleRepository;
import com.java.pip.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProductClient productClient;

	public UserResponseDTO map(User user) {
		
		Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
		return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), roles);
	}
	
	@Override
	@Transactional
	public UserResponseDTO createUser(UserRequestDTO dto) {
		log.debug("In Service layer creating user with email: {}", dto.email());
		Set<Role> roles = roleRepository.findByNameIn(dto.roles());
		if (roles.isEmpty()) {
	        throw new ResourceNotFoundException("Role not found");
	    }
		User user = new User();
		user.setName(dto.name());
		user.setEmail(dto.email());
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.setRoles(roles);
	
		return map(userRepository.save(user));
	}

	@Override
	@Cacheable(value = "users", key = "#id")
	public UserResponseDTO getUserById(Long id) {
		log.debug("In Service layer Fetching user with id: {}", id);
		User user = userRepository.findById(id)
			     .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
		return map(user);
	}
	
	@Override
	public UserWithProductResponse getUserWithProduct(Long userId, Long productId) {
    	
		log.debug("In service Fetching User userId: {} with Product productId: {}", userId, productId);
    	UserResponseDTO user = getUserById(userId);
    	ProductResponseDTO product = productClient.getProduct(productId).join();
    	return new UserWithProductResponse(
                user.id(),
                user.name(),
                user.email(),
                product
        );
    }

	@Override
	public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
		log.debug("In Service layer Fetching all users");
		return userRepository.findAll(pageable).map(this::map);
	}

	@CacheEvict(value = "users", key = "#id")
	@Override
	@Transactional
	public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
		log.debug("In Service layer Updating user with id: {}", id);
		User user = userRepository.findById(id)
		              .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
		user.setName(dto.name());
		user.setEmail(dto.email());
		
		if(dto.password() != null && !dto.password().isBlank()) {
			user.setPassword(passwordEncoder.encode(dto.password()));
		}
		
		if(dto.roles() != null && !dto.roles().isEmpty()) {
			
			Set<Role> roles = roleRepository.findByNameIn(dto.roles());
			if (roles.isEmpty()) {
	            throw new ResourceNotFoundException("Role not found");
	        }
			user.setRoles(roles);
			
		}
		
		return map(userRepository.save(user));
	}

	@CacheEvict(value = "users", key = "#id")
	@Override
	@Transactional
	public void deleteUser(Long id) {
		log.debug("In Service layer Deleting user with id: {}", id);
		if(!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found with id: "+id);
		}
		userRepository.deleteById(id);
	}
}