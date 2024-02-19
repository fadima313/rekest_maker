package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.employes.Employe;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListEmploye implements IObservableList<Employe> {    
	
    private ObservableList<Employe> employeData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Employe> getData() {
		return employeData;
	}

	@Override
	public void add(Employe entity) {
		employeData.add(entity);
	}

	public void delete(Employe entity) {
		employeData.remove(entity);
	}

	@Override
	public void update(Employe entity) {
		 int index = employeData.lastIndexOf(entity);
	        if (index >= 0) {
	            Employe.copy(employeData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Employe> predicate = person -> (person.getId() == id);
        Optional<Employe> person = employeData.stream().filter(predicate).findFirst();
        if (person != null) {
            employeData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Employe> entities) {
		this.employeData.setAll(entities);
	}

	@Override
	public void clear() {
		this.employeData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Employe) {
	                employeData.add((Employe)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}