package com.jake.example;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;

public class SampleBinder {

	public void bind(SampleView view, SampleModel model) {
		model.nameProperty().bindBidirectional(view.getTextField().textProperty());
		view.getPushButton().focusProperty().addListener((observable, oldValue, newValue) -> {
			model.summaryProperty().set("focus:" + newValue);
		});
		view.getSummaryLabel().textProperty().bind(model.summaryProperty());
		view.getLabel().textProperty().bind(model.nameProperty());
		view.getList().itemsProperty().set(model.listItems());
		view.getListSelectionLabel().textProperty().bind(view.getList().selectedItemProperty());
		view.getPushButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.listItems().add(model.nameProperty().get());
				model.tableItems().add(new Person(model.nameProperty().get()));
				Person child = new Person(model.nameProperty().get());
				model.getRootNode().addChild(child);
				child.setParent(model.getRootNode());
			}
		});
		view.getTable().itemsProperty().set(model.tableItems());
		view.getTableSelectionLabel().textProperty().bind(view.getTable().selectedItemProperty().asString());
		view.getTree().rootProperty().set(model.getRootNode());
		view.getTreeSelectionLabel().textProperty().bind(view.getTree().selectedItemProperty().asString());
	}
}
