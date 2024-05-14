package com.samuel_reginaldo.gestioVideojocs.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;
import com.samuel_reginaldo.gestioVideojocs.backend.business.model.dtos.Videojoc1DTO;

public interface VideojocRepository extends JpaRepository<Videojoc, Long>{

	List<Videojoc> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
	@Query("SELECT new com.samuel_reginaldo.gestioVideojocs.backend.business.model.dtos.Videojoc1DTO"
		   + "(CONCAT(p.nom, ' - ', "
		   + "        p.precio)"
		   + "FROM VIDEOJOCS p")
	List<Videojoc1DTO> getAllProducto1DTO();
	
	
}
