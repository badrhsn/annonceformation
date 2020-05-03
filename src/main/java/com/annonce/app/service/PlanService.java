package com.annonce.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annonce.app.model.Formation;
import com.annonce.app.model.PlanFormation;
import com.annonce.app.repository.PlanRepository;

@Service
public class PlanService {
	
	
	@Autowired
	private PlanRepository planRepository;
	
	public void addPlanFormation(PlanFormation plan, Formation formation) {
		
		plan.setFormation(formation);
		planRepository.save(plan);
	}
	
	public List<PlanFormation>  findPlanFormation(Formation formation){
		
		return planRepository.findByFormation(formation);
	}

	public Optional<PlanFormation> findPlanById(Long id) {
		// TODO Auto-generated method stub
		return planRepository.findById(id);
	}
	
	public void updatePlan(PlanFormation plan) {
		planRepository.save(plan);
	}

	public void deletePlan(Long id) {
		planRepository.deleteById(id);
	}
}
