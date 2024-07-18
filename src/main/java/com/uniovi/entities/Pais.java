package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pais {
	@Id
	private String codPais;
	private String nombre;

	public Pais() {

	}

	public Pais(String nombre, String codPais) {
		super();
		this.nombre = nombre;
		this.codPais = codPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

}
