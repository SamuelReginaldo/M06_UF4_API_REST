package com.samuel_reginaldo.gestionvideojocs.backend.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelreginaldo.gestionvideojocs.backend.business.model.Videojoc;
import com.samuelreginaldo.gestionvideojocs.backend.business.services.VideojocServices;
import com.samuelreginaldo.gestionvideojocs.backend.presentation.config.RespuestaError;
import com.samuelreginaldo.gestionvideojocs.backend.presentation.restcontrollers.VideojocController;

@WebMvcTest(controllers=VideojocController.class)
public class VideojocControllerTest {
	
	@Autowired
	private MockMvc miniPostman;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private VideojocServices productoServices;
	
	private Videojoc producto1;
	private Videojoc producto2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void pedimos_todos_los_productos() throws Exception {
		
		// Arrange
		
		List<Videojoc> productos = Arrays.asList(producto1, producto2);
		when(productoServices.getAll()).thenReturn(productos);
		
		// Act
		
		MvcResult respuesta = miniPostman.perform(get("/productos").contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		// Assert
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void pedimos_todos_los_productos_entre_rango_precios() throws Exception {
				
		List<Videojoc> productos = Arrays.asList(producto1, producto2);
		when(productoServices.getBetweenPriceRange(10, 500)).thenReturn(productos);
			
		MvcResult respuesta = miniPostman.perform(get("/productos").param("min", "10")
																   .param("max","500")
																   .contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void obtenemos_producto_a_partir_de_su_id() throws Exception{
		
		when(productoServices.read(100L)).thenReturn(Optional.of(producto1));
		
		MvcResult respuesta = miniPostman.perform(get("/productos/100").contentType("application/json"))
									.andExpect(status().isOk())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(producto1);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	
	}
	
	@Test
	void solicitamos_producto_a_partir_de_un_id_inexistente() throws Exception {
		
		when(productoServices.read(100L)).thenReturn(Optional.empty());
		
		MvcResult respuesta = miniPostman.perform(get("/productos/100").contentType("application/json"))
									.andExpect(status().isNotFound())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el producto con id 100"));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void crea_producto_ok() throws Exception {
		
		producto1.setId(null);
		
		when(productoServices.create(producto1)).thenReturn(1033L);
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		miniPostman.perform(post("/productos").content(requestBody).contentType("application/json"))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location","http://localhost/productos/1033"));
	}
	
	@Test
	void crear_producto_con_id_NO_NULL() throws Exception{
		
		when(productoServices.create(producto1)).thenThrow(new IllegalStateException("Problema con el id..."));
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		MvcResult respuesta = miniPostman.perform(post("/productos").content(requestBody).contentType("application/json"))
						.andExpect(status().isBadRequest())
						.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("Problema con el id..."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void eliminamos_producto_ok() throws Exception{
		
		miniPostman.perform(delete("/productos/789")).andExpect(status().isNoContent());
		
		verify(productoServices, times(1)).delete(789L);
	}
	
	@Test
	void eliminamos_producto_no_existente() throws Exception{
		
		Mockito.doThrow(new IllegalStateException("xxxx")).when(productoServices).delete(789L);
		
		MvcResult respuesta = miniPostman.perform(delete("/productos/789"))
								.andExpect(status().isNotFound())
								.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el producto con id [789]. No se ha podido eliminar."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		producto1 = new Videojoc();
		producto1.setId(100L);
		producto1.setNombre("Final Fantasy VII");
		producto1.setGenero("JRPG");
		producto1.setPrecio(20.0);
		
		producto2 = new Videojoc();
		producto2.setId(101L);
		producto2.setNombre("Tekken 8");
		producto2.setGenero("3D Fighter");
		producto2.setPrecio(60.0);
		
	}
	
}
