//package com.java.pip.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.java.pip.dto.UserRequestDTO;
//import com.java.pip.dto.UserResponseDTO;
//import com.java.pip.entity.User;
//import com.java.pip.exception.ResourceNotFoundException;
//import com.java.pip.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//	
//	@Mock
//	private UserRepository userRepository;
//	
//	@InjectMocks
//	private UserServiceImpl userService;
//	
//	@Test
//	void testCreateUser() {
//		
//		UserRequestDTO userRequestDTO = new UserRequestDTO("hemanth","hemanth@test.com");
//		
//		when(userRepository.save(any(User.class))).thenReturn(new User(1L, "hemanth", "hemanth@test.com"));
//		
//		UserResponseDTO response = userService.createUser(userRequestDTO);
//		
//		assertEquals(1L, response.id());
//		assertEquals("hemanth", response.name());
//		verify(userRepository, times(1)).save(any(User.class));
//	}
//	
//	@Test
//	void testGetUserById() {
//		
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//		
//		assertThrows(ResourceNotFoundException.class,() -> userService.getUserById(1L));
//	}
//}