package com.annonce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.annonce.app.model.Formation;
import com.annonce.app.model.Subscription;
import com.annonce.app.model.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	public Subscription findByFormationAndUser(Formation formation, User user);
	
	public List<Subscription> findByUser(User user);
	
}
