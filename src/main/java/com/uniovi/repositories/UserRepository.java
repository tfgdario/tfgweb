package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
		
	User findById(long id);
	
	User findByNumDocumento(String numDocumento);
	
	@Query("SELECT u FROM User u WHERE u.role = 'ROLE_USER' ORDER BY u.id ASC ")
	List<User> getAllVotantes();
	
}
