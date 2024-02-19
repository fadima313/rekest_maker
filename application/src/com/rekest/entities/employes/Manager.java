package com.rekest.entities.employes;

import jakarta.persistence.Entity;

@Entity
public class Manager extends Utilisateur {
	
	public Manager() {
		super();
	}
	
	public Manager(String nom, String prenom, String telephone, String email, String adresse) {
		super(nom, prenom, telephone, email, adresse);
	}
	
	public Manager(String nom, String prenom, String telephone, String email, String adresse,  String login, String password) {
		super(nom, prenom, telephone, email, adresse);
		this.login = login;
		this.password = password;
	}
	
	public Manager(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);		
	}
	
}
