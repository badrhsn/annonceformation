package com.annonce.app.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annonce.app.model.Formation;
import com.annonce.app.model.User;
import com.annonce.app.repository.FormationRepository;

@Service
public class FormationService {
	
	@Autowired
	private FormationRepository formationRepository;
	
	public void addFormation(Formation formation, User user) {
		formation.setUser(user);
		formationRepository.save(formation);
	}
	
	public List<Formation>  findUserFormation(User user){
		
		return formationRepository.findByUser(user);
	}
	
	public List<Formation>  findAllFormation(){
		
		return formationRepository.findAll();
	}
	
	public Formation findFormation(Long id) {
		
		return formationRepository.getOne(id);
	}
	
	public void deleteFormation(Long formation_id) {
		formationRepository.deleteById(formation_id);
	}
	
	public void updateFormation(Formation formation) {
		formationRepository.save(formation);
	}
	
	public List<Formation> findByTiltleLike(String search) {
		return formationRepository.findBytitleLike(search);
	}
	public List<Formation> findByCategorie(String categorie){
		// TODO Auto-generated method stub
		return formationRepository.findByCategorie(categorie);
	}
	
	
}
