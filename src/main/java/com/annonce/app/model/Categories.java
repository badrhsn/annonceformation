package com.annonce.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categories {
	
	@Id
	private Long id;
	private String name;
	private String icon;
	
	public Categories() {
		
	}
	
	public Categories(Long id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
	

}
