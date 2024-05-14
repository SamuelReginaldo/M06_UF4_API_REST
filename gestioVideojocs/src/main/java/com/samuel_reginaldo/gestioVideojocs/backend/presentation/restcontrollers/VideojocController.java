package com.samuel_reginaldo.gestioVideojocs.backend.presentation.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.samuel_reginaldo.gestioVideojocs.backend.business.model.Videojoc;
import com.samuel_reginaldo.gestioVideojocs.backend.business.model.dtos.Videojoc1DTO;
import com.samuel_reginaldo.gestioVideojocs.backend.business.services.*;
import com.samuel_reginaldo.gestioVideojocs.backend.presentation.config.RespostaError;

@RestController
@RequestMapping("/videojocs")
public class VideojocController {

	@Autowired
	private VideojocServices videojocServices;
	
	@GetMapping("/videojocs")
	public List<Videojoc> getAll(){
		return videojocServices.getAll();
	}

	@GetMapping("/videojocs/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		
		if(id > 500) {
			throw new RuntimeException("El número " + id + " no es válido.");
		}
		
		Optional<Videojoc> optional = videojocServices.read(id);
		
		if (optional.isEmpty()) {
			RespostaError respuestaError = new RespostaError("No se encuentra el producto con id " + id);
			return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
	
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PostMapping("/videojocs")
	public ResponseEntity<?> create(@RequestBody Videojoc producto, UriComponentsBuilder ucb) {
		
		Long codigo = null;
		
		try {
			codigo = videojocServices.create(producto);
		} catch(IllegalStateException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		URI uri = ucb.path("/videojocs/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/videojocs/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		try {
			videojocServices.delete(id);
		} catch(IllegalStateException e) {
			throw new RuntimeException("No se encuentra el videojoc con id [" + id + "]. No se ha podido eliminar.");
		}
		
	}
	
	@PutMapping("/videojocs")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Videojoc videojoc) {
		
		try {
			videojocServices.update(videojoc);
		} catch(IllegalStateException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	
	// ****************************************************
	//
	// Gestión de excepciones
	//
	// ****************************************************
	
	@ExceptionHandler({IllegalArgumentException.class, ClassCastException.class})
	public ResponseEntity<?> gestor1(Exception e){
		return ResponseEntity.badRequest().body(new RespostaError(e.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> gestor2(Exception e){
		return ResponseEntity.badRequest().body(new RespostaError(e.getMessage()));
	}
	
}

