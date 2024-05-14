package com.samuel_reginaldo.gestioVideojocs.backend.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="VIDEOJOCS")
public class Videojoc implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private String nom;
	private String genero;
	private Double precio;
	
	public Videojoc() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genero, id, nom, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videojoc other = (Videojoc) obj;
		return Objects.equals(genero, other.genero) && Objects.equals(id, other.id) && Objects.equals(nom, other.nom)
				&& Objects.equals(precio, other.precio);
	}

	@Override
	public String toString() {
		return "Videojoc [id=" + id + ", nom=" + nom + ", genero=" + genero + ", precio=" + precio + "]";
	}

	
	
}
