package com.java.pip.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.pip.dto.UserRequestDTO;
import com.java.pip.dto.UserResponseDTO;
import com.java.pip.dto.UserWithProductResponse;

public interface UserService {
	
	UserResponseDTO createUser(UserRequestDTO userRequestDTO);
	UserResponseDTO getUserById(Long id);
	Page<UserResponseDTO> getAllUsers(Pageable pageable);
	UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
	void deleteUser(Long id);
	UserWithProductResponse getUserWithProduct(Long userId, Long productId);

}
