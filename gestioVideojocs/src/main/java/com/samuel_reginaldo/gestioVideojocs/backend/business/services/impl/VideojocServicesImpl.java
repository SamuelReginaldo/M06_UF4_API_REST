package com.samuel_reginaldo.gestioVideojocs.backend.business.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;
import com.samuel_reginaldo.gestioVideojocs.backend.business.services.VideojocServices;


@Service
public class VideojocServicesImpl implements VideojocServices {

	private final TreeMap<Long, Videojoc> VIDEOJOCS = new TreeMap<>();
	
	public VideojocServicesImpl() {
		init();
	}
	
	@Override
	public Long create(Videojoc videojoc) {
		
		Long id = VIDEOJOCS.lastKey() + 1;
		
		videojoc.setId(id);
		
		VIDEOJOCS.put(videojoc.getId(), videojoc);
		
		return id;
	}

	@Override
	public Optional<Videojoc> read(Long id) {
		return Optional.ofNullable(VIDEOJOCS.get(id));
	}
	
	

	@Override
	public List<Videojoc> getAll() {
		return new ArrayList<>(VIDEOJOCS.values());
	}
	
	// ***************************************************************
	//
	// Private Methods
	//
	// ***************************************************************

	private void init() {
		
		Videojoc p1 = new Videojoc();
		Videojoc p2 = new Videojoc();
		Videojoc p3 = new Videojoc();
		
		p1.setId(10L);
		p1.setNom("Final Fantasy XVI");
		p1.setGenero("Action RPG");
		p1.setPrecio(69.99);
		
		p2.setId(11L);
		p2.setNom("Tekken 8");
		p2.setGenero("3D Fighter");
		p2.setPrecio(69.99);
		
		p3.setId(11L);
		p3.setNom("Elden Ring");
		p3.setGenero("Souls like");
		p3.setPrecio(40.00);
		
		VIDEOJOCS.put(p1.getId(), p1);
		VIDEOJOCS.put(p2.getId(), p2);
		VIDEOJOCS.put(p3.getId(), p3);
		
	}


	public void update(Videojoc videojoc) {
		// TODO Auto-generated method stub
		
	}

	
	public void delete(Long id) {
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}		

		if(!VIDEOJOCS.containsKey(id)) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		VIDEOJOCS.remove(id);
		
	}


	public List<Videojoc> getBetweenPriceRange(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}
}