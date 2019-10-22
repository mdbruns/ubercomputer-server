package com.ubercomputer.ubercomputerserver.daos;

import java.util.List;

import com.ubercomputer.ubercomputerserver.entities.Part;

public interface PartDAO {
	public List<Part> findAll();
}