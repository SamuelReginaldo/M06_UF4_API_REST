package com.samuelreginaldo.gestionvideojocs.backend.integration.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.samuelreginaldo.gestionvideojocs.backend.business.model.Videojoc;
import com.samuelreginaldo.gestionvideojocs.backend.business.model.dtos.Videojoc1DTO;

public interface VideojocRepository extends JpaRepository<Videojoc, Long>{

	List<Videojoc> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
	
	@Query("SELECT new com.samuelreginaldo.gestionvideojocs.backend.business.model.dtos.Videojoc1DTO"
		   + "(CONCAT(p.nombre, ' - ', "
		   + "        p.genero), "
		   + "        p.precio) "
		   + "FROM Videojoc p")
	List<Videojoc1DTO> getAllVideojoc1DTO();
	
	
}
