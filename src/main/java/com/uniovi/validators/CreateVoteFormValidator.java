package com.uniovi.validators;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Votacion;

@Component
public class CreateVoteFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return Votacion.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

		
		Votacion votacion =  (Votacion) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consulta", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaInicio", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaFin", "Error.empty");

		//System.out.println(votacion.getFechaInicio());
		//System.out.println(votacion.getFechaFin());
		
		if(votacion.getFechaInicio().after(votacion.getFechaFin())){
			errors.rejectValue("fechaInicio", "ErrorFechas", "Error.signup.fechasVotacion");
		}
		
		Date hoy= new Date();
		
		if(votacion.getFechaInicio().before(hoy) || votacion.getFechaInicio().equals(hoy) || votacion.getFechaFin().before(hoy) || votacion.getFechaFin().equals(hoy)  ){
			errors.rejectValue("fechaInicio", "ErrorFechas", "Error.signup.fechasVotacion2");
		}
		
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 3);
		
		if(votacion.getFechaInicio().after(cal.getTime())){
			errors.rejectValue("fechaInicio", "ErrorFechas", "Error.signup.fechInicioMaximoVotacion");
		}
		
		//cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 3);
		
		if(votacion.getFechaFin().after(cal.getTime())){
			errors.rejectValue("fechaInicio", "ErrorFechas", "Error.signup.fechFinMaximoVotacion");
		}
		
		System.out.println(cal.getTime());
		
	}

}
