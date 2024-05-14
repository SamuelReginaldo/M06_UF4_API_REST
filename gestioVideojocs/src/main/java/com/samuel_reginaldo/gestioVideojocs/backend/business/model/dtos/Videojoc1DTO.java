package com.samuel_reginaldo.gestioVideojocs.backend.business.model.dtos;

import java.io.Serializable;

public class Videojoc1DTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private double precio;
	
	public Videojoc1DTO(String name, double precio) {
		this.nom = name;
		this.precio = precio;
	}

	public String getName() {
		return nom;
	}

	public double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "Producto1DTO [name=" + nom + ", precio=" + precio + "]";
	}
	

}
