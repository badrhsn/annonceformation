package com.annonce.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Subscription {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="formation_id")
	private Formation formation;
	
	@OneToOne
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private User user ;
	
	public Subscription() {
		// TODO Auto-generated constructor stub
	}
	
	public Subscription(Formation formation, User user) {
		super();
		this.formation = formation;
		this.user = user;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public Formation getFormation() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
