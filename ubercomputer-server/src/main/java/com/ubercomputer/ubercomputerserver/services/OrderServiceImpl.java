package com.ubercomputer.ubercomputerserver.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubercomputer.ubercomputerserver.daos.OrderDAO;
import com.ubercomputer.ubercomputerserver.entities.Order;
import com.ubercomputer.ubercomputerserver.exceptions.CustomException;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired 
	private OrderDAO orderDAO;

	@Override
	public List<Order> findByUserId(int userId) {
		return orderDAO.findByUserId(userId);
	}

	@Override
	public void create(Order newOrder) {
		orderDAO.create(newOrder);
		
		try {
			Email from = new Email("matt.ubercomputer@gmail.com");
		    String subject = "New Order #" + newOrder.getId();
		    Email to = new Email("matt.ubercomputer@gmail.com");
		    Content content = new Content("text/plain", newOrder.toString());
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
		    	System.out.println(ex);		    
		    }
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public String delete(int orderId, int userId) {
		try {
			orderDAO.delete(orderId, userId);
		}
		catch (CustomException ce) {
			return ce.getMessage();
		}
		
		try {
			Email from = new Email("matt.ubercomputer@gmail.com");
		    String subject = "Order #" + orderId + " Cancelled";
		    Email to = new Email("matt.ubercomputer@gmail.com");
		    Content content = new Content("text/plain", "Order cancelled by user at " + new Timestamp(System.currentTimeMillis()));
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
		    	System.out.println(ex);		    
		    }
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return "Order #" + orderId + "deleted successfully";
	}
}