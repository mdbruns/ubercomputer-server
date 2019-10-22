package com.ubercomputer.ubercomputerserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubercomputer.ubercomputerserver.daos.PartDAO;
import com.ubercomputer.ubercomputerserver.entities.Part;

@Service
public class PartServiceImpl implements PartService {
	private PartDAO partDAO;
	
	@Autowired
	public PartServiceImpl(PartDAO partDAO) {
		this.partDAO = partDAO;
	}

	@Override
	public List<Part> findAll() {
		List<Part> parts = partDAO.findAll();
		
		for (Part part : parts) {
			if (!part.getType().equals("operating system")) {
				part.setPrice((int) Math.round(part.getPrice() * 1.05));
			}
		}
		
		return parts;
	}
}