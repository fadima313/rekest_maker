package com.rekest.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Utilisateur;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;


@Entity
public class Demande {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id_demande")
	private int id;

	private String etat;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_At", columnDefinition="TIMESTAMP")
	private Date  createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_At", columnDefinition="TIMESTAMP")
	private Date updatedAt;

	@OneToOne(targetEntity=Produit.class)
	@JoinColumn(name="id_produit")
	private Produit produit;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_demande")
	private List<Note> notes = new ArrayList<>();

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_demande")
	private List<Notification> notifications = new ArrayList<>();

	@ManyToOne
	private Employe employe;
	
	@ManyToOne
	private Utilisateur utilisateur;
	
	@Transient
	private String produitId;
	
	@Transient
	private String nomEmploye;
	
	@Transient
	private String employeId;
	
	@Transient
	private String utilisateurId;
	
	@Transient
	private String nomUtilisateur;

	public Demande() {
		this.createdAt = new java.util.Date();
		this.employe = null;
		this.utilisateur = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		this.updatedAt = new java.util.Date();
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
		this.updatedAt = new java.util.Date();
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
		this.updatedAt = new java.util.Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void addNote(Note note) {
		this.notes.add(note);
		this.updatedAt = new java.util.Date();
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

	public void removeNotification(Notification notification) {
		this.notifications.remove(notification);
	}

	public static void copy(Demande demande, Demande entity) {}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public String getProduitString() {
		return produit!= null? produit.getId() + " - " + produit.getNom() : null;
	}

	public String getEmployeString() {
		return employe!= null? employe.getId() + " - " + employe.getFullName() : null;
	}
	
	public Employe getEmploye() {
		return employe;
	}

	public String getProduitId() {
		return String.valueOf(produit.getId());
	}

	public void setProduitId(String produitId) {
		this.produitId = produitId;
	}

	public String getNomEmploye() {
		return (employe != null) ? employe.getFullName() : null;
	}

	public void setNomEmploye(String nomEmploye) {
		this.nomEmploye = nomEmploye;
	}

	public int getEmployeId() {
		return this.employe == null ? 00 : this.employe.getId();
	}

	public void setEmployeId(String employeId) {
		this.employeId = employeId;
	}

	public int getUtilisateurId() {
		return this.utilisateur.getId();
	}

	public void setUtilisateurId(String utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	public String getNomUtilisateur() {
		return (utilisateur != null) ? utilisateur.getFullName() : "Pas Employé";
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public Produit getProduit() {
		return produit;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	@Override
	public String toString() {
		return "Demande [id=" + id + ", etat=" + etat + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", produit=" + produit + ", employe=" + employe + ", utilisateur=" + utilisateur + ", produitId="
				+ produitId + ", nomEmploye=" + nomEmploye + ", employeId=" + employeId + ", utilisateurId="
				+ utilisateurId + ", nomUtilisateur=" + nomUtilisateur + "]";
	}
	
}
