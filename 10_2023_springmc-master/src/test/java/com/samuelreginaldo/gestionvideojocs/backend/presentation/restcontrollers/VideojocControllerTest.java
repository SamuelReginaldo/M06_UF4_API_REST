package com.samuelreginaldo.gestionvideojocs.backend.presentation.restcontrollers;

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
import org.springframework.boot.test.context.SpringBootTest;
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
	private VideojocServices videojocServices;
	
	private Videojoc videojoc1;
	private Videojoc videojoc2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void pedimos_todos_los_videojocs() throws Exception {
		
		// Arrange
		
		List<Videojoc> videojocs = Arrays.asList(videojoc1, videojoc2);
		when(videojocServices.getAll()).thenReturn(videojocs);
		
		// Act
		
		MvcResult respuesta = miniPostman.perform(get("/videojocs").contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(videojocs);
		
		// Assert
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void pedimos_todos_los_videojocs_entre_rango_precios() throws Exception {
				
		List<Videojoc> videojocs = Arrays.asList(videojoc1, videojoc2);
		when(videojocServices.getBetweenPriceRange(10, 500)).thenReturn(videojocs);
			
		MvcResult respuesta = miniPostman.perform(get("/videojocs").param("min", "10")
																   .param("max","500")
																   .contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(videojocs);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void obtenemos_videojoc_a_partir_de_su_id() throws Exception{
		
		when(videojocServices.read(100L)).thenReturn(Optional.of(videojoc1));
		
		MvcResult respuesta = miniPostman.perform(get("/videojocs/100").contentType("application/json"))
									.andExpect(status().isOk())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(videojoc1);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	
	}
	
	@Test
	void solicitamos_videojoc_a_partir_de_un_id_inexistente() throws Exception {
		
		when(videojocServices.read(100L)).thenReturn(Optional.empty());
		
		MvcResult respuesta = miniPostman.perform(get("/videojocs/100").contentType("application/json"))
									.andExpect(status().isNotFound())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el videojoc con id 100"));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void crea_videojoc_ok() throws Exception {
		
		videojoc1.setId(null);
		
		when(videojocServices.create(videojoc1)).thenReturn(1033L);
		
		String requestBody = objectMapper.writeValueAsString(videojoc1);
		
		miniPostman.perform(post("/videojocs").content(requestBody).contentType("application/json"))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location","http://localhost/videojocs/1033"));
	}
	
	@Test
	void crear_videojoc_con_id_NO_NULL() throws Exception{
		
		when(videojocServices.create(videojoc1)).thenThrow(new IllegalStateException("Problema con el id..."));
		
		String requestBody = objectMapper.writeValueAsString(videojoc1);
		
		MvcResult respuesta = miniPostman.perform(post("/videojocs").content(requestBody).contentType("application/json"))
						.andExpect(status().isBadRequest())
						.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("Problema con el id..."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void eliminamos_videojoc_ok() throws Exception{
		
		miniPostman.perform(delete("/videojocs/789")).andExpect(status().isNoContent());
		
		verify(videojocServices, times(1)).delete(789L);
	}
	
	@Test
	void eliminamos_videojoc_no_existente() throws Exception{
		
		Mockito.doThrow(new IllegalStateException("xxxx")).when(videojocServices).delete(789L);
		
		MvcResult respuesta = miniPostman.perform(delete("/videojocs/789"))
								.andExpect(status().isNotFound())
								.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el videojoc con id [789]. No se ha podido eliminar."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		videojoc1 = new Videojoc();
		videojoc1.setId(100L);
		videojoc1.setNombre("Final Fantasy VII");
		videojoc1.setGenero("JRPG");
		videojoc1.setPrecio(20.0);
		
		videojoc2 = new Videojoc();
		videojoc2.setId(101L);
		videojoc2.setNombre("Tekken 8");
		videojoc2.setGenero("3D Fighter");
		videojoc2.setPrecio(60.0);
		
	}
	
}
