package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OpcionesVotacion {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Votacion votacion;
	private String opcion;

	@OneToMany(mappedBy = "opcion",cascade=CascadeType.DETACH) // , cascade = CascadeType.ALL)
	private Set<Voto> votos;

	public OpcionesVotacion() {
	}

	public OpcionesVotacion(long id, Votacion votacion, String opcion) {
		super();
		this.id = id;
		this.votacion = votacion;
		this.opcion = opcion;
	}

	public OpcionesVotacion(Votacion votacion, String opcion) {
		this.votacion = votacion;
		this.opcion = opcion;
	}

	public OpcionesVotacion(long id, Votacion votacion, String opcion, Set<Voto> votos) {
		super();
		this.id = id;
		this.votacion = votacion;
		this.opcion = opcion;
		this.votos = votos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Votacion getVotacion() {
		return votacion;
	}

	public void setVotacion(Votacion votacion) {
		this.votacion = votacion;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public Set<Voto> getVotos() {
		return votos;
	}

	public void setVotos(Set<Voto> votos) {
		this.votos = votos;
	}

	@Override
	public String toString() {
		return "OcionesVotacion [id=" + id + ", votacion=" + votacion + ", opcion=" + opcion + "]";
	}

}
