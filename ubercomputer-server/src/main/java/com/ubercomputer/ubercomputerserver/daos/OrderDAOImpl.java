package com.ubercomputer.ubercomputerserver.daos;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ubercomputer.ubercomputerserver.entities.Order;
import com.ubercomputer.ubercomputerserver.exceptions.CustomException;

@Repository
public class OrderDAOImpl implements OrderDAO {
	private EntityManager entityManager;

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	@Transactional
	public List<Order> findByUserId(int userId) {
		return entityManager.unwrap(Session.class).createQuery("FROM Order order WHERE order.user.id = :userId ORDER BY order.dateTimeSubmitted", Order.class).setParameter("userId", userId).getResultList();
	}

	@Override
	@Transactional
	public void create(Order newOrder) {
		entityManager.unwrap(Session.class).save(newOrder);	
	}
	
	@Override
	@Transactional
	public void delete(int orderId, int userId) {
		Session session = entityManager.unwrap(Session.class);
		Order targetOrder = session.get(Order.class, orderId);
		if (targetOrder.getUser().getId() == userId) {
			session.delete(targetOrder);
		}
		else {
			throw new CustomException("Request to delete order made with invalid user id.", HttpStatus.UNAUTHORIZED);
		}
	}
}