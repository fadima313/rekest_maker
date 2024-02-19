package com.rekest.notificationmanager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.rekest.dao.IDao;
import com.rekest.dao.impl.HibernateDao;
import com.rekest.entities.Demande;
import com.rekest.entities.Notification;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.exeptions.DAOException;
import com.rekest.notificationmanager.INotificationManager;

public class NotificationManager implements INotificationManager {
	public final static Logger logger = LogManager.getLogger(HibernateDao.class);
	private IDao dao = HibernateDao.getCurrentInstance();
	
	
	@Override
	public boolean createNotification(Utilisateur utilisateur, Demande demande, String message) throws DAOException {
		
		Notification notification = new Notification(message);
		notification.setDemande(demande);
		dao.save(notification);
		
		utilisateur.addNotification(notification);		
		dao.update(utilisateur);
		
		demande.addNotification(notification);
		dao.update(demande);
		
		return true;
	}

	@Override
	public boolean isRead(Notification notification) throws DAOException {
		notification.setRead(true);
		dao.update(notification);
		return true;
	}

	@Override
	public boolean deleteNotification(Notification notification, Utilisateur utilisateur, Demande demande)
			throws DAOException {
		utilisateur.removeNotification(notification);
		dao.update(utilisateur);
		demande.removeNotification(notification);
		dao.update(demande);
		dao.delete(notification);
		return true;
	}


		
}
