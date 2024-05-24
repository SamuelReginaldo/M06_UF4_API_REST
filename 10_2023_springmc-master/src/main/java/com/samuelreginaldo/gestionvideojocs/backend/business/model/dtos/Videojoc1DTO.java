package com.samuelreginaldo.gestionvideojocs.backend.business.model.dtos;

import java.io.Serializable;

public class Videojoc1DTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double precio;
	
	public Videojoc1DTO(String name, double precio) {
		this.name = name;
		this.precio = precio;
	}

	public String getName() {
		return name;
	}

	public double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "Videojoc1DTO [name=" + name + ", precio=" + precio + "]";
	}
	

}
