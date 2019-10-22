package com.ubercomputer.ubercomputerserver.services;

import java.util.List;

import com.ubercomputer.ubercomputerserver.entities.Order;

public interface OrderService {

	List<Order> findByUserId(int userId);

	void create(Order newOrder);

	String delete(int orderId, int userId);
}
