package com.samuelreginaldo.gestionvideojocs.backend.business.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuelreginaldo.gestionvideojocs.backend.business.model.Videojoc;
import com.samuelreginaldo.gestionvideojocs.backend.business.services.VideojocServices;
import com.samuelreginaldo.gestionvideojocs.backend.integration.repositores.VideojocRepository;

@Service
public class VideojocServicesImpl implements VideojocServices{

	@Autowired
	private VideojocRepository videojocRepository;
	
	@Override
	@Transactional
	public Long create(Videojoc videojoc) {
		
		if(videojoc.getId() != null) {
			throw new IllegalStateException("No se puede crear un videojuego con código not null");
		}
		
		Long id = System.currentTimeMillis();
		videojoc.setId(id);
		
		videojocRepository.save(videojoc);
		
		return id;
	}

	@Override
	public Optional<Videojoc> read(Long id) {	
		return videojocRepository.findById(id);
	}

	@Override
	@Transactional
	public void update(Videojoc videojoc) {
		
		Long id = videojoc.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un videojuego con código not null");
		}
		
		boolean existe = videojocRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El videojuego con código " + id + " no existe. No se puede actualizar.");
		}
		
		videojocRepository.save(videojoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		videojocRepository.deleteById(id);
	}

	@Override
	public List<Videojoc> getAll() {
		return videojocRepository.findAll();
	}

	@Override
	public List<Videojoc> getBetweenPriceRange(double min, double max) {
		return videojocRepository.findByPrecioBetweenOrderByPrecioAsc(min, max);
	}

}
