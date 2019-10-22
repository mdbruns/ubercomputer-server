package com.ubercomputer.ubercomputerserver.services;

import java.sql.Timestamp;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ubercomputer.ubercomputerserver.daos.OrderDAO;
import com.ubercomputer.ubercomputerserver.entities.Order;
import com.ubercomputer.ubercomputerserver.exceptions.CustomException;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired 
	private OrderDAO orderDAO;
	
	@Autowired
	private JavaMailSender sender; 

	@Override
	public List<Order> findByUserId(int userId) {
		return orderDAO.findByUserId(userId);
	}

	@Override
	public void create(Order newOrder) {
		orderDAO.create(newOrder);
		
		try {
			MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setTo("matt.ubercomputer@gmail.com");
	        helper.setText(newOrder.toString());
	        helper.setSubject("New Order #" + newOrder.getId());
	         
	        sender.send(message);
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
			MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setTo("matt.ubercomputer@gmail.com");
	        helper.setText("Order cancelled by user at " + new Timestamp(System.currentTimeMillis()));
	        helper.setSubject("Order #" + orderId + " Cancelled");
	         
	        sender.send(message);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return "Order #" + orderId + "deleted successfully";
	}
}