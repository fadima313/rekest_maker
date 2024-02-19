package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Produit;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListProduit implements IObservableList<Produit> {    
	
    private ObservableList<Produit> produitData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Produit> getData() {
		return produitData;
	}

	@Override
	public void add(Produit entity) {
		produitData.add(entity);
	}

	public void delete(Produit entity) {
		produitData.remove(entity);
	}

	@Override
	public void update(Produit entity) {
		 int index = produitData.lastIndexOf(entity);
	        if (index >= 0) {
	            Produit.copy(produitData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Produit> predicate = person -> (person.getId() == id);
        Optional<Produit> person = produitData.stream().filter(predicate).findFirst();
        if (person != null) {
            produitData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Produit> entities) {
		this.produitData.setAll(entities);
	}

	@Override
	public void clear() {
		this.produitData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Produit) {
	                produitData.add((Produit)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}