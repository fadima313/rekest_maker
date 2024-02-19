package com.rekest.observableList.impl;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.employes.ChefDepartement;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListChefDepartement implements IObservableList<ChefDepartement> {    
	
    private ObservableList<ChefDepartement> chefDepartementData = FXCollections.observableArrayList();

	@Override
	public ObservableList<ChefDepartement> getData() {
		return chefDepartementData;
	}

	@Override
	public void add(ChefDepartement entity) {
		chefDepartementData.add(entity);
	}

	public void delete(ChefDepartement entity) {
		chefDepartementData.remove(entity);
	}

	@Override
	public void update(ChefDepartement entity) {
		 int index = chefDepartementData.lastIndexOf(entity);
	        if (index >= 0) {
	            //ChefDepartement.copy(chefDepartementData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<ChefDepartement> predicate = person -> (person.getId() == id);
        Optional<ChefDepartement> person = chefDepartementData.stream().filter(predicate).findFirst();
        if (person != null) {
            chefDepartementData.remove(person.get());
        }
	}

	@Override
	public void setData(List<ChefDepartement> entities) {
		this.chefDepartementData.setAll(entities);
	}

	@Override
	public void clear() {
		this.chefDepartementData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof ChefDepartement) {
	                chefDepartementData.add((ChefDepartement)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}