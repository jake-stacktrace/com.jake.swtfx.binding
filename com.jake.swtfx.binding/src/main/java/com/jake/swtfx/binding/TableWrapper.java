package com.jake.swtfx.binding;

import org.eclipse.jface.viewers.TableViewer;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public class TableWrapper<T> extends ControlWrapper {
	private final TableViewer tableViewer;

	public TableWrapper(TableViewer tableViewer) {
		super(tableViewer.getTable());
		this.tableViewer = tableViewer;
	}

	public ListProperty<T> itemsProperty() {
		return createItemsProperty(tableViewer);
	}
	
	public ObjectProperty<T> selectedItemProperty() {
		return createSelectedItemProperty(tableViewer);
	}
}
