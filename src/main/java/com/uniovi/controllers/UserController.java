package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniovi.entities.Pais;
import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import com.uniovi.services.EmailService;
import com.uniovi.services.PaisService;
import com.uniovi.services.PasswordService;
import com.uniovi.services.RolesService;
import com.uniovi.services.UserService;
import com.uniovi.services.VotacionService;
import com.uniovi.services.VotoService;
import com.uniovi.validators.SignUpFormValidator;
import com.uniovi.validators.UpdatUserInfoValidator;

@Controller
@PropertySource("classpath:messages.properties")

public class UserController {

	@Autowired
	private UserService usersService;
	@Autowired
	private PaisService paisService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private VotoService votoService;
	@Autowired
	private SignUpFormValidator singUpFormValidator;
	@Autowired
	private UpdatUserInfoValidator updatUserInfoValidator;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private VotacionService votacionService;

	@Autowired
	private Environment env;

	@RequestMapping(value = "/singup", method = RequestMethod.GET)
	public String singup(Model model, HttpServletRequest request) {

		// List<String> municipalities = rCountryService.finaAllMunicipalities();
		// assume the list is populated with values
		List<Pais> paises = paisService.getAll();

		model.addAttribute("paises", paises);
		model.addAttribute("user", new User());

		return "singup";
	}

	@RequestMapping(value = "/singup", method = RequestMethod.POST)
	public String singup(@Validated User user, BindingResult result, Model model, HttpServletRequest request) {
		singUpFormValidator.validate(user, result);

		if (result.hasErrors()) {
			Errors errores = result;

			System.out.println(errores.getErrorCount());

			System.out.println(errores.getAllErrors());

			String erroresCreacion = "";
			//
			for (ObjectError error : errores.getAllErrors()) {

				// System.err.println(env.getProperty(error.getDefaultMessage()));
				erroresCreacion = erroresCreacion + "\t" + env.getProperty(error.getDefaultMessage());

			}
			//
			model.addAttribute("erroresCreacion", erroresCreacion);

			return "singup_err";

			// return "redirect:/singup";
		}

		user.setRole(rolesService.getRoles()[0]);
		user.setPassword(passwordService.generarPassword(15));
		user.setPasswordConfirm(user.getPassword());
		emailService.sendNewUserEmail(user);
		usersService.addUser(user);

		return "singup_info";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) {
		// session.setAttribute("tipoLogin", "normal");
		return "login";

	}

	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String loginError(Model model) {

		model.addAttribute("error",
				"Los datos introducidos no se corresponden con ningún usuario registrado en la aplicación");
		return "login";

	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model, HttpSession session, Principal principal) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		// System.out.println(numDocumento);
		User user = usersService.getUserByNumDocumento(numDocumento);// (email);
		// System.out.println(user.getRole());

		if (user.getRole().equals("ROLE_USER")) {

			// System.out.println("User Home");
			return "user/home";

		} else if (user.getRole().equals("ROLE_ADMIN")) {

			// System.out.println("Admin Home");
			return "redirect:/admin/home";

		}

		return "redirect:login-error";
	}

	@RequestMapping("/user/home")
	public String getUserHome(Model model, Principal principal) {
		String numDocumento = principal.getName(); // email es el name de la autenticación
		// System.out.println(numDocumento);
		User user = usersService.getUserByNumDocumento(numDocumento);

		model.addAttribute("user", user);
		return "user/home";
	}

	@RequestMapping("/admin/home")
	public String getAdminHome(Model model, Principal principal) {
		String numDocumento = principal.getName(); // email es el name de la autenticación
		// System.out.println(numDocumento);
		User user = usersService.getUserByNumDocumento(numDocumento);

		model.addAttribute("user", user);

		// System.out.println("Admin Home");

		return "/admin/home";
	}

	@RequestMapping(value = "/volver", method = RequestMethod.GET)
	public String volverHome(Model model) {

		return "user/home";
	}

	@RequestMapping(value = "/verPerfil", method = RequestMethod.GET)
	public String verPerfil(Model model, Principal principal, HttpServletRequest request) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);
		user.setPassword(null);
		model.addAttribute("user", user);

		return "/user/perfil";
	}

	@RequestMapping(value = "/verVotaciones", method = RequestMethod.GET)
	public String verVotaciones(Model model, Principal principal) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);

		List<Votacion> votaciones = votacionService.getVotacionesDisponible(user);

		model.addAttribute("votaciones", votaciones);

		return "/user/votacionesList";

	}

	@RequestMapping(value = "/irVotar/{idVotacion}", method = RequestMethod.GET)
	public String irVotar(@PathVariable Long idVotacion, Model model, Principal principal) {


		Votacion votacion = votacionService.getVotacion(idVotacion);

		Date inicioVotacion=votacion.getFechaInicio();
		Date finVotacion=votacion.getFechaFin();
		Date today=new Date();
		
		if(today.after(inicioVotacion) && today.before(finVotacion)) {
			
			model.addAttribute("votacion", votacion);
			model.addAttribute("opciones", votacion.getOpciones());
			
		}else {
			String numDocumento = principal.getName(); // email es el name de la autenticación
			User user = usersService.getUserByNumDocumento(numDocumento);
			model.addAttribute("votaciones", votacionService.getVotacionesDisponible(user));//votacionService.getVotaciones());
			model.addAttribute("msgVotacionInfo", "No se puede emitir voto, la votación no esta activa");
			return "/user/votacionesList";
			
		}
		

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);
		
		
		Voto v = votoService.findVotoByuserAndVotacion(user, votacion);
		
		//System.out.println("Voto "+v);
		if(v!=null && v.getOpcion()!=null) {
			
			model.addAttribute("tituloVoto", "Error al emitir el voto");
			model.addAttribute("msgVoto","El usuario ya ha emitido su voto para esta votacion con anterioriad, no puede volver a votar");
			
			return "/user/infoVoto";
			
		}
		// System.out.println(opciones.toString());

		return "/user/votar";

	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String modificarPerfil(@Validated User user, Model model, BindingResult result, Principal principal,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		
		String numDocumento = principal.getName(); // email es el name de la autenticación
		user = usersService.getUserByNumDocumento(numDocumento);
		
		
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String repPass = request.getParameter("reptPass");

		String calle = request.getParameter("calle");
		String cp = request.getParameter("cp");
		String poblacion = request.getParameter("poblacion");
		String provincia = request.getParameter("provincia");
		String pais = request.getParameter("pais");

		User userAux = new User();

		userAux.setEmail(email);
		userAux.setPassword(pass);
		userAux.setPasswordConfirm(repPass);

		userAux.setCalle(calle);
		userAux.setCp(cp);
		userAux.setPoblacion(poblacion);
		userAux.setProvincia(provincia);
		userAux.setPais(pais);

		updatUserInfoValidator.validate(userAux, result);

		if (result.hasErrors()) {
			Errors errores = result;

			System.out.println(errores.getErrorCount());

			System.out.println(errores.getAllErrors());

			String erroresUpdate = "";
			//
			for (ObjectError error : errores.getAllErrors()) {

				erroresUpdate = erroresUpdate + "\t" + env.getProperty(error.getDefaultMessage());

			}

			redirectAttrs.addFlashAttribute("erroresUpdate", erroresUpdate);

			return "redirect:/verPerfil";

		}

		boolean isUpdated = usersService.modificar(user, userAux);
		System.out.println(isUpdated);
		System.out.println(usersService.getUserByNumDocumento(user.getNumDocumento()));
		
		model.addAttribute("user", user);

		if (isUpdated) {
			return "redirect:/verPerfilInfo";

		}

		return "redirect:/verPerfil";
	}

	@RequestMapping(value = "/verPerfilInfo", method = RequestMethod.GET)
	public String verPerfil2(Model model, Principal principal) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);
		user.setPassword(null);
		model.addAttribute("user", user);
		model.addAttribute("msgUpdateInfo", "Datos Actualizados");

		return "/user/perfil";
	}

	@RequestMapping(value = "/verPerfilErr", method = RequestMethod.GET)
	public String verPerfilErr(Model model, Principal principal) {

		String numDocumento = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByNumDocumento(numDocumento);
		user.setPassword(null);
		model.addAttribute("user", user);
		// model.addAttribute("msgUpdateInfo", "Datos Actualizados");

		return "/user/perfil";
	}

	@RequestMapping(value = "/verPoliticaPrivacidad", method = RequestMethod.GET)
	public String verPoliticaPrivacidad(Model model) {

		return "verPoliticaPrivacidad";

	}

	@RequestMapping(value = "/resetPass", method = RequestMethod.GET)
	public String irResetPass(Model model) {

		return "resetPass";

	}

	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public String resetPass(User user, Model model) {

		usersService.restPassword(user.getNumDocumento());

		return "resetPassInfo";

	}

}
