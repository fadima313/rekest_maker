package com.rekest.notificationmanager;

import com.rekest.entities.Demande;
import com.rekest.entities.Notification;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.exeptions.DAOException;

public interface INotificationManager {

	public boolean createNotification (Utilisateur utilisateur, Demande demande, String message) throws DAOException;
	public boolean isRead(Notification notification) throws DAOException;
	boolean deleteNotification(Notification notification, Utilisateur utilisateur, Demande demande) throws DAOException;
	
}
