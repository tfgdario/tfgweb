package com.uniovi.services;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import com.uniovi.repositories.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@PostConstruct
	public void init() {
	}

	public void addVoto(Voto voto) {

		votoRepository.save(voto);

	}

	public boolean votoPermitido(User user, Votacion votacion) {
System.out.println("Permitido?"+votoRepository.findVoto(user, votacion));
		Voto voto = votoRepository.findVoto(user, votacion);
		// System.out.println(voto);
System.out.println("permitido"+voto);
		return voto != null ? true : false;

	}

	public Voto findVotoByuserAndVotacion(User user, Votacion votacion) {

		return votoRepository.findVoto(user, votacion);

	}

	public void votar(OpcionesVotacion opcion, User user, Votacion votacion) throws Exception {

		// Votacion votacion = opcion.getVotacion();

		Date fechaActual = new Date();

		// System.out.println("Votacion Antes de Voto "+votacion.toString());

		if (fechaActual.before(votacion.getFechaFin())) {

			// System.out.println("Cond 1 " + (votacion.isForAll()==false));
			// System.out.println("Cond 2 "+votoPermitido(user, votacion));

			if (!votacion.isForAll() && votoPermitido(user, votacion)) {

				Voto voto = votoRepository.findVoto(user, votacion);

				if (voto.getOpcion() == null) {

					voto.setOpcion(opcion);
					votoRepository.save(voto);

				} else {

					throw new Exception(
							"El usuario ya ha emitido su voto para esta votacion con anterioriad, no puede volver a votar");

				}

			} else if (votacion.isForAll()) {

				// System.out.println("} else if (votacion.isForAll()) {");

				Voto voto = votoRepository.findVoto(user, votacion);

				if (voto == null) {

					votoRepository.save(new Voto(user, votacion, opcion));

				} else {

					throw new Exception(
							"El usuario ya ha emitido su voto para esta votacion con anterioriad, no puede volver a votar");

				}

			} else {

				throw new Exception("El usuario no tienie permitido acceder a esta votacion");

			}

		} else {

			throw new Exception("La votacion esta cerrada, no se pueden emitir mas votos");

		}

	}

	public List<Voto> getVotos(Votacion votacion) {

		return votoRepository.getVotoByVotavion(votacion);

	}

	public void delete(Voto voto) {
		votoRepository.delete(voto);
	}

	public List<Voto> getVotosByUser(User user) {

		List<Voto> votos = votoRepository.findVotoByUser(user);

		for (Voto voto : votos) {

			if (voto.getOpcion() != null && voto.getOpcion().getVotos() != null) {
				voto.getOpcion().getVotos().remove(voto);
			}
			votoRepository.save(voto);
			System.out.println(voto);
		}

		return votoRepository.findVotoByUser(user);

	}

	public boolean haVotado(User user) {

		List<Voto> votos = votoRepository.findVotoByUser(user);
		
		for (Voto voto : votos) {
			if (voto.getOpcion() != null) {
				return true;
			}
		}

		return false;
	}

}
