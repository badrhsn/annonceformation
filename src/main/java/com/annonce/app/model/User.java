package com.annonce.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class User {
	

	@Id
	@Email
	@NotEmpty
	@Column(unique = true)
	private String email;
	@NotEmpty
	private String name;
	@NotEmpty
	private String first_name;
	@NotEmpty
	private String last_name;
	private String address;
	private String city;
	private String country;
	private String tele;
	private String description;
	private String imgUrl;
	@Size(min = 4)
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Formation> formations;
	

    private String roles;

	private int active;

	
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Formation> getFormations() {
		return formations;
	}

	public void setFormations(List<Formation> formations) {
		this.formations = formations;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}
	
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}

	public String getRoles() {
		return roles;
	}

	public void setImgUrl(String imageUrl) {
		// TODO Auto-generated method stub
		this.imgUrl = imageUrl;
	}
	
	
	public User(@Email @NotEmpty String email, @NotEmpty String name, @NotEmpty String first_name,
			@NotEmpty String last_name, String address, String city, String country, String tele, String description,
			String imgUrl, @Size(min = 4) String password, List<Formation> formations,
			String roles, int active) {
		super();
		this.email = email;
		this.name = name;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.tele = tele;
		this.description = description;
		this.imgUrl = imgUrl;
		this.password = password;
		this.formations = formations;
		this.roles = roles;
		this.active = active;
	}

	public List<String> getRoleList(){
		List<String> listeroles=new ArrayList<String>();
		listeroles=Arrays.asList(this.roles.split(","));
		return listeroles;
		}

	public User() {
		
	}


}
