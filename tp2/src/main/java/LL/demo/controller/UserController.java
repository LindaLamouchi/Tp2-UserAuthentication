package LL.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import LL.demo.models.*;
import LL.demo.payload.request.LoginRequest;
import LL.demo.payload.request.SignupRequest;
import LL.demo.payload.response.MessageResponse;
import LL.demo.repositories.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
		
	@GetMapping("/")
	public List<User> Users() {
		return userRepository.findAll();
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<String> Update(@PathVariable Long id,@RequestBody User u){
		User user= userRepository.findById(id).get();
		user.setPassword(u.getPassword())
	        .setUsername(u.getUsername())
	        .setEmail(u.getEmail());
		
		userRepository.save(user);
		return new ResponseEntity<>("Profile update !", HttpStatus.OK);

	}
	
	@PostMapping("/signin")
	public User authenticate(@Valid @RequestBody LoginRequest loginRequest) {
		String pass= loginRequest.getPassword();
		User u = userRepository.findByUsername(loginRequest.getUsername()).get();
		
		Role[] r = u.getRoles().toArray(new Role[0]);
		Role r1 = r[0];
			
			if(u.getPassword().equals(pass)) {
				return  (userRepository.findByUsername(loginRequest.getUsername()).get());
			}
		
		return null;
	
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
		
		return check(signUpRequest);
	}
	

	public ResponseEntity<?> 	check(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
	
	
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),signUpRequest.getPassword());
		if(user.getRoles()==null)
		{
			
			user.setRoles(null);
		}

		String strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
	
	

		if (strRoles == null) {
			Role clientRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(clientRole);
		
		} else {
		
			switch (strRoles) {
				case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
		
				default:
					Role clientRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(clientRole);
				}
		
		}
	

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
}
}
