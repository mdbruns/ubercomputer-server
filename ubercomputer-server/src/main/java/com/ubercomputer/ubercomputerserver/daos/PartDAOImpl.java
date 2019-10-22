package com.ubercomputer.ubercomputerserver.daos;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ubercomputer.ubercomputerserver.entities.Part;

@Repository
public class PartDAOImpl implements PartDAO {
	private EntityManager entityManager;

    @Autowired
    public PartDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	@Transactional
	public List<Part> findAll() {
		return entityManager.unwrap(Session.class).createQuery("FROM Part part ORDER BY part.price", Part.class).getResultList();
	}
}