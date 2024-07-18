package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import java.util.List;


public interface VotoRepository extends CrudRepository<Voto,Long>{

	@Query("SELECT v FROM Voto v WHERE v.user = ?1 and v.votacion = ?2")
	Voto findVoto(User user, Votacion votacion);
	
	@Query("SELECT v FROM Voto v WHERE v.votacion = ?1")
	List<Voto> getVotoByVotavion(Votacion votacion);

	@Query("SELECT v FROM Voto v WHERE v.user = ?1")
	List<Voto> findVotoByUser(User user);

}
