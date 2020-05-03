package com.annonce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.annonce.app.model.Formation;
import com.annonce.app.model.PlanFormation;

public interface PlanRepository  extends JpaRepository<PlanFormation, Long>{

 List<PlanFormation> findByFormation(Formation formation);
}
