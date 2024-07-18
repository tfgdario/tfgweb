package com.uniovi.entities;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Transient;

import com.uniovi.entities.keys.VotoKey;

@Entity
public class Voto {
	
	@EmbeddedId
	private VotoKey id;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("userId")
	private User user;

	@ManyToOne
	@MapsId("votacionId")
	private Votacion votacion;

	@ManyToOne
	private OpcionesVotacion opcion;

	@Transient
	private String opcionTemporal;
	
	public Voto() {
		setId(new VotoKey());
	}

	public Voto(User user, Votacion votacion, OpcionesVotacion opcion) {
		// VotoKey vk= new VotoKey();
		// vk.setUserId(user.getId());
		// vk.setVotacionId(votacion.getId());
		this.id = new VotoKey();
		this.user = user;
		this.votacion = votacion;
		this.opcion = opcion;
	}

	public Voto(VotoKey id, User user, Votacion votacion, OpcionesVotacion opcion) {
		super();
		this.id = id;
		this.user = user;
		this.votacion = votacion;
		this.opcion = opcion;
	}

	public VotoKey getId() {
		return id;
	}

	public void setId(VotoKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Votacion getVotacion() {
		return votacion;
	}

	public void setVotacion(Votacion votacion) {
		this.votacion = votacion;
	}

	public OpcionesVotacion getOpcion() {
		return opcion;
	}

	public void setOpcion(OpcionesVotacion opcion) {
		this.opcion = opcion;
	}

	public String getOpcionTemporal() {
		return opcionTemporal;
	}

	public void setOpcionTemporal(String opcionTemporal) {
		this.opcionTemporal = opcionTemporal;
	}

	@Override
	public String toString() {
		return "Voto [id=" + id + ", user=" + user + ", votacion=" + votacion + ", opcionId=" + opcion + "]";
	}

}
