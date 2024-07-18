package com.uniovi.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@Component
public class SignUpFormValidator  implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return User.class.equals(clazz);
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		User user =  (User) target;
		String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		
		Matcher matcher = pattern.matcher(user.getEmail());
		
		if(!matcher.matches()) {
			//errors.rejectValue("email", "Error.signup.email.error.forma");
			errors.rejectValue("email", "ErrorEmail", "Error.signup.email.error.forma");
		}
		
//		if (userService.getUserByEmail(user.getEmail()) != null) {
//			errors.rejectValue("email", "Error.signup.email.duplicate");
//		}
		
		if (user.getNacionalidad().equals("default")) {
			//errors.rejectValue("numDocumento", "Error.signup.numDocumento.duplicate");
			errors.rejectValue("nacionalidad", "NacionalidadIncompleta", "Error.signup.nacionalidad");
		}
		
		if (userService.getUserByNumDocumento(user.getNumDocumento()) != null) {
			//errors.rejectValue("numDocumento", "Error.signup.numDocumento.duplicate");
			errors.rejectValue("numDocumento", "DocumentoDuplicado", "Error.signup.numDocumento.duplicate");
		}
		
		if(!userService.validateDocumentacion(user)) {
			//errors.rejectValue("numDocumento", "Error.signup.numDocumento.invalid");
			errors.rejectValue("numDocumento", "DocumentoInvalido", "Error.signup.numDocumento.invalid");
		}
		
		if ((user.getNombre().length() < 3 || user.getNombre().length() > 30)) {
			//errors.rejectValue("nombre", "Error.signup.name.length");
			errors.rejectValue("nombre", "LongitudNombre", "Error.signup.name.length");
		}
		

		if(!hasNoNumbers(user.getNombre())){//.matches(".*[a-zA-Z]+.*")) {
			errors.rejectValue("nombre", "NombreNoLetras", "Error.signup.name.noletras" );//"Error.signup.name.notOnlyLetter");
		}
		 
		if (user.getApellidos().length() < 3 || user.getApellidos().length() > 30) {
			//errors.rejectValue("apellidos", "Error.signup.lastName.length");
			errors.rejectValue("apellidos", "LongitudApellidos", "Error.signup.lastName.length");
		}
		
		if(!hasNoNumbers(user.getApellidos())){//.matches("^[a-zA-Z]+$")) {
			errors.rejectValue("apellidos", "ApellidosNoLetras","Error.signup.lastName.noletras" );//"Error.signup.lastName.notOnlyLetter");
		}
		
		if (user.getCalle().isEmpty()) {
			//errors.rejectValue("calle", "Error.signup.calle");
			errors.rejectValue("calle", "CalleVacia", "Error.signup.calle");
		}
		
		if (user.getPoblacion().isEmpty()) {		
			//errors.rejectValue("poblacion", "Error.signup.poblacion");
			errors.rejectValue("poblacion", "PablacionVacia", "Error.signup.poblacion");
		}
		
		if (user.getCp().isEmpty()||!(user.getCp().length()==5)||!user.getCp().matches("[0-9]+")) {	
			//errors.rejectValue("cp", "Error.signup.cp");
			errors.rejectValue("cp", "CpVacio", "Error.signup.cp");
		}
		
		if (user.getProvincia().isEmpty()) {	
			//errors.rejectValue("provincia", "Error.signup.provincia");
			errors.rejectValue("provincia", "ProvinciaVacia", "Error.signup.provincia");
		}
		
		if (user.getPais().isEmpty()) {
			//errors.rejectValue("pais", "Error.signup.pais");
			errors.rejectValue("pais", "PaisVacio", "Error.signup.pais");
		}
		
		if (user.getNacionalidad().isEmpty()) {
			//errors.rejectValue("nacionalidad", "Error.signup.nacionalidad");
			errors.rejectValue("nacionalidad", "NacionalidadVacio", "Error.signup.nacionalidad");
		}
		
		if(!userService.comprobarEdad(user.getFechaNacimiento())) {
			errors.rejectValue("fechaNacimiento", "FechaErronea", "Error.signup.fechaNacimiento");
		}
				
//		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
//			errors.rejectValue("password", "Error.signup.password.length");
//		}
//		if (!user.getPasswordConfirm().equals(user.getPassword())) {
//			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
//		}
//		
	}

	public static boolean hasNoNumbers(String s){
		  for(int i=0;i<s.length();i++){
		    char ch = s.charAt(i);
		    
		    if (Character.isDigit(ch)) {
		    	return false;
		    }
		    
		  }
		  return true;
		}

//	private boolean validateDocumentacion(User user) {
//		
//		boolean isValid=false;
//		String tipoDocumento=user.getTipoDocumento();
//		String numDoc = user.getNumDocumento();
//		
//		String dniRegexp = "\\d{8}[A-HJ-NP-TV-Z]";
//		String nifRegex = "\\[XYZ]{7}[A-HJ-NP-TV-Z]";
//		
//		String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
//		int resto = 0;
//		int num = Integer.parseInt(numDoc.substring(0,8));
//		String letra = "";
//		
//		switch (tipoDocumento) {
//		case "DNI":
//			
//			num = Integer.parseInt(numDoc.substring(0,8));
//			resto = num % 23;
//			letra = asignacionLetra[resto];
//			
//			isValid=Pattern.matches(dniRegexp,numDoc)&&
//					letra.equals(numDoc.substring(8).toUpperCase());
//			
//			break;
//
//		case "NIE":
//			
//			letra = "";
//			String letraI=numDoc.substring(0,1).toUpperCase();
//			String auxNum = null;
//			
//			if(letraI.equals("X")) {
//				auxNum = "0" + numDoc.substring(1,9);
//			} else if(letraI.equals("Y")) {
//				auxNum = "1" + numDoc.substring(1,9);
//			} else if(letraI.equals("Z")) {
//				auxNum = "2" + numDoc.substring(1,9);
//			}
//				
//			num = Integer.parseInt(auxNum.substring(0,8));
//			
//			isValid=Pattern.matches(nifRegex,numDoc)&&
//					(letra.equals(numDoc.substring(8).toUpperCase()));
//			
//			break;
//			
//		default:
//			isValid=false;
//			break;
//		}
//		
//		return isValid;
//	}

}