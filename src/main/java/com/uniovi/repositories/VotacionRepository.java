package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Votacion;

public interface VotacionRepository extends CrudRepository<Votacion, Long> {

	List<Votacion> findAll();

	@Query("SELECT v FROM Votacion v WHERE v.forAll = ?1 ORDER BY v.id ASC ")
	List<Votacion> getVotaciones(boolean publica);

}
