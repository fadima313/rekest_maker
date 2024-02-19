package com.rekest.observableList.impl;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rekest.entities.Note;
import com.rekest.observableList.IObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListNote implements IObservableList<Note> {    
	
    private ObservableList<Note> noteData = FXCollections.observableArrayList();

	@Override
	public ObservableList<Note> getData() {
		return noteData;
	}

	@Override
	public void add(Note entity) {
		noteData.add(entity);
	}

	public void delete(Note entity) {
		noteData.remove(entity);
	}

	@Override
	public void update(Note entity) {
		 int index = noteData.lastIndexOf(entity);
	        if (index >= 0) {
	            //Note.copy(noteData.get(index), entity);            
	        }
	}

	@Override
	public void delete(int id) {
		Predicate<Note> predicate = person -> (person.getId() == id);
        Optional<Note> person = noteData.stream().filter(predicate).findFirst();
        if (person != null) {
            noteData.remove(person.get());
        }
	}

	@Override
	public void setData(List<Note> entities) {
		this.noteData.setAll(entities);
	}

	@Override
	public void clear() {
		this.noteData.clear();
	}

	@Override
	public void addAll(List<Object> entities) {
		 for(Object obj : entities) {
	            if (obj instanceof Note) {
	                noteData.add((Note)obj);
	            }
	        }
	}

	@Override
	public void refresh() {}

	public void addAllVersion2(List<Note> notes) {
		for(Note note : notes) {
                noteData.add(note);
        }
	}

}