package com.rekest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produit {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id_produit")
	private int id;
	
	@Column(unique=true)
	private String nom;
	
	private double prix;
	private int quantite;
	private String type;
	private String description;
	
	public Produit() {}
	
	public Produit(String nom) {
		this.nom = nom;
	}
	
	public Produit(String nom ,double prix, int quantite , String type , String description) {
		this(nom);
		this.setPrix(prix);
		this.setQuantite(quantite);
		this.type = type;
		this.description = description;
	}
	
	public Produit(String nom ,double prix, int quantite ) {
		this(nom , prix ,quantite , ""  , "");
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static void copy(Produit produit, Produit entity) {}
	

	
	
}
