package com.samuelreginaldo.gestionvideojocs.backend.presentation.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuelreginaldo.gestionvideojocs.backend.integration.repositores.VideojocRepository;

@RestController
public class BorrameController {

	@Autowired
	private VideojocRepository videojocRepository;
	
	@GetMapping("/dto1")
	public Object getVideojoc1DTO() {
		return videojocRepository.getAllVideojoc1DTO();
	}
}
