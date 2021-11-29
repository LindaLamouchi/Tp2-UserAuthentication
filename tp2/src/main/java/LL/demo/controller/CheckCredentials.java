package LL.demo.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import LL.demo.models.ERole;
import LL.demo.models.Role;
import LL.demo.models.User;
import LL.demo.payload.request.SignupRequest;
import LL.demo.payload.response.MessageResponse;
import LL.demo.repositories.RoleRepository;
import LL.demo.repositories.UserRepository;

public class CheckCredentials {

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
		
	
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
