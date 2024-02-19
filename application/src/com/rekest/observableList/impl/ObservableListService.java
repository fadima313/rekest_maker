package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Service;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListService implements IObservableList<Service> {    
	
    private ObservableList<Service> serviceData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Service> getData() {
		return serviceData;
	}

	@Override
	public void add(Service entity) {
		serviceData.add(entity);
	}

	public void delete(Service entity) {
		serviceData.remove(entity);
	}

	@Override
	public void update(Service entity) {
		 int index = serviceData.lastIndexOf(entity);
	        if (index >= 0) {
	            Service.copy(serviceData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Service> predicate = person -> (person.getId() == id);
        Optional<Service> person = serviceData.stream().filter(predicate).findFirst();
        if (person != null) {
            serviceData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Service> entities) {
		this.serviceData.setAll(entities);
	}

	@Override
	public void clear() {
		this.serviceData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Service) {
	                serviceData.add((Service)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}