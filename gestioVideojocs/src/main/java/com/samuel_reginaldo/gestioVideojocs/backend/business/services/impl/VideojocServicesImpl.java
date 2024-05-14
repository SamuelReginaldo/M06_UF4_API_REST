package com.samuel_reginaldo.gestioVideojocs.backend.business.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;
import com.samuel_reginaldo.gestioVideojocs.backend.business.services.VideojocServices;
import com.samuel_reginaldo.gestioVideojocs.backend.integration.repositores.VideojocRepository;

@Service
public class VideojocServicesImpl implements VideojocServices{

	@Autowired
	private VideojocRepository productoRepository;
	
	@Override
	@Transactional
	public Long create(Videojoc producto) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = System.currentTimeMillis();
		producto.setId(id);
		
		productoRepository.save(producto);
		
		return id;
	}

	@Override
	public Optional<Videojoc> read(Long id) {	
		return productoRepository.findById(id);
	}

	@Override
	@Transactional
	public void update(Videojoc producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		productoRepository.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public List<Videojoc> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Videojoc> getBetweenPriceRange(double min, double max) {
		return productoRepository.findByPrecioBetweenOrderByPrecioAsc(min, max);
	}

}