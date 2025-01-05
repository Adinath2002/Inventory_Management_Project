package com.project.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.project.inventory.dtos.UserDTO;
import com.project.inventory.dtos.UserResponseDto;
import com.project.inventory.dtos.UserUpdateDTO;
import com.project.inventory.exceptions.ResourceNotFoundException;
import com.project.inventory.dtos.LoginRequestDTO;
import com.project.inventory.model.User;
import com.project.inventory.services.OrderService;
import com.project.inventory.services.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OrderService orderService;

//    @PostMapping("/register")
//    public UserDTO registerUser(@Valid @RequestBody UserDTO userDTO) {
//        User user = userService.saveUser(userDTO);
//        return userService.toDTO(user);
//    }

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
		User user = userService.saveUser(userDTO);
		String message = "ID: " + user.getId() + "\n" + "Username: " + user.getUsername() + "\n" + "Encoded Password: "
				+ user.getPassword() + "\n" + "Role: " + user.getRole() + "\n" + "User Registered successfully";
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/login")
	public String loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "Login successful!";
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers().stream().map(userService::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public UserResponseDto getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);

			System.out.println("User with ID " + id + " deleted successfully from the database.");
			return ResponseEntity.ok("User deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println("User with ID " + id + " not found in the database.");
			return ResponseEntity.status(404).body("User not found.");
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
	    User updatedUser = userService.updateUser(id, userUpdateDTO);
	    UserDTO updatedUserDTO = userService.toDTO(updatedUser);
	    return ResponseEntity.ok(updatedUserDTO);
	}



	 

 
}
