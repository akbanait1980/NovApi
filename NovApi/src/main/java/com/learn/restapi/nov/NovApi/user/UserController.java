package com.learn.restapi.nov.NovApi.user;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.learn.restapi.nov.NovApi.bean.User;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/users")
	public List<User> listAll(){
		return userService.listAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<String> findUser(@PathVariable("id") int id) {
		User returnedUser = userService.findUser(id);
		
		if (returnedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("User Not Found");
		} 
				
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("User Found - " + returnedUser);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") int id) {
		User deletedUser = userService.deleteUser(id);
		
		if(deletedUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User Not Found");
		}
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("Deleted User - " + deletedUser.getId());
				
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
		
		
	}
	
	
}
