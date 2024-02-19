package com.rekest.entities;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
public class Notification {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id_notification")
	private int id;
	
	private String message;
	
	@Column(name="is_read")
	private boolean isRead = false;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@ManyToOne
	private Demande demande;
	
	public Notification() {}
	
	public Notification(String message) {
		this.message = message;
		this.createdAt = new java.util.Date();
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

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreateAt(Date createat) {
		this.createdAt = createat;
	}

	@Override
	public String toString() {
		return "Notification [message=" + message + "createdAt=" + createdAt
				+ "]";
	}
	
	public Demande getDemande() {
		return demande;
	}
	
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	
}
