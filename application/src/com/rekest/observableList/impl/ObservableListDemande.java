package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Demande;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListDemande implements IObservableList<Demande> {    

	private ObservableList<Demande> demandeData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Demande> getData() {
		return demandeData;
	}

	@Override
	public void add(Demande entity) {
		demandeData.add(entity);
	}

	public void delete(Demande entity) {
		demandeData.remove(entity);
	}

	@Override
	public void update(Demande entity) {
		int index = demandeData.lastIndexOf(entity);
		if (index >= 0) {
			Demande.copy(demandeData.get(index), entity);            
		}
	}

	@Override
	public void delete(int id) {
		Predicate<Demande> predicate = person -> (person.getId() == id);
		Optional<Demande> person = demandeData.stream().filter(predicate).findFirst();
		if (person != null) {
			demandeData.remove(person.get());
		}
	}

	@Override
	public void setData(List<Demande> entities) {
		this.demandeData.setAll(entities);
	}

	@Override
	public void clear() {
		this.demandeData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Demande) {
	            	demandeData.add((Demande)obj);
	            }
	        }
	}
	
	public void addAllVersion2(List<Demande> demandes) {
		for (Demande demande : demandes) {
			demandeData.add(demande);
		}
	}

	@Override
	public void refresh() {}

}