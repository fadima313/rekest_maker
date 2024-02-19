package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Notification;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class ObservableListNotification implements IObservableList<Notification> {    

	
    private ObservableList<Notification> notificationData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Notification> getData() {
		return notificationData;
	}

	@Override
	public void add(Notification entity) {
		notificationData.add(entity);
	}

	public void delete(Notification entity) {
		notificationData.remove(entity);
	}

	@Override
	public void update(Notification entity) {
		 int index = notificationData.lastIndexOf(entity);
	        if (index >= 0) {
	            //Notification.copy(notificationData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Notification> predicate = person -> (person.getId() == id);
        Optional<Notification> person = notificationData.stream().filter(predicate).findFirst();
        if (person != null) {
            notificationData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Notification> entities) {
		this.notificationData.setAll(entities);
	}

	@Override
	public void clear() {
		this.notificationData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Notification) {

	                notificationData.add((Notification)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

	
}