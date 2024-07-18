package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.Votacion;
import com.uniovi.repositories.OpcionesVotacionRepository;

@Service
public class OpcionesVotacionService {

	@Autowired
	private OpcionesVotacionRepository opcionesRepository;

	public void addOpcion(OpcionesVotacion opcionesVotacion) {

		opcionesRepository.save(opcionesVotacion);
		
	}

	public OpcionesVotacion findOpcion(Long idOpcion) {
		
		return opcionesRepository.findOne(idOpcion);
		
	}

	public void delete(OpcionesVotacion opcion) {


		opcionesRepository.delete(opcion);

	}

	public List<OpcionesVotacion> findOpcionesVotacion(Votacion votacion) {
		return opcionesRepository.findOpcionesvotacion(votacion);
	}
	
}
