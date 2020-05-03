package com.annonce.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annonce.app.model.Formation;
import com.annonce.app.model.Subscription;
import com.annonce.app.model.User;
import com.annonce.app.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	public void addSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}
	
	public Subscription findByFormationIdAndUSer(Formation formation, User user) {
		return subscriptionRepository.findByFormationAndUser(formation, user);
	}
	
	public List<Subscription> findByUser(User user) {
		return subscriptionRepository.findByUser(user);
	}
	
	public void deleteSubscriptionById(long sub_id) {
		subscriptionRepository.deleteById(sub_id);
	}
	
	public boolean isSubscriptionExist(Formation formation, User user) {
		return  subscriptionRepository.findByFormationAndUser(formation, user) != null;
	}
}
