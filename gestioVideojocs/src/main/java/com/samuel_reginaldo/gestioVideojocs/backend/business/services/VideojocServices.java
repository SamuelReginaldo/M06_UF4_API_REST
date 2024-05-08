package com.samuel_reginaldo.gestioVideojocs.backend.business.services;

import java.util.List;
import java.util.Optional;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;


public interface VideojocServices {

	Long create(Videojoc videojoc);	    // C
	
	Optional<Videojoc> read(Long id);   // R
	
	/**
	 * 
	 * Lanza una IllegalStateException si el código del producto es null o no existe en el sistema
	 * 
	 */
	void update(Videojoc videojoc);		// U
	
	/**
	 * Lanza una IllegalStateException si no existe la id en el sistema
	 * 
	 */
	void delete(Long id);				// D
	
	List<Videojoc> getBetweenPriceRange(double min, double max);
	
	List<Videojoc> getAll();
	
}
