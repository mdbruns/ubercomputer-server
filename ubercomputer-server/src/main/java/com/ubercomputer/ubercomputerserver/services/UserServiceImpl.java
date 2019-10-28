package com.ubercomputer.ubercomputerserver.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ubercomputer.ubercomputerserver.daos.UserDAO;
import com.ubercomputer.ubercomputerserver.entities.PasswordResetToken;
import com.ubercomputer.ubercomputerserver.entities.User;
import com.ubercomputer.ubercomputerserver.exceptions.CustomException;
import com.ubercomputer.ubercomputerserver.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
 	private UserDAO userDAO;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	@Override
	public Object[] login(String email, String password) {
		try {
			User savedCredentials = userDAO.findByEmail(email);
			
			if (passwordEncoder.matches(password, savedCredentials.getPassword())) {
				Object[] responseData = new Object[2];
				responseData[0] = jwtTokenProvider.createToken(email, savedCredentials.getAuthorities());
				responseData[1] = savedCredentials;
		        return responseData;
		    } 
			else {
		        throw new CustomException("Invalid Password", HttpStatus.UNPROCESSABLE_ENTITY);
		    }
		}
		catch (EmptyResultDataAccessException edare) {
			throw new CustomException("Invalid Email", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@Override
	public Object[] signup(User user) {
	    if (!userDAO.existsByEmail(user.getEmail())) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userDAO.create(user);
	        Object[] responseData = new Object[2];
			responseData[0] = jwtTokenProvider.createToken(user.getEmail(), user.getAuthorities());
			responseData[1] = user;
	        return responseData;
	    } 
	    else {
	        throw new CustomException("Email Already In Use", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	}
	
	@Override
	public void delete(String email) {
	    userDAO.deleteByEmail(email);
	}
	
	@Override
	public String refresh(String email) {
	    return jwtTokenProvider.createToken(email, userDAO.findByEmail(email).getAuthorities());
	}

	@Override
	public User updateDetails(User updatedUser) {
		if (updatedUser.getEmail().equals(updatedUser.getOldEmail()) || !userDAO.existsByEmail(updatedUser.getEmail())) {
			return userDAO.updateDetails(updatedUser);
		}
		else {
			throw new CustomException("Email Already In Use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public String updatePassword(User updatedUser) {
		String newPassword = updatedUser.getPassword();
		updatedUser.setPassword(passwordEncoder.encode(newPassword));
		try {
			userDAO.updatePassword(updatedUser);
			Object[] results = login(updatedUser.getEmail(), newPassword);
			return results[0].toString();
		}
		catch (Exception e) {
			throw new CustomException("Something went wrong on our end while updating your password. Please try again in a minute or contact us to report the problem.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void requestPasswordReset(String email) {
		try {
			User requestingUser = userDAO.findByEmail(email);
		
			try {
				PasswordResetToken newToken = new PasswordResetToken();
				newToken.setUser(requestingUser);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				timestamp.setTime(timestamp.getTime() + (3600 * 1000));
				newToken.setExpiry(timestamp);
				String token = UUID.randomUUID().toString();
				newToken.setToken(passwordEncoder.encode(token));
				int tokenId = userDAO.savePasswordResetToken(newToken);
				
				try {
					Email from = new Email("matt.ubercomputer@gmail.com");
				    String subject = "Übercomputer -- Password Reset Link";
				    Email to = new Email(email);
				    Content content = new Content("text/html", "<html><body>To continue with the password reset "
				    		+ "process please click <a href=\"http://ubercomputer.herokuapp.com/reset/" + requestingUser.getId() + "/" + tokenId + "/" + token + "\">this link.</a></body></html>");
				    
				    Mail mail = new Mail(from, subject, to, content);
				    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
				    Request request = new Request();
				    
				    try {
				      request.setMethod(Method.POST);
				      request.setEndpoint("mail/send");
				      request.setBody(mail.build());
				      Response response = sg.api(request);
				      System.out.println(response.getStatusCode());
				      System.out.println(response.getBody());
				      System.out.println(response.getHeaders());
				    } 
				    catch (IOException ex) {
				    	throw ex;		    
				    }
				}
				catch (Exception e) {
					throw new CustomException("Something went wrong on our end while sending the password reset link to your email. Please try again in a minute or contact us to report the problem.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				throw new CustomException("Something went wrong on our end while generating your password reset token. Please try again in a minute or contact us to report the problem.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (EmptyResultDataAccessException edare) {
			throw new CustomException("Invalid Email", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public void executePasswordReset(int tokenId, String resetToken, User updatedUser) {
		try {
			PasswordResetToken passwordResetToken = userDAO.findPasswordResetToken(tokenId);
			User oldUser = passwordResetToken.getUser();
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			
			if (!passwordEncoder.matches(resetToken, passwordResetToken.getToken()) || oldUser.getId() != updatedUser.getId()) {
				throw new CustomException("Invalid Password Reset Token", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			else if (currentTime.after(passwordResetToken.getExpiry())) {
				throw new CustomException("Password Reset Token Expired", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			else {
				boolean passwordResetSuccess = false;
				
				try {
					oldUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
					userDAO.updatePassword(oldUser);
					passwordResetSuccess = true;
				}
				catch (Exception e) {
					throw new CustomException("Something went wrong on our end while updating your password. Please try again in a minute or contact us to report the problem.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				finally {
					if (passwordResetSuccess) {
						userDAO.deletePasswordResetToken(tokenId);
					}
				}
			}
		}
		catch (EmptyResultDataAccessException edare) {
			throw new CustomException("Invalid Password Reset Token", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
}