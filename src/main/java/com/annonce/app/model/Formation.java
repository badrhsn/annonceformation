package com.annonce.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;



@Entity
public class Formation {

	
	
	@Id
	@GeneratedValue
	private Long formation_id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String subtitle;
	@NotEmpty
	private String price;
	@NotEmpty
	private String language;
	@NotEmpty
	private String date_depart;
	@NotEmpty
	private String limit_student;
	@NotEmpty
	private String categorie;
	@NotEmpty 
	private String duration;
	@NotEmpty 
	private String place;
	@Column
	private String[] prerequis;
	private String current_student;
	private int active;
	private String creation_date;
	private String img;
	@NotEmpty
	@Column(length=1000)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="USER_EMAIL")
	private User user;
	@OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
	private List<PlanFormation> plans;
	
	@OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
	private List<Subscription> subscriptions;
	
	
	public Long getId() {
		return formation_id;
	}
	
	
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public String[] getPrerequis() {
		return prerequis;
	}
	
	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public void setPrerequis(String[] prerequis) {
		this.prerequis = prerequis;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public String getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<PlanFormation> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanFormation> plans) {
		this.plans = plans;
	}

	
	public Long getFormation_id() {
		return formation_id;
	}
	public void setFormation_id(Long formation_id) {
		this.formation_id = formation_id;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDate_depart() {
		return date_depart;
	}
	public void setDate_depart(String date_depart) {
		this.date_depart = date_depart;
	}
	public String getLimit_student() {
		return limit_student;
	}
	public void setLimit_student(String limit_student) {
		this.limit_student = limit_student;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCurrent_student() {
		return current_student;
	}
	public void setCurrent_student(String current_student) {
		this.current_student = current_student;
	}
	
	
	public Formation(@NotEmpty String title, @NotEmpty String subtitle, @NotEmpty String price,
			@NotEmpty String language, @NotEmpty String date_depart, @NotEmpty String limit_student,
			@NotEmpty String categorie, @NotEmpty String duration, @NotEmpty String place,
			@NotEmpty String description, User user) {
		this.title = title;
		this.subtitle = subtitle;
		this.price = price;
		this.language = language;
		this.date_depart = date_depart;
		this.limit_student = limit_student;
		this.categorie = categorie;
		this.duration = duration;
		this.place = place;
		this.description = description;
		this.user = user;
	}
	public Formation(@NotEmpty String title, @NotEmpty String subtitle, @NotEmpty String price,
			@NotEmpty String language, @NotEmpty String date_depart, @NotEmpty String limit_student,
			@NotEmpty String categorie, @NotEmpty String duration, @NotEmpty String place,
			@NotEmpty String description) {
		this.title = title;
		this.subtitle = subtitle;
		this.price = price;
		this.language = language;
		this.date_depart = date_depart;
		this.limit_student = limit_student;
		this.categorie = categorie;
		this.duration = duration;
		this.place = place;
		this.description = description;
	}
		public Formation( @NotEmpty String title, @NotEmpty String subtitle, @NotEmpty String price,
			@NotEmpty String language, @NotEmpty String date_depart, @NotEmpty String limit_student,
			@NotEmpty String categorie, @NotEmpty String duration, @NotEmpty String place, String[] prerequis,
			String current_student, @NotEmpty String description, User user, List<PlanFormation> plans) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.price = price;
		this.language = language;
		this.date_depart = date_depart;
		this.limit_student = limit_student;
		this.categorie = categorie;
		this.duration = duration;
		this.place = place;
		this.prerequis = prerequis;
		this.current_student = current_student;
		this.description = description;
		this.user = user;
		this.plans = plans;
	}
		
	public Formation(Long formation_id, @NotEmpty String title, @NotEmpty String subtitle, @NotEmpty String price,
				@NotEmpty String language, @NotEmpty String date_depart, @NotEmpty String limit_student,
				@NotEmpty String categorie, @NotEmpty String duration, @NotEmpty String place, String[] prerequis,
				String current_student, int active, String creation_date, String img, @NotEmpty String description,
				User user, List<PlanFormation> plans, List<Subscription> subscriptions) {
			super();
			this.formation_id = formation_id;
			this.title = title;
			this.subtitle = subtitle;
			this.price = price;
			this.language = language;
			this.date_depart = date_depart;
			this.limit_student = limit_student;
			this.categorie = categorie;
			this.duration = duration;
			this.place = place;
			this.prerequis = prerequis;
			this.current_student = current_student;
			this.active = active;
			this.creation_date = creation_date;
			this.img = img;
			this.description = description;
			this.user = user;
			this.plans = plans;
			this.subscriptions = subscriptions;
		}


	public Formation() {
		
	}
	
}
