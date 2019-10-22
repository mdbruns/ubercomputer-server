package com.ubercomputer.ubercomputerserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubercomputer.ubercomputerserver.entities.Part;
import com.ubercomputer.ubercomputerserver.services.PartService;

@RestController
@RequestMapping("/api")
public class PartRESTController {
	private PartService partService;
	
	@Autowired
    public PartRESTController(PartService partService) {
        this.partService = partService;
    }
	
	@GetMapping("/parts")
	@CrossOrigin
	public List<Part> findAll() {
		return partService.findAll();
	}
}