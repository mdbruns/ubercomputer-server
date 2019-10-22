package com.ubercomputer.ubercomputerserver.daos;

import java.util.List;

import com.ubercomputer.ubercomputerserver.entities.Order;

public interface OrderDAO {
	
	public List<Order> findByUserId(int id);
	
	public void create(Order newOrder);

	public void delete(int orderId, int userId);
}