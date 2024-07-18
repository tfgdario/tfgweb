package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Pais;

public interface PaisRepository extends CrudRepository<Pais, Long>{

	List<Pais> findAll();
	
}
