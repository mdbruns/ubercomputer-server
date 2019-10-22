package com.ubercomputer.ubercomputerserver.daos;

import com.ubercomputer.ubercomputerserver.entities.PasswordResetToken;
import com.ubercomputer.ubercomputerserver.entities.User;

public interface UserDAO {
	
	public User findByEmail(String email);
	
	public boolean existsByEmail(String email);

	public void create(User user);

	public void deleteByEmail(String email);

	public User updateDetails(User updatedUser);

	public void updatePassword(User updatedUser);

	public int savePasswordResetToken(PasswordResetToken passwordResetToken);
	
	public PasswordResetToken findPasswordResetToken(int tokenId);
	
	public void deletePasswordResetToken(int tokenId);
}