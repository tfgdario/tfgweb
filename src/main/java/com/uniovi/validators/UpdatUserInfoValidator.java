package com.uniovi.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;

@Component
public class UpdatUserInfoValidator implements Validator {

	// @Autowired
	// private VotacionService votacionService;

	@Override
	public boolean supports(Class<?> clazz) {

		return User.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

		
		User user =  (User) target;
		System.out.println(user);
        Pattern patternPass = Pattern.compile("[a-zA-Z0-9]*");
        
        Matcher specialMatcher = patternPass.matcher(user.getPasswordConfirm());
		
		boolean hasMayus=user.getPasswordConfirm().matches(".*[A-Z].*");
		boolean hasSpecial=!specialMatcher.matches();
		boolean passLength=user.getPasswordConfirm().length()>=8 && user.getPasswordConfirm().length()<=16;
		
		if(user.getPasswordConfirm()!=null && user.getPassword()!=null && !user.getPasswordConfirm().isEmpty() && !user.getPassword().isEmpty()) {
		if(!hasMayus || !hasSpecial || !passLength) {
			errors.rejectValue("password", "ErrorPassword", "Error.update.passwordRequirements");
		}}
		
		if(user.getPasswordConfirm() != null && !user.getPasswordConfirm().isEmpty()
				&& user.getPassword() != null && !user.getPassword().isEmpty()
				&& !user.getPassword().equals(user.getPasswordConfirm())){
			errors.rejectValue("password", "ErrorPassword", "Error.update.password");
		}
		
		String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
	
	Matcher matcher = pattern.matcher(user.getEmail());
	
	if(!matcher.matches()) {
		//errors.rejectValue("email", "Error.signup.email.error.forma");
		errors.rejectValue("email", "ErrorEmail", "Error.signup.email.error.forma");
	}
	
	if (user.getCp().isEmpty()||!(user.getCp().length()==5)||!user.getCp().matches("[0-9]+")) {	
		//errors.rejectValue("cp", "Error.signup.cp");
		errors.rejectValue("cp", "CpVacio", "Error.signup.cp");
	}
		
//		if (votacion.getFechaInicio() == null ) {
//			errors.rejectValue("fechaInicio", "Error.empty");
//		}
//		
//		if (votacion.getFechaFin() == null) {
//			errors.rejectValue("fechaFin", "Error.empty");
//		}
		
//		if(votacion.isForAll() && votacion.getFile().exists()) {
//			errors.rejectValue("censo", "Error.censo");
//		}		

	}

}
