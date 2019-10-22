package com.ubercomputer.ubercomputerserver.services;

import com.ubercomputer.ubercomputerserver.entities.User;

public interface UserService {
	
	public Object[] login(String email, String password);
	
	public Object[] signup(User user);
	
	public void delete(String email);
	
	public String refresh(String email);

	public User updateDetails(User updatedUser);

	public String updatePassword(User updatedUser);

	public void requestPasswordReset(String email);

	public void executePasswordReset(int tokenId, String resetToken, User updatedUser);
}
