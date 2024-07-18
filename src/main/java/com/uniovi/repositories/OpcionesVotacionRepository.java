package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.Votacion;

public interface OpcionesVotacionRepository extends CrudRepository<OpcionesVotacion, Long>{

	@Query("SELECT op FROM OpcionesVotacion op WHERE op.votacion = ?1")
	List<OpcionesVotacion> findOpcionesvotacion(Votacion votacion);
	
}
