package com.samuelreginaldo.gestionvideojocs.backend.integration.repositories;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.samuelreginaldo.gestionvideojocs.backend.business.model.Videojoc;
import com.samuelreginaldo.gestionvideojocs.backend.business.model.dtos.Videojoc1DTO;
import com.samuelreginaldo.gestionvideojocs.backend.integration.repositores.VideojocRepository;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_test.sql","/data/h2/data_test.sql"})
public class VideojocRepositoryTest {

	@Autowired
	private VideojocRepository videojocRepository;
	
	private Videojoc videojoc1;
	private Videojoc videojoc2;
	private Videojoc videojoc3;
	private Videojoc videojoc4;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void obtenemos_videojocs_entre_rango_de_precios_en_orden_ascendente() {
		
		List<Videojoc> videojocs = videojocRepository.findByPrecioBetweenOrderByPrecioAsc(20.0, 500.0);
		
		assertEquals(2, videojocs.size());
			
		assertTrue(videojoc1.equals(videojocs.get(0)));
		assertTrue(videojoc2.equals(videojocs.get(1)));
		
	}
	
	@Test
	void obtenemos_todos_los_Videojoc1DTO() {
		
		List<Videojoc1DTO> videojocs1DTO = videojocRepository.getAllVideojoc1DTO();
		
		for(Videojoc1DTO videojoc1DTO: videojocs1DTO) {
			System.err.println(videojoc1DTO);
		}
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		videojoc1 = new Videojoc();
		videojoc2 = new Videojoc();
		videojoc3 = new Videojoc();
		videojoc4 = new Videojoc();
		
		videojoc1.setId(100L);
		videojoc2.setId(101L);
		videojoc3.setId(102L);
		videojoc4.setId(103L);
		
	}
	
}
