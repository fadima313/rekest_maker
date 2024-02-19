package com.rekest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Note {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id_note")
	private int id;
	
	@ManyToOne
	private Demande demande;
	
	private String message;
	
	@Transient
	private String emetteur;
	
	public Note() {}	
	
	public Note(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	
	public Demande getDemande() {
		return demande;
	}

	@Override
	public String toString() {
		return "Note" +  this.getMessage()
				+ "]";
	}
	
	public String getEmetteur() {
		return  demande.getUtilisateur() != null ? demande.getUtilisateur() .getFullName() : null;
	}
	
	public void setEmetteur(String emetteur) {
		this.emetteur = emetteur;
	}
}
