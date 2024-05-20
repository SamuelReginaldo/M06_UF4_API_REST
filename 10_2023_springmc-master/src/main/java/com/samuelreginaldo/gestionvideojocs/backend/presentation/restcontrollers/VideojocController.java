package com.samuelreginaldo.gestionvideojocs.backend.presentation.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.samuelreginaldo.gestionvideojocs.backend.business.model.Videojoc;
import com.samuelreginaldo.gestionvideojocs.backend.business.services.VideojocServices;
import com.samuelreginaldo.gestionvideojocs.backend.presentation.config.PresentationException;

@RestController
@RequestMapping("/productos")
public class VideojocController {

	@Autowired
	private VideojocServices productoServices;
		
	@GetMapping
	public List<Videojoc> getAll(@RequestParam(name = "min", required = false) Double min, 
								 @RequestParam(name = "max", required = false) Double max){
		
		List<Videojoc> productos = null;
		
		if(min != null && max != null) {
			productos = productoServices.getBetweenPriceRange(min, max);
		} else {
			productos = productoServices.getAll();
		}
			
		return productos;
	}
		
	@GetMapping("/{id}")
	public Videojoc read(@PathVariable Long id) {
		
		Optional<Videojoc> optional = productoServices.read(id);
		
		if(!optional.isPresent()) {
			throw new PresentationException("No se encuentra el producto con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Videojoc producto, UriComponentsBuilder ucb) {
		
		Long codigo = null;
		
		try {
			codigo = productoServices.create(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		URI uri = ucb.path("/productos/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
		
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		try {
			productoServices.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("No se encuentra el producto con id [" + id + "]. No se ha podido eliminar.", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Videojoc producto) {
		
		try {
			productoServices.update(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}

}
