package com.rekest.observableList.impl;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.employes.Utilisateur;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListUtilisateur implements IObservableList<Utilisateur> {    
	
    private ObservableList<Utilisateur> utilisateurData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Utilisateur> getData() {
		return utilisateurData;
	}

	@Override
	public void add(Utilisateur entity) {
		utilisateurData.add(entity);
	}

	public void delete(Utilisateur entity) {
		utilisateurData.remove(entity);
	}

	@Override
	public void update(Utilisateur entity) {
		 int index = utilisateurData.lastIndexOf(entity);
	        if (index >= 0) {
	            //Utilisateur.copy(utilisateurData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Utilisateur> predicate = person -> (person.getId() == id);
        Optional<Utilisateur> person = utilisateurData.stream().filter(predicate).findFirst();
        if (person != null) {
            utilisateurData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Utilisateur> entities) {
		this.utilisateurData.setAll(entities);
	}

	@Override
	public void clear() {
		this.utilisateurData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Utilisateur) {
	                utilisateurData.add((Utilisateur)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}