package com.samuel_reginaldo.gestioVideojocs.backend.business.services;

import java.util.List;
import java.util.Optional;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;

public interface VideojocServices {

	/**
	 * Devuelve el código que ha otorgado el sistema
	 * 
	 * Lanza una IllegalStateException si el código del producto no es null
	 * 
	 */
	Long create(Videojoc producto);	    // C
	
	Optional<Videojoc> read(Long id);   // R
	
	/**
	 * 
	 * Lanza una IllegalStateException si el código del producto es null o no existe en el sistema
	 * 
	 */
	void update(Videojoc producto);		// U
	
	/**
	 * Lanza una IllegalStateException si no existe la id en el sistema
	 * 
	 */
	void delete(Long id);				// D
	
	List<Videojoc> getAll();
	List<Videojoc> getBetweenPriceRange(double min, double max);
	
}
