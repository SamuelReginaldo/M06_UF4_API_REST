package com.samuel_reginaldo.gestionvideojocs.backend.integration.repositories;

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
	private VideojocRepository productoRepository;
	
	private Videojoc producto1;
	private Videojoc producto2;
	private Videojoc producto3;
	private Videojoc producto4;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void obtenemos_productos_entre_rango_de_precios_en_orden_ascendente() {
		
		List<Videojoc> productos = productoRepository.findByPrecioBetweenOrderByPrecioAsc(20.0, 500.0);
		
		assertEquals(2, productos.size());
			
		assertTrue(producto4.equals(productos.get(0)));
		assertTrue(producto1.equals(productos.get(1)));
		
	}
	
	@Test
	void obtenemos_todos_los_Producto1DTO() {
		
		List<Videojoc1DTO> productos1DTO = productoRepository.getAllProducto1DTO();
		
		for(Videojoc1DTO producto1DTO: productos1DTO) {
			System.err.println(producto1DTO);
		}
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		producto1 = new Videojoc();
		producto2 = new Videojoc();
		producto3 = new Videojoc();
		producto4 = new Videojoc();
		
		producto1.setId(100L);
		producto2.setId(101L);
		producto3.setId(102L);
		producto4.setId(103L);
		
	}
	
}
