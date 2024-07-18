package com.uniovi.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.CascadeType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "votaciones")

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	private String nombre;
	private String apellidos;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private Date fechaNacimiento;
	@Column(unique = true)
	private String numDocumento;
	private String role;

	//@Column(unique = true)
	private String email;
	private String password;
	private String nacionalidad;

	private String tipoDocumento;

	@Transient
	private String passwordConfirm;

	private String calle;
	private String poblacion;
	private String provincia;
	private String pais;
	private String cp;
	
	private String genero;

	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	Set<Voto> voto;
	
	@Transient
	private int edad;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	 @JoinTable(name = "user_votacion",
//     joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//     inverseJoinColumns = @JoinColumn(name = "votacion_id", referencedColumnName = "id"))
//	private Set<Votacion> votaciones;

	public User() {

	}

	public User(long id, String nombre, String apellidos, Date fechaNacimiento, String numDocumento, String role,
			String email, String password, String nacionalidad, String tipoDocumento, String passwordConfirm,
			String calle, String poblacion, String provincia, String pais, String cp, Set<Voto> voto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.numDocumento = numDocumento;
		this.role = role;
		this.email = email;
		this.password = password;
		this.nacionalidad = nacionalidad;
		this.tipoDocumento = tipoDocumento;
		this.passwordConfirm = passwordConfirm;
		this.calle = calle;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.pais = pais;
		this.cp = cp;
		this.voto = voto;
//		this.votaciones = Stream.of(votaciones).collect(Collectors.toSet());
//      this.votaciones.forEach(x -> x.getVotantes().add(this));
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento.toUpperCase();
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String pais) {
		this.nacionalidad = pais;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public int getEdad(){
		 
	    LocalDate hoy = LocalDate.now();   
	    LocalDate nacimiento = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
	    Long edad = ChronoUnit.YEARS.between(nacimiento, hoy); 
	    
		return edad.intValue();
		
	}
	
	public void setFechaNacimientoString(String fecha) {
		
		try {
			this.fechaNacimiento=new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
	}
	
		
//	public Set<Votacion> getVotaciones() {
//		return votaciones;
//	}
//
//	public void setVotaciones(Set<Votacion> votaciones) {
//		this.votaciones = votaciones;
//	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento="
				+ fechaNacimiento + ", numDocumento=" + numDocumento + ", role=" + role + ", email=" + email
				+ ", password=" + password + ", nacionalidad=" + nacionalidad + ", tipoDocumento=" + tipoDocumento
				+ ", passwordConfirm=" + passwordConfirm + ", calle=" + calle + ", poblacion=" + poblacion
				+ ", provincia=" + provincia + ", pais=" + pais + ", cp=" + cp + ", genero=" + genero + "]";
	}

}
