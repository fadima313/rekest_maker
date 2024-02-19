package com.rekest.entities.employes;

import java.util.ArrayList;
import java.util.List;

import com.rekest.entities.Demande;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Gestionnaire extends Utilisateur {
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_gestionnaire")
	private List<Demande> demandes_assignees = new ArrayList<>();

	public Gestionnaire() {
		super();
	}
	
	public Gestionnaire(String nom, String prenom, String telephone, String email, String adresse) {
		super(nom, prenom, telephone, email, adresse);
	}
	
	public Gestionnaire(String nom, String prenom, String telephone, String email, String adresse,  String login, String password) {
		super(nom, prenom, telephone, email, adresse);
		this.login = login;
		this.password = password;
	}
	
	public Gestionnaire(String nom, String prenom, String login, String password) {
		super(nom, prenom, login, password);		
	}
}
