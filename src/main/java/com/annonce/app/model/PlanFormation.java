package com.annonce.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;


@Entity
public class PlanFormation {
	
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty
	private String p_title;
	@NotEmpty
	private String description;
	@NotEmpty
	private String price;
	@NotEmpty
	private String duration;
	@ManyToOne
	@JoinColumn(name="formation_id")
	private Formation formation;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Formation getFormation() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	


	
	public PlanFormation(@NotEmpty String p_title, @NotEmpty String description, @NotEmpty String price,
			@NotEmpty String duration) {
		super();
		this.p_title = p_title;
		this.description = description;
		this.price = price;
		this.duration = duration;
	}
	
	
	public PlanFormation(@NotEmpty String p_title, @NotEmpty String description, @NotEmpty String price,
			@NotEmpty String duration, Formation formation) {
		super();
		this.p_title = p_title;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.formation = formation;
	}
	public PlanFormation() {
		
	}
	

}
