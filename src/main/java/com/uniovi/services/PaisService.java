package com.uniovi.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Pais;
import com.uniovi.repositories.PaisRepository;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;

	@PostConstruct
	public void init() {

	}

	public List<Pais> getAll() {

		return paisRepository.findAll();

	}

}
