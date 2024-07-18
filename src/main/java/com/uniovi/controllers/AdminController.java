package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unbescape.html.HtmlEscape;

import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.services.CensoFileService;
import com.uniovi.services.UserService;
import com.uniovi.services.VotacionService;
import com.uniovi.services.VotoService;

@Controller
public class AdminController {

	@Autowired
	private VotacionService votacionService;
	@Autowired
	private CensoFileService censoFileService;
	@Autowired
	private UserService userService;
	@Autowired
	private VotoService votoService;

	@RequestMapping(value = "/admin/irCargarCenso", method = RequestMethod.GET)
	public String irCargarCenso(Model model) {
		return "admin/loadCenso";
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	  public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
	    model.addAttribute("msgFile", "File is too large!");

	    return "admin/loadCenso";
	  }

	@RequestMapping(value = "/cargarCenso", method = RequestMethod.POST)
	public String cargarCenso(Model model, @RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {

			Map<String, Object> auxiliar = null;
			try {
				auxiliar = censoFileService.readCensoFile(file.getOriginalFilename());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				model.addAttribute("msgFile", e.getMessage());
			}

			if (auxiliar != null) {
				String msg = (String) auxiliar.get("msg");

				model.addAttribute("msg",
						HtmlEscape.escapeHtml4Xml(msg).replace(System.getProperty("line.separator"), "<br />"));
			}
		} else {
			model.addAttribute("msgFile", "No se ha seleccionado ningún archivo");

		}

		return "admin/loadCenso";
	}

	@RequestMapping(value = "/admin/irGestionUsuarios", method = RequestMethod.GET)
	public String irGestionUsuarios(Model model) {

		List<User> listaUsuarios = userService.getAllVotantes();

		model.addAttribute("usersList", listaUsuarios);

		return "admin/gestionUsuarios";
	}

	@RequestMapping(value = "/volverAdmin", method = RequestMethod.GET)
	public String volverHome(Model model) {

		return "admin/home";
	}

	@RequestMapping("/admin/delete/{id}")
	public String deleteUser(@PathVariable Long id, Principal principal, Model model) {

		User user = userService.getUserById(id);

		if (votoService.haVotado(user)) {

			return "admin/gestionUsuariosInfo";

		}
		userService.delete(user);

		return "redirect:/admin/irGestionUsuarios";

	}

	@RequestMapping(value = "/admin/irGestionarVotaciones", method = RequestMethod.GET)
	public String irGestionVotaciones(Model model) {

		List<Votacion> listaVotaciones = votacionService.getVotaciones();

		model.addAttribute("votaciones", listaVotaciones);

		return "admin/gestionVotaciones";

	}

}
