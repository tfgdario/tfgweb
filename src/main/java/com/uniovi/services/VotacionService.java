package com.uniovi.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import com.uniovi.repositories.VotacionRepository;

@Service
public class VotacionService {

	@Autowired
	private VotacionRepository votacionRepository;
	@Autowired
	private VotoService votoService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private OpcionesVotacionService opcionesService;

	@PostConstruct
	public void init() {
	}

	public void addVotacion(Votacion votacion) {
		votacionRepository.save(votacion);
	}

	public List<Votacion> getVotaciones() {
		return votacionRepository.findAll();
	}

	public List<Votacion> getVotacionesDisponible(User user) {
		List<Votacion> votaciones = votacionRepository.getVotaciones(true);
		List<Votacion> votacionesPrivadas = votacionRepository.getVotaciones(false);
		for (Votacion votacion : votacionesPrivadas) {
			boolean permitido = votoService.votoPermitido(user, votacion);
			if (permitido) {
				votaciones.add(votacion);
			}
		}
		return votaciones;
	}

	public Votacion getVotacion(Long idVotacion) {
		return votacionRepository.findOne(idVotacion);
	}

	public void asignarVotantes(Votacion votacion, List<User> usuarios) {
		
		for (User user : usuarios) {
			System.out.println("asignarVotantes "+user);
System.out.println(votoService.votoPermitido(user, votacion));
			if (!votoService.votoPermitido(user, votacion)) {
				Voto voto = new Voto();

				voto.setUser(user);
				voto.setVotacion(votacion);

				votoService.addVoto(voto);
System.out.println("user Añadido");
				//emailService.sendNewVotacionEmail(user, votacion);

			}

		}

	}

	public void update(Votacion votacion) {
		votacionRepository.save(votacion);
	}

	public void delete(Votacion votacion) {

		List<Voto> votos = votoService.getVotos(votacion);
		List<OpcionesVotacion> opciones = opcionesService.findOpcionesVotacion(votacion);

		for (OpcionesVotacion opcionesVotacion : opciones) {
			opcionesService.delete(opcionesVotacion);
		}

		for (Voto voto : votos) {
			votoService.delete(voto);
		}

		votacionRepository.delete(votacion);
	}

	public void notificarNuevaVotacion(Votacion votacion, List<User> usuarios) {

		for (User user : usuarios) {

			emailService.sendNewVotacionEmail(user, votacion);

		}

	}

}
