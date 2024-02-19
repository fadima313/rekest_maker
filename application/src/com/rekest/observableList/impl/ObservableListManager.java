package com.rekest.observableList.impl;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.employes.Manager;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListManager implements IObservableList<Manager> {    
	
    private ObservableList<Manager> managerData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Manager> getData() {
		return managerData;
	}

	@Override
	public void add(Manager entity) {
		managerData.add(entity);
	}

	public void delete(Manager entity) {
		managerData.remove(entity);
	}

	@Override
	public void update(Manager entity) {
		 int index = managerData.lastIndexOf(entity);
	        if (index >= 0) {
	            Manager.copy(managerData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Manager> predicate = person -> (person.getId() == id);
        Optional<Manager> person = managerData.stream().filter(predicate).findFirst();
        if (person != null) {
            managerData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Manager> entities) {
		this.managerData.setAll(entities);
	}

	@Override
	public void clear() {
		this.managerData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Manager) {
	                managerData.add((Manager)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}