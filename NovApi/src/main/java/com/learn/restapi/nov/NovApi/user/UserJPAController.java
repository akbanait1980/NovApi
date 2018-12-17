package com.learn.restapi.nov.NovApi.user;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learn.restapi.nov.NovApi.bean.Post;
import com.learn.restapi.nov.NovApi.bean.User;
import com.learn.restapi.nov.NovApi.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

//@Qualifier("/JPA")
@RestController
public class UserJPAController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/user/{id}")
	public ResponseEntity<String> findUser(@PathVariable("id") int id) {
		Optional<User> returnedUser = userRepository.findById(id);
		
		if (!returnedUser.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}  
		
		
				
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("User Found - " + returnedUser);
	}
	
	@GetMapping("/jpa/user-resource/{id}")
	public Resource<Optional<User>> findUserResource(@PathVariable("id") int id){
		Optional<User> returnedUser = userRepository.findById(id);
		
//		if(returnedUser == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body("User Not Found");
//		}
		
		if(!returnedUser.isPresent()) {
			throw new UserNotFoundException("id - " + id);
		}
		
		Resource<Optional<User>> resource = new Resource<>(returnedUser);
//		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listAll());
//		resource.add(linkTo.withRel("all-users"));
		
//		Link link = linkTo(UserController.class).slash("user").slash(returnedUser.getId()).withSelfRel();
//		resource.add(link.withSelfRel());
		
		List<User> allUsers = userRepository.findAll();
		
		for(User user : allUsers) {
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findUser(user.getId()));
			resource.add(linkTo.withSelfRel());
		}
		
		return resource;
		
	}
	
	@DeleteMapping("/jpa/user/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		userRepository.deleteById(id);
//		
//		if(deletedUser == null) {
////			return ResponseEntity.status(HttpStatus.NOT_FOUND)
////					.body("User Not Found");
//			
//			throw new UserNotFoundException("id - " + id);
//		}
//		
//		return ResponseEntity.status(HttpStatus.FOUND)
//				.body("Deleted User - " + deletedUser.getId());
//				
	}
	
	@PostMapping("/jpa/user")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
//		return ResponseEntity.created(location).build();
		
		return ResponseEntity.status(HttpStatus.CREATED)
					.body(null);
		
		
	}
	
/////////////---POST------------///////////////
	
	@GetMapping("/jpa-resource/user/{id}/post")
	public ResponseEntity<String> findPostResource(@PathVariable("id") int id) {
		Optional<User> returnedUser = userRepository.findById(id);
		
		if (!returnedUser.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}  
		
		
		List<Post> userPost = returnedUser.get().getPost();
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("User Found - " + returnedUser 
						+ "  ---  " + "Post Found - " + userPost);
	}
	
	
	@GetMapping("/jpa/user/{id}/post")
	public List<Post> findPost(@PathVariable("id") int id) {
		Optional<User> returnedUser = userRepository.findById(id);
		
		if (!returnedUser.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}  
		
		
		List<Post> userPost = returnedUser.get().getPost();
		
		return userPost;
	}
	
	@PostMapping("/jpa/user/{id}/post")
	public ResponseEntity<String> findPost(@PathVariable("id") int id, @RequestBody Post post) {
		Optional<User> returnedUser = userRepository.findById(id);
		
		if (!returnedUser.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body("User Not Found");
			
			throw new UserNotFoundException("id - " + id);
		}  
		
		post.setUser(returnedUser.get());
		
		postRepository.save(post);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User Found - " + returnedUser 
						+ "  ---  " + "Post - " + post);
	}
	
}
