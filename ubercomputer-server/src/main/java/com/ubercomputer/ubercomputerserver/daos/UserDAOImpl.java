package com.ubercomputer.ubercomputerserver.daos;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ubercomputer.ubercomputerserver.entities.PasswordResetToken;
import com.ubercomputer.ubercomputerserver.entities.User;

@Repository
public class UserDAOImpl implements UserDAO {
	private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	@Transactional
	public User findByEmail(String email) {
		return entityManager.unwrap(Session.class).createQuery("FROM User user WHERE user.email = :email", User.class).setParameter("email", email).getSingleResult();
	}

	@Override
	@Transactional
	public boolean existsByEmail(String email) {
		if (entityManager.unwrap(Session.class).createQuery("FROM User user WHERE user.email = :email", User.class).setParameter("email", email).uniqueResult() == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	@Transactional
	public void create(User user) {
		entityManager.unwrap(Session.class).save(user);
	}

	@Override
	@Transactional
	public void deleteByEmail(String email) {
		entityManager.unwrap(Session.class).createQuery("DELETE User user WHERE user.email = :email", User.class).setParameter("email", email);
	}

	@Override
	@Transactional
	public User updateDetails(User updatedUser) {
		Session session = entityManager.unwrap(Session.class);
		User existingUser = session.get(User.class, updatedUser.getId());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());
		existingUser.setStreetAddress(updatedUser.getStreetAddress());
		existingUser.setCity(updatedUser.getCity());
		existingUser.setZipCode(updatedUser.getZipCode());
		existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
		session.saveOrUpdate(existingUser);
		return updatedUser;
	}

	@Override
	@Transactional
	public void updatePassword(User updatedUser) {
		entityManager.unwrap(Session.class).update(updatedUser);
	}

	@Override
	@Transactional
	public int savePasswordResetToken(PasswordResetToken newToken) {
		PasswordResetToken oldToken = entityManager.unwrap(Session.class).createQuery(
				"FROM PasswordResetToken prt WHERE prt.user.id = :userId", PasswordResetToken.class).setParameter("userId", newToken.getUser().getId()).uniqueResult();
		
		if (oldToken != null) {
			oldToken.setUser(newToken.getUser());
			oldToken.setExpiry(newToken.getExpiry());
			oldToken.setToken(newToken.getToken());
			entityManager.unwrap(Session.class).update(oldToken);
			return oldToken.getId();
		}
		else {
			entityManager.unwrap(Session.class).save(newToken);
			return newToken.getId();
		}
	}

	@Override
	@Transactional
	public PasswordResetToken findPasswordResetToken(int tokenId) {
		return entityManager.unwrap(Session.class).createQuery("FROM PasswordResetToken prt WHERE prt.id = :tokenId", PasswordResetToken.class).setParameter("tokenId", tokenId).getSingleResult();
	}

	@Override
	@Transactional
	public void deletePasswordResetToken(int tokenId) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(session.get(PasswordResetToken.class, tokenId));	
	}
}
