package com.uniovi.services;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.entities.Voto;
import com.uniovi.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private VotoService votoService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@PostConstruct
	public void init() {
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User getUserByNumDocumento(String numDocumento) {
		return userRepository.findByNumDocumento(numDocumento);
	}

	public boolean validateDocumentacion(User user) {

		boolean isValid = false;
		String tipoDocumento = user.getTipoDocumento();
		String numDoc = user.getNumDocumento();

		if (numDoc.length() == 9) {

			String dniRegexp = "\\d{8}[A-HJ-NP-TV-Z]";
			String nifRegex = "(^[XYZ]\\d{7}[A-Z&&[^IÑOU]])";// "\\[XYZ]{1}{[0-0]7}[A-HJ-NP-TV-Z]";

			String[] asignacionLetra = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
					"Q", "V", "H", "L", "C", "K", "E" };
			int resto;
			int num;
			String letra = "";

			switch (tipoDocumento) {
			case "DNI":

				num = Integer.parseInt(numDoc.substring(0, 8));
				resto = num % 23;
				letra = asignacionLetra[resto];

				isValid = Pattern.matches(dniRegexp, numDoc) && letra.equals(numDoc.substring(8).toUpperCase());
				System.out.println(isValid);
				break;

			case "NIE":

				letra = "";
				String letraI = numDoc.substring(0, 1).toUpperCase();
				String auxNum = null;

				if (letraI.equals("X")) {
					auxNum = "0" + numDoc.substring(1, 8);
				} else if (letraI.equals("Y")) {
					auxNum = "1" + numDoc.substring(1, 8);
				} else if (letraI.equals("Z")) {
					auxNum = "2" + numDoc.substring(1, 8);
				} else {
					isValid = false;
					break;
				}

				num = Integer.parseInt(auxNum);
				resto = num % 23;
				letra = asignacionLetra[resto];

				isValid = Pattern.matches(nifRegex, numDoc) && (letra.equals(numDoc.substring(8).toUpperCase()));

				break;

			default:
				isValid = false;
				break;
			}
		}

		return isValid;
	}

	public List<User> getAllVotantes() {

		return userRepository.getAllVotantes();

	}

	public User getUserById(Long id) {

		return userRepository.findById(id);

	}

	public void delete(User user) {

		logger.debug(String.format("Eliminado usuario con email: %s.", user.getNumDocumento()));

		List<Voto> votos = votoService.getVotosByUser(user);
		// List<OpcionesVotacion> opciones= opcionesService.
		for (Voto voto : votos) {
			// voto.setOpcion(null);
			if (voto.getUser().getId() == user.getId() && voto.getOpcion() == null) {
				votoService.delete(voto);
			}
		}

		userRepository.delete(user);

	}

	public boolean modificar(User user, User userAux) {

		User userEmailInfo = new User();
		boolean updated = false;

		if (userAux.getPasswordConfirm() != null && !userAux.getPasswordConfirm().isEmpty()
				&& userAux.getPassword() != null && !userAux.getPassword().isEmpty()
				&& userAux.getPassword().equals(userAux.getPasswordConfirm())) {

			System.out.println(userAux.getPassword());

			userEmailInfo.setPassword(userAux.getPassword());

			user.setPassword(bCryptPasswordEncoder.encode(userAux.getPassword()));
			updated = true;
		}

		if (!userAux.getEmail().isEmpty() && !userAux.getEmail().equals(user.getEmail())) {

			user.setEmail(userAux.getEmail());
			userEmailInfo.setEmail(userAux.getEmail());
			updated = true;

		}

		String calle = userAux.getCalle();
		String cp = userAux.getCp();
		String pob = userAux.getPoblacion();
		String prov = userAux.getProvincia();
		String pais = userAux.getPais();

		if (!calle.isEmpty() && !calle.equals(user.getCalle())) {

			user.setCalle(calle);
			userEmailInfo.setCalle(calle);
			updated = true;

		}

		if (!cp.isEmpty() && !cp.equals(user.getCp())) {

			user.setCp(cp);
			userEmailInfo.setCp(cp);
			updated = true;

		}

		if (!pob.isEmpty() && !pob.equals(user.getPoblacion())) {

			user.setPoblacion(pob);
			userEmailInfo.setPoblacion(pob);
			updated = true;

		}

		if (!prov.isEmpty() && !prov.equals(user.getProvincia())) {

			user.setProvincia(prov);
			userEmailInfo.setProvincia(prov);
			updated = true;

		}

		if (!pais.isEmpty() && !pais.equals(user.getPais())) {

			user.setPais(pais);
			userEmailInfo.setPais(pais);
			updated = true;

		}

		if (updated) {
			userRepository.save(user);
			emailService.sendChangeUserInfoEmail(userEmailInfo, user.getEmail());

		}

		return updated;

	}

	public List<User> filtrarVotantesPorEdad(int opcFiltro, List<User> usuarios) {

		int filtroSuperior = Integer.MAX_VALUE;
		int filtroInferior = Integer.MIN_VALUE;

		switch (opcFiltro) {
		case 1:
			filtroSuperior = 18;
			break;
		case 2:
			filtroInferior = 18;
			filtroSuperior = 25;
			break;

		case 3:
			filtroInferior = 25;
			filtroSuperior = 30;
			break;
		case 4:
			filtroInferior = 30;
			filtroSuperior = 40;
			break;

		case 5:
			filtroInferior = 40;
			filtroSuperior = 50;
			break;
		case 6:
			filtroInferior = 50;
			filtroSuperior = 60;
			break;
		case 7:
			filtroInferior = 60;
			break;
		default:
			break;
		}

		List<User> usuariosFiltrados = new ArrayList<User>();

		for (User user : usuarios) {

			if (filtroInferior <= user.getEdad() && user.getEdad() <= filtroSuperior) {

				//User aux = getUserByNumDocumento(user.getNumDocumento());

				//if (aux == null) {

					//addUser(user);
					//emailService.sendNewUserEmail(user);
					usuariosFiltrados.add(user);

				//} else {

				//	usuariosFiltrados.add(aux);

				//}

			}

		}

		return usuariosFiltrados;

	}

	public void restPassword(String dni) {

		User user = userRepository.findByNumDocumento(dni);

		if (user != null) {
			user.setPassword(passwordService.generarPassword(15));
			emailService.sendResetPasswordEmail(user);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			userRepository.save(user);
		}

	}

	public List<User> filtrarVotantesPorGenero(int filtroGenero, List<User> usuarios) {

		String genero = null;
		switch (filtroGenero) {

		case 1:
			genero = "M";
			break;
		case 2:
			genero = "F";
			break;
		case 3:
			genero = "NB";
			break;
		default:
			genero = null;
		}
		if (genero == null) {
			return usuarios;
		}
		List<User> usuariosFiltrados = new ArrayList<User>();

		for (User user : usuarios) {
			System.out.println("Genero user: " + user.getGenero());
			System.out.println("Genero filtro: " + genero);
			System.out.println("Genero comparacion: " + user.getGenero().equals(genero));

			if (user.getGenero().equals(genero)) {
				//User aux = getUserByNumDocumento(user.getNumDocumento());
				//if (aux == null) {
					//addUser(user);
					// emailService.sendNewUserEmail(user);
					usuariosFiltrados.add(user);
				//} else {
				//	usuariosFiltrados.add(aux);
				//}
			}
		}

		return usuariosFiltrados;

	}

	public boolean comprobarEdad(Date fechaNacimiento) {

		Calendar birthDay = new GregorianCalendar();
		birthDay.setTime(fechaNacimiento);
		Calendar today = new GregorianCalendar();
		today.setTime(new Date());

		int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

		int monthsDiff = today.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);

		int ageInMonths = yearsInBetween * 12 + monthsDiff;

		Period period = Period.ofMonths(ageInMonths).normalized();

		return period.getYears() > 16;
	}

	public Map<String, List<User>> ChekCensoFile(List<User> usuarios) {

		Map<String, List<User>> mapaUsers = new HashMap<String, List<User>>();
		List<User> listaViejos = new ArrayList<User>();
		List<User> listaNuevos = new ArrayList<User>();

		for (User user : usuarios) {
			User userAux = userRepository.findByNumDocumento(user.getNumDocumento());
			System.out.println("userAux "+userAux);
			if (userAux != null) {
				listaViejos.add(userAux);
			} else {
				addUser(user);
				System.out.println("Nuevo user añadido");
				listaNuevos.add(user);
			}

		}

		mapaUsers.put("usuariosNuevos", listaNuevos);
		mapaUsers.put("usuariosViejos", listaViejos);

		return mapaUsers;
	}

}
