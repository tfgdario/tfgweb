package com.uniovi.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Null;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Votacion {

	@Id
	@GeneratedValue
	private long id;

	private String nombre;
	private String consulta;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private Date  fechaInicio;
	@DateTimeFormat (pattern="yyyy-MM-dd")
    private Date  fechaFin;
	//private String opciones;

	private boolean forAll;
	
//	@Transient
//	private File file;
	
	@OneToMany(mappedBy="votacion")
	private Set<Voto> voto;

	@Null
	@Column(nullable=true)
	@OneToMany(mappedBy="votacion")//, cascade = CascadeType.ALL)
	private Set<OpcionesVotacion> opciones;
	
	@Transient
	private String opcionesTemporal;
	
	@Transient
	private List<String> opcionesTemporal2;
	
	@Transient
	private int filtroEdad;
	
	@Transient
	private int filtroGenero;
	
	public Votacion() {

	}

	public Votacion(long id,  Date fechaInicio, Date fechaFin,boolean forAll, String nombre,String consulta, Set<OpcionesVotacion> opciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.opciones = opciones;
		this.forAll = forAll;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Set<OpcionesVotacion> getOpciones() {
		return opciones;
	}

	public void setOpciones(Set<OpcionesVotacion> opciones) {
		this.opciones = opciones;
	}

	public boolean isForAll() {
		return forAll;
	}

	public void setForAll(boolean forAll) {
		this.forAll = forAll;
	}

//	public Set<User> getVotantes() {
//		return votantes;
//	}
//
//	public void setVotantes(Set<User> votantes) {
//		this.votantes = votantes;
//	}

//	public File getFile() {
//		return file;
//	}
//
//	public void setFile(File file) {
//		this.file = file;
//	}

	public Set<Voto> getVoto() {
		return voto;
	}

	public void setVoto(Set<Voto> voto) {
		this.voto = voto;
	}

	public String getOpcionesTemporal() {
		return opcionesTemporal;
	}

	public void setOpcionesTemporal(String opcionesTemporal) {
		this.opcionesTemporal = opcionesTemporal;
	}
	
	public int getFiltroEdad() {
		return filtroEdad;
	}

	public void setFiltroEdad(int filtroEdad) {
		this.filtroEdad = filtroEdad;
	}

	public int getFiltroGenero() {
		return filtroGenero;
	}

	public void setFiltroGenero(int filtroGenero) {
		this.filtroGenero = filtroGenero;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public List<String> getOpcionesTemporal2() {
		return opcionesTemporal2;
	}

	public void setOpcionesTemporal2(List<String> opcionesTemporal2) {
		this.opcionesTemporal2 = opcionesTemporal2;
	}

	@Override
	public String toString() {
		return "Votacion [id=" + id + ", nombre=" + nombre + ", consulta=" + consulta + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", forAll=" + forAll + "]";
	}

}
