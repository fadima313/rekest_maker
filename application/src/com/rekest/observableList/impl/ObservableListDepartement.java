package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Departement;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListDepartement implements IObservableList<Departement> {    
	
    private ObservableList<Departement> departementData = FXCollections.observableArrayList();

    public ObservableListDepartement() {}
    
	@Override
	public ObservableList<Departement> getData() {
		return departementData;
	}

	@Override
	public void add(Departement entity) {
		departementData.add(entity);
	}

	public void delete(Departement entity) {
		departementData.remove(entity);
	}

	@Override
	public void update(Departement entity) {
		 int index = departementData.lastIndexOf(entity);
	        if (index >= 0) {
	            Departement.copy(departementData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Departement> predicate = person -> (person.getId() == id);
        Optional<Departement> person = departementData.stream().filter(predicate).findFirst();
        if (person != null) {
            departementData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Departement> entities) {
		this.departementData.setAll(entities);
	}

	@Override
	public void clear() {
		this.departementData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Departement) {
	                departementData.add((Departement)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}
	
	public void displayDepartementData() {
		
		if (departementData.isEmpty())
				System.out.println("Liste des observables est vide");
		else {
			for (Departement departement : departementData) {
				System.out.println(departement);
			}
		}
		
		
	}

}