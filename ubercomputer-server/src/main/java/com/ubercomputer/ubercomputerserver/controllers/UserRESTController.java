package com.ubercomputer.ubercomputerserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubercomputer.ubercomputerserver.entities.User;
import com.ubercomputer.ubercomputerserver.services.UserService;

@RestController
@RequestMapping("/api")
public class UserRESTController {

	@Autowired 
	private UserService userService;
	
	@PostMapping("/users/login")
	@CrossOrigin
	public Object[] login(@RequestBody User user) {
		return userService.login(user.getEmail(), user.getPassword());
	}
	
	@PostMapping("/users/signup")
	@CrossOrigin
	public Object[] signup(@RequestBody User user) {
		return userService.signup(user); 
	}
	
	@PostMapping("/users/forgot")
	@CrossOrigin
	public void requestPasswordReset(@RequestBody User user) {
		userService.requestPasswordReset(user.getEmail());
	}
	
	@PutMapping("/users/reset/{tokenId}/{resetToken}")
	@CrossOrigin
	public void executePasswordReset(@PathVariable int tokenId, @PathVariable String resetToken, @RequestBody User updatedUser) {
		userService.executePasswordReset(tokenId, resetToken, updatedUser);
	}
	
	@PutMapping("/users/contact/{id}")
	@CrossOrigin
	public User updateDetails(@PathVariable int id, @RequestBody User updatedUser) {
		updatedUser.setId(id);
		return userService.updateDetails(updatedUser);
	}
	
	@PutMapping("/users/password/{id}")
	@CrossOrigin
	public String updatePassword(@PathVariable int id, @RequestBody User updatedUser) {
		updatedUser.setId(id);
		return userService.updatePassword(updatedUser);
	}
}
