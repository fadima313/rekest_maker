package com.rekest.observableList.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Role;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListRole implements IObservableList<Role> {    
	
    private ObservableList<Role> roleData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Role> getData() {
		return roleData;
	}

	@Override
	public void add(Role entity) {
		roleData.add(entity);
	}

	public void delete(Role entity) {
		roleData.remove(entity);
	}

	@Override
	public void update(Role entity) {
		 int index = roleData.lastIndexOf(entity);
	        if (index >= 0) {
	            Role.copy(roleData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Role> predicate = person -> (person.getId() == id);
        Optional<Role> person = roleData.stream().filter(predicate).findFirst();
        if (person != null) {
            roleData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Role> entities) {
		this.roleData.setAll(entities);
	}

	@Override
	public void clear() {
		this.roleData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Role) {
	                roleData.add((Role)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

}