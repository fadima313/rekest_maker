package com.rekest.observableList;

import java.util.List;

import javafx.collections.ObservableList;

public interface IObservableList<T> {

	/**
	 * Return Data of Observable list
	 * @return
	 */
	ObservableList<T> getData();

	/**
	 * Add entity in Observable list
	 * @param entity
	 */
	void add(T entity);

	/**
	 * Delete entity by entity in Observable list
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * Update entity in Observable list
	 * @param entity
	 */
	void update(T entity);

	/**
	 * Delete entity by id in Observable list
	 * @param id
	 */
	void delete(int id);

	/**
	 * Set data in Observable list
	 * @param entities
	 */
	void setData (List<T> entities);

	/**
	 * Clear Data
	 */
	void clear ();

	/**
	 * Add all entities in Observable list
	 * @param entities
	 */
	void addAll(List<Object> entities);

	/**
	 * Refresh Observable list
	 */
	void refresh();
}
