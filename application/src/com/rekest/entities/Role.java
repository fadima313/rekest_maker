package com.rekest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id_role")
	private int id;
	
	@Column(unique=true)
	private String intitule;
	
	public Role() {}
	
	public Role(String intitule) {
		this.intitule = intitule;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIntitule() {
		return intitule;
	}
	
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public static void copy(Role role, Role entity) {}
}
