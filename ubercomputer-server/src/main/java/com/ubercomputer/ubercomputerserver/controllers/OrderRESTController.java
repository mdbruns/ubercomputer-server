package com.ubercomputer.ubercomputerserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ubercomputer.ubercomputerserver.entities.Order;
import com.ubercomputer.ubercomputerserver.json.View;
import com.ubercomputer.ubercomputerserver.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderRESTController {
	private OrderService orderService;
	
	@Autowired
    public OrderRESTController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@GetMapping("/orders/{userId}")
	@CrossOrigin
	@JsonView(View.OrdersByUserID.class)
	public List<Order> findByUserId(@PathVariable int userId) {
		return orderService.findByUserId(userId);
	}
	
	@PostMapping("/orders")
	@CrossOrigin
	public Order create(@RequestBody Order newOrder) {
		orderService.create(newOrder);
		return newOrder;
	}
	
	@DeleteMapping("/orders/{orderId}/{userId}")
	@CrossOrigin
	public String delete(@PathVariable int orderId, @PathVariable int userId) {
		return orderService.delete(orderId, userId);
	}
}