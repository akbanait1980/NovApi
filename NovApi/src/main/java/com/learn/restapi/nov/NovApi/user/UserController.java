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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.learn.restapi.nov.NovApi.bean.User;
import com.learn.restapi.nov.NovApi.exception.UserNotFoundException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}  
		
		
				
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("User Found - " + returnedUser);
	}
	
	@GetMapping("/user-resource/{id}")
	public Resource<User> findUserResource(@PathVariable("id") int id){
		User returnedUser = userService.findUser(id);
		
//		if(returnedUser == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body("User Not Found");
//		}
		
		Resource<User> resource = new Resource<>(returnedUser);
//		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listAll());
//		resource.add(linkTo.withRel("all-users"));
		
//		Link link = linkTo(UserController.class).slash("user").slash(returnedUser.getId()).withSelfRel();
//		resource.add(link.withSelfRel());
		
		List<User> allUsers = userService.listAll();
		
		for(User user : allUsers) {
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findUser(user.getId()));
			resource.add(linkTo.withSelfRel());
		}
		
		return resource;
		
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") int id) {
		User deletedUser = userService.deleteUser(id);
		
		if(deletedUser == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("Deleted User - " + deletedUser.getId());
				
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User savedUser = userService.saveUser(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
//		return ResponseEntity.created(location).build();
		
		return ResponseEntity.status(HttpStatus.CREATED)
					.body(null);
		
		
	}
	
	
}
