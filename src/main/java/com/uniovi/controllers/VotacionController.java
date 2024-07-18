package com.uniovi.controllers;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import com.uniovi.services.CensoFileService;
import com.uniovi.services.EmailService;
import com.uniovi.services.OpcionesVotacionService;
import com.uniovi.services.UserService;
import com.uniovi.services.VotacionService;
import com.uniovi.services.VotoService;
import com.uniovi.validators.CreateVoteFormValidator;

@Controller
public class VotacionController {

	@Autowired
	private CreateVoteFormValidator createVoteFormValidator;
	@Autowired
	private VotacionService votacionService;
	@Autowired
	private VotoService votoService;
	@Autowired
	private OpcionesVotacionService opcionesService;
	@Autowired
	private CensoFileService censoFileService;
	@Autowired
	private OpcionesVotacionService opcionService;
	@Autowired
	private UserService usersService;
	@Autowired
	private EmailService emailService;

	@Autowired
	private Environment env;

	private Map<String, List<User>> mapaUsers;

	@RequestMapping(value = "/admin/crearVotacion", method = RequestMethod.GET)
	public String creacionVotacion(Model model) {

		Votacion votacion = new Votacion();
		votacion.setOpciones(new HashSet<OpcionesVotacion>());
		model.addAttribute("votacion", new Votacion());

		return "admin/crearVotacion";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/irCrearVotacionSiguiente", method = RequestMethod.POST)
	public String crearVotacion(@Validated @ModelAttribute("votacion") Votacion votacion, BindingResult result,
			Model model, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		createVoteFormValidator.validate(votacion, result);
		System.out.println("Fichero vacio: " + file.isEmpty());
		if (!file.isEmpty()) {
			votacion.setForAll(false);
		} else {
			votacion.setForAll(true);
		}
		System.out.println("Tiene Errores: " + result.hasErrors());
		if (result.hasErrors()) {

			Errors errores = result;

			String erroresCreacionVotacion = "";

			for (ObjectError error : errores.getAllErrors()) {
				erroresCreacionVotacion = erroresCreacionVotacion + "\t" + env.getProperty(error.getDefaultMessage());
			}

			model.addAttribute("erroresCreacionVotacion", erroresCreacionVotacion);

			return "admin/crearVotacionError";
		}

		List<User> usuarios = null;
		Map<String, Object> auxiliar;
		votacionService.addVotacion(votacion);

		int filtroEdad = votacion.getFiltroEdad();
		int filtroGenero = votacion.getFiltroGenero();
		if (!file.isEmpty()) {
			System.out.println("Procesando Fichero");
			try {
				auxiliar = censoFileService.readCensoFileWhileCreatingPoll(file.getOriginalFilename());
				System.out.println("aux votacion controller crear votacon"+auxiliar);

				usuarios = (List<User>) auxiliar.get("Usuarios");
				String msgCreacionVotacion = (String) auxiliar.get("msg");
				System.out.println("antes filtro");

				mapaUsers = aplicarFiltroVotacion(votacion, usuarios, filtroEdad, filtroGenero,true);
				System.out.println("despues filtro");

				model.addAttribute("tituloCreacion", "Informacion Complementaria");
				model.addAttribute("msgCreacionVotacion", msgCreacionVotacion);
				System.out.println("Redirect");

			} catch (Exception e) {

				model.addAttribute("error", e.getMessage());
				return "admin/crearVotacion";

			}

		} else {
			// aqui añado los filtros a todos los users de la aplicacion

			if (filtroEdad != 0 || filtroGenero != 0) {
				List<User> listadoUsuarios = usersService.getAllVotantes();
				aplicarFiltroVotacion(votacion, listadoUsuarios, filtroEdad, filtroGenero,false);
			}

		}
		System.out.println("Redirect");
		redirectAttributes.addAttribute("idVotacion", votacion.getId());
		model.addAttribute("votacion", votacion);
		return "/admin/crearVotacion2";
		// return "redirect:admin/crearVotacionSiguiente/{idVotacion}";
	}

	private Map<String, List<User>> aplicarFiltroVotacion(Votacion votacion, List<User> usuarios, int filtroEdad,
			int filtroGenero, boolean checkFile) {
		System.out.println("Filtro Edad: " + filtroEdad + ", Filtro Genero: " + filtroGenero);
		if (filtroEdad != 0) {
			System.out.println("Usuario Antes Filtro Edad: "+usuarios.size());
			usuarios = usersService.filtrarVotantesPorEdad(filtroEdad, usuarios);
			System.out.println("Usuario Despues Filtro Edad: "+usuarios.size());

		}

		if (filtroGenero != 0) {
			System.out.println("Usuario Antes Filtro Genero: "+usuarios.size());
			usuarios = usersService.filtrarVotantesPorGenero(filtroGenero, usuarios);
			System.out.println("Usuario Despues Filtro Genero: "+usuarios.size());

		}
		System.out.println("Usuarios para crear: " + usuarios.size());

		//if (checkFile) {
			Map<String, List<User>> mapaUsers = usersService.ChekCensoFile(usuarios);

			for (Map.Entry<String, List<User>> entry : mapaUsers.entrySet()) {
				votacionService.asignarVotantes(votacion, entry.getValue());
			}
			// votacionService.notificarNuevaVotacion(votacion, usuarios);
			return mapaUsers;
		//} else {
		//	return usuarios;
		//}

	}

	// @SuppressWarnings("null")
	@RequestMapping(value = "admin/finalizarCreacion/{idVotacion}", method = RequestMethod.POST)
	public String finalizarCreacionVotacion(Model model, @PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);
		System.out.println(mapaUsers);
		List<User> usuarios = new ArrayList<User>();

		// int filtroEdad = votacion.getFiltroEdad();
		// int filtroGenero = votacion.getFiltroGenero();

		// System.out.println(filtroEdad + " " + filtroGenero);
		if (mapaUsers != null) {
			
			List<User> usuariosNuevos = mapaUsers.get("usuariosNuevos");

			for (User user : usuariosNuevos) {
				System.out.println("Enviando email nuevo user: "+user.getNombre());
				emailService.sendNewUserEmail(user);
			}
		}

		List<Voto> votos = votoService.getVotos(votacion);

		if (votos.size() > 0) {
			for (Voto voto : votos) {
				usuarios.add(voto.getUser());
			}

		} else {

			usuarios = (List<User>) usersService.getAllVotantes();

		}

		/*
		 * if(votacion.isForAll()) {
		 * 
		 * 
		 * //usuarios = usersService.filtrarVotantesPorEdad(filtroEdad, usuarios);
		 * System.out.println(usuarios.size()); if(filtroEdad!=0 || filtroGenero!=0) {
		 * 
		 * aplicarFiltroVotacion(votacion,usuarios,filtroEdad,filtroGenero);
		 * 
		 * //votacionService.asignarVotantes(votacion, usuarios);
		 * //votacionService.notificarNuevaVotacion(votacion, usuarios);
		 * 
		 * }
		 * 
		 * }else {
		 * 
		 * List<Voto> votos = votoService.getVotos(votacion);
		 * 
		 * for (Voto voto : votos) { usuarios.add(voto.getUser()); }
		 * 
		 * }
		 */

		votacionService.notificarNuevaVotacion(votacion, usuarios);

		return "redirect:/admin/irGestionarVotaciones";

	}

	@RequestMapping(value = "admin/crearVotacionSiguiente/{idVotacion}", method = RequestMethod.GET)
	public String verCrearVotacionSiguiente(Model model, Principal principal, HttpServletRequest request,
			@PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		model.addAttribute("votacion", votacion);
		System.out.println("cambio pag");
		return "/admin/crearVotacion2";

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/mostrarResultados/{idVotacion}", method = RequestMethod.GET)
	public String mostrarResultadosVotacion(Model model, @PathVariable Long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);
		List<Voto> votosList = votoService.getVotos(votacion);

		Map<String, Integer> votos = new TreeMap<String, Integer>();

		int noParticipan = 0;
		int participan = 0;
		for (Voto voto : votosList) {

			if (voto.getOpcion() == null) {

				noParticipan++;
				continue;
			}

			String opcion = voto.getOpcion().getOpcion();
			participan++;
			if (votos.get(opcion) == null) {

				votos.put(opcion, 1);
				continue;

			}

			votos.put(opcion, votos.get(opcion) + 1);
			System.out.println(voto.toString());

		}

		model.addAttribute("nombreVotacion", votacion.getNombre());
		model.addAttribute("votacion", votos);

		return "resultadoVotacion";

	}

	@RequestMapping(value = "/user/votar/{idVotacion}", method = RequestMethod.POST)
	public String votar(Model model, Principal principal, HttpServletRequest request, @PathVariable long idVotacion) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);

		String param = request.getParameter("opcion");

		Long idOpcion = (long) 1;// opcion En Blanco

		if (param != null) {
			idOpcion = Long.valueOf(param);
		}

		OpcionesVotacion opcion = opcionService.findOpcion(idOpcion);
		Votacion votacion = votacionService.getVotacion(idVotacion);

		try {
			votoService.votar(opcion, user, votacion);
			model.addAttribute("tituloVoto", "Gracias por participar ");
			model.addAttribute("msgVoto", "Voto emitido correctamente");
			emailService.sendVotoInfoEmail(user, votacion);
		} catch (Exception e) {
			model.addAttribute("tituloVoto", "Error al emitir el voto");
			model.addAttribute("msgVoto", e.getMessage());
		}

		return "/user/infoVoto";
	}

	@RequestMapping(value = "/admin/verVotacion/{idVotacion}", method = RequestMethod.GET)
	public String verVotacion(Model model, Principal principal, HttpServletRequest request,
			@PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		System.out.println(votacion.getFechaInicio().equals(new Date()));
		System.out.println(new Date());
		System.out.println(votacion.getFechaInicio());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(votacion.getFechaInicio().toString());
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		Date diaAntesInicioVotacion = calendar.getTime();

		Date today = new Date();
		calendar.setTime(today);

		// System.out.println(diaAntesInicioVotacion);
		// System.out.println(date);
		// System.out.println(calendar.getTime());
		// System.out.println(diaAntesInicioVotacion.after(calendar.getTime()));

		if (!diaAntesInicioVotacion.after(calendar.getTime())) {
			model.addAttribute("deleteVotacion", false);

			return "/admin/EditVotacionInfo";
		}

		model.addAttribute("votacion", votacion);

		return "/admin/votacion";

	}

	@RequestMapping(value = "/votacion/anadirOpcion/{idVotacion}", method = RequestMethod.POST)
	public String añadirOpcion(Model model, Principal principal, HttpServletRequest request,
			@PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		try {
			if (esModificable(votacion)) {

				String nuevaOpcion = request.getParameter("nuevaOpcion");

				if (!nuevaOpcion.isEmpty()) {

					OpcionesVotacion op = new OpcionesVotacion(votacion, nuevaOpcion);
					opcionesService.addOpcion(op);
				}

			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}

		return "redirect:/admin/verVotacion/" + votacion.getId();

	}

	@RequestMapping("/votacion/eliminarOpcion/{idVotacion}/{idOpcion}")
	public String deleteOpcion(Principal principal, Model model, @PathVariable long idVotacion,
			@PathVariable long idOpcion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		try {
			if (esModificable(votacion)) {
				OpcionesVotacion opcion = opcionesService.findOpcion(idOpcion);
				opcionesService.delete(opcion);
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/verVotacion/" + votacion.getId();

	}

	@RequestMapping("/admin/deleteVotacion/{idVotacion}")
	public String deleteVotacion(Principal principal, Model model, @PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(votacion.getFechaInicio());
		calendar.add(Calendar.DATE, -1);
		Date diaAntesInicioVotacion = calendar.getTime();

		Date today = new Date();
		calendar.setTime(today);

		// System.out.println(diaAntesInicioVotacion);
		// System.out.println(date);
		// System.out.println(calendar.getTime());
		// System.out.println(diaAntesInicioVotacion.after(calendar.getTime()));

		if (!diaAntesInicioVotacion.after(calendar.getTime())) {

			model.addAttribute("deleteVotacion", true);

			return "/admin/EditVotacionInfo";

		} else {

			try {
				System.out.println(esModificable(votacion));

				if (esModificable(votacion)) {
					votacionService.delete(votacion);

				} else {
					List<Votacion> listaVotaciones = votacionService.getVotaciones();

					model.addAttribute("votaciones", listaVotaciones);
					model.addAttribute("msgVotacionInfo", "La votación no se puede eliminar una vez ya haya comenzado");
					return "admin/gestionVotaciones";

				}
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}
		return "redirect:/admin/irGestionarVotaciones";

	}

	@RequestMapping(value = "/votacion/anadirOpcion2/{idVotacion}", method = RequestMethod.POST)
	public String añadirOpcion2(Model model, Principal principal, HttpServletRequest request,
			@PathVariable long idVotacion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		String nuevaOpcion = request.getParameter("nuevaOpcion");
		if (!nuevaOpcion.isEmpty()) {

			OpcionesVotacion op = new OpcionesVotacion(votacion, nuevaOpcion);
			opcionesService.addOpcion(op);
		}

		return "redirect:/admin/crearVotacionSiguiente/" + votacion.getId();

	}

	@RequestMapping("/votacion/eliminarOpcion2/{idVotacion}/{idOpcion}")
	public String deleteOpcion2(Principal principal, Model model, @PathVariable long idVotacion,
			@PathVariable long idOpcion) {

		Votacion votacion = votacionService.getVotacion(idVotacion);

		OpcionesVotacion opcion = opcionesService.findOpcion(idOpcion);
		opcionesService.delete(opcion);

		return "redirect:/admin/crearVotacionSiguiente/" + votacion.getId();

	}

	private boolean esModificable(Votacion votacion) throws ParseException {

		ZonedDateTime today = ZonedDateTime.now();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		Date fechaInicio = formato.parse(votacion.getFechaInicio().toString());
		Date fechaActual = formato.parse(today.toString());

		if (!fechaActual.before(fechaInicio)) {
			return false;
		}

		return true;

	}

}
