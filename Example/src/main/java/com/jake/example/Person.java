package com.jake.example;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Person {
	private final StringProperty nameProperty = new SimpleStringProperty();
	private final IntegerProperty ageProperty = new SimpleIntegerProperty();
	private final ObservableList<Person> children = FXCollections.observableArrayList();
	private Person parent;

	public Person(String name) {
		nameProperty.set(name);
	}
	
	public StringProperty nameProperty() {
		return nameProperty;
	}
	
	public IntegerProperty ageProperty() {
		return ageProperty;
	}
	
	@Override
	public String toString() {
		return nameProperty.get();
	}

	public void addChild(Person child) {
		children.add(child);
	}

	public ObservableList<Person> getChildren() {
		return children;
	}

	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}
}
