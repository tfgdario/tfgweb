package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendNewUserEmail(User user) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		StringBuilder contenido= new StringBuilder();
		
		contenido.append("Bienvenido a eVote");
		contenido.append("\n");
		contenido.append("A continuacion aparece la contraseña que el sistema ha generado automaticamente");
		contenido.append("\n");
		contenido.append("\t\t\t").append(user.getPasswordConfirm());
		contenido.append("\n");
		contenido.append("A continuacion le indicaremos los datos que registraremos en el sistema");
		contenido.append("\n");
		contenido.append(user.getNombre()+" "+user.getApellidos());
		contenido.append("\n");
		contenido.append(user.getTipoDocumento()+" "+user.getNumDocumento());
		contenido.append("\n");
		contenido.append(user.getCalle()+" "+user.getPoblacion()+" "+user.getProvincia()+" "+user.getPais()+" "+user.getCp());
		contenido.append("\n");
		
		email.setTo(user.getEmail());
		email.setSubject("Bienvenido a eVote");
		email.setText(contenido.toString());

		mailSender.send(email);
	}
	
	
	public void sendNewVotacionEmail(User user,Votacion votacion) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		StringBuilder contenido= new StringBuilder();
		
		
		contenido.append("Hola ").append(user.getNombre());
		contenido.append("\n");
		contenido.append("Queremos comunicar que hay una nueva votacion disponible en la cual puede participar");
		contenido.append("\n");
		contenido.append("\t\t\t").append(votacion.getNombre());
		contenido.append("\n");
		contenido.append("disponible desde: ").append(votacion.getFechaInicio()).append(" hasta ").append(votacion.getFechaFin());
		contenido.append("\n");
		contenido.append("Saludos,");
		contenido.append("Equipo de evote");
		contenido.append("\n");
		
		email.setTo(user.getEmail());
		email.setSubject("Nueva Votación Disponible");
		email.setText(contenido.toString());

		mailSender.send(email);
		
	}


	public void sendResetPasswordEmail(User user) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		StringBuilder contenido= new StringBuilder();
		
		
		contenido.append("Hola");
		contenido.append("\n");
		contenido.append("Te indicamos la nueva contraseña solicitada, a continuanción aparece la contraseña que el sistema ha generado automaticamente");
		contenido.append("\n");
		contenido.append("\t\t\t").append(user.getPassword());
		contenido.append("\n");
		
		email.setTo(user.getEmail());
		email.setSubject("Nueva Contraseña");
		email.setText(contenido.toString());

		mailSender.send(email);
		
	}


	public void sendChangeUserInfoEmail(User user, String userEmail) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		StringBuilder contenido= new StringBuilder();
		
		
		contenido.append("Hola");
		contenido.append("\n");
		contenido.append("Se han modificado algunos Datos en su perfil");
		contenido.append("\n");
		contenido.append("A continuacion indicamos los cambios realidazados y los nuevos valores registrados");
		contenido.append("\n");

		if (user.getPassword()!=null && !user.getPassword().isEmpty() ){

			contenido.append("La contraseña ha sido modificada");
			contenido.append("\n");

		}

		if (user.getEmail()!=null &&!user.getEmail().isEmpty()) {
			
			contenido.append("Nueva direccion de correo electronico registrada");
			contenido.append("\t\t\t").append(user.getEmail());
			contenido.append("\n");

		}

		String calle = user.getCalle();
		String cp = user.getCp();
		String pob = user.getPoblacion();
		String prov = user.getProvincia();
		String pais = user.getPais();

		if (calle!=null &&!calle.isEmpty()) {

			contenido.append("Nueva Calle registrada");
			contenido.append("\t\t\t").append(calle);
			contenido.append("\n");

		}

		if (cp!=null &&!cp.isEmpty()) {

			contenido.append("Nuevo codigo postal registrado");
			contenido.append("\t\t\t").append(cp);
			contenido.append("\n");

		}

		if (pob!=null &&!pob.isEmpty()) {

			contenido.append("Nueva poblacion registrada");
			contenido.append("\t\t\t").append(pob);
			contenido.append("\n");

		}

		if (prov!=null &&!prov.isEmpty()) {

			contenido.append("Nueva provincia registrada");
			contenido.append("\t\t\t").append(prov);
			contenido.append("\n");

		}

		if (pais!=null &&!pais.isEmpty()) {

			contenido.append("Nuevo país registrado");
			contenido.append("\t\t\t").append(pais);
			contenido.append("\n");

		}

		contenido.append("\n");
		
		contenido.append("Si no has sido tú el que ha solicitado este cambio debes ponerte en contacto con el administrador de la aplicación enviando un correo a la dirección info@evote.com");
		
		email.setTo(userEmail);
		email.setSubject("Datos De Perfil Modificados");
		email.setText(contenido.toString());

		mailSender.send(email);
		
	}


	public void sendVotoInfoEmail(User user, Votacion votacion) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		StringBuilder contenido= new StringBuilder();
		
		
		contenido.append("Hola");
		contenido.append("\n");
		contenido.append("\n");
		contenido.append("Le informamos que el voto emitido para la votacion ");
		contenido.append(votacion.getNombre());
		contenido.append(" ha sido registrado correctamente \n");
		contenido.append("\n");
		
		contenido.append("\n");
		contenido.append("Saludos");

		
		email.setTo(user.getEmail());
		email.setSubject("Voto Registrado");
		email.setText(contenido.toString());

		mailSender.send(email);
		
	}


}
