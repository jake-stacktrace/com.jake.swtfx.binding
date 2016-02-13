package com.jake.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SampleModel {
	private final StringProperty nameProperty = new SimpleStringProperty();
	private final StringProperty buttonNameProperty = new SimpleStringProperty();
	private final ObservableList<String> listItems = FXCollections.observableArrayList("a", "b", "c");
	private ObservableList<Person> tableItems = FXCollections.observableArrayList();
	private Person rootPerson = new Person("Root");

	public StringProperty nameProperty() {
		return nameProperty;
	}
	
	public void setName(String name) {
		nameProperty.set(name);
	}

	public StringProperty summaryProperty() {
		return buttonNameProperty;
	}

	public ObservableList<String> listItems() {
		return listItems;
	}
	
	public ObservableList<Person> tableItems() {
		return tableItems;
	}
	
	public Person getRootNode() {
		return rootPerson;
	}
}
