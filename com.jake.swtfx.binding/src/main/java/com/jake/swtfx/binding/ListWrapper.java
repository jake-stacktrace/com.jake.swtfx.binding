package com.jake.swtfx.binding;

import org.eclipse.jface.viewers.ListViewer;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public class ListWrapper<T> extends ControlWrapper {
	private ListViewer listViewer;

	public ListWrapper(ListViewer listViewer) {
		super(listViewer.getList());
		this.listViewer = listViewer;
	}

	public ListProperty<T> itemsProperty() {
		return createItemsProperty(listViewer);
	}
	
	public ObjectProperty<T> selectedItemProperty() {
		return createSelectedItemProperty(listViewer);
	}
}
