package com.annonce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.annonce.app.model.Formation;
import com.annonce.app.model.User;

public interface FormationRepository  extends JpaRepository<Formation, Long>{

	List<Formation> findByUser(User user);

	List<Formation> findBytitleLike(String search);

	List<Formation> findByCategorie(String categorie); 
	
	
}
