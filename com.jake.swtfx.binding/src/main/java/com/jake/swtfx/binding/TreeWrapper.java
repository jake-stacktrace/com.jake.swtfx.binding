package com.jake.swtfx.binding;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

public class TreeWrapper<T> extends ControlWrapper {
	private TreeViewer treeViewer;

	public TreeWrapper(TreeViewer treeViewer) {
		super(treeViewer.getTree());
		this.treeViewer = treeViewer;
	}

	public ObjectProperty<T> rootProperty() {
		ObjectProperty<T> rootProperty = new SimpleObjectProperty<T>((T) treeViewer.getInput());
		ChangeListener<T> changeListener = (observable, oldValue, newValue) -> {
			Display.getDefault().asyncExec(() -> {
				treeViewer.setInput(new Object[] {newValue});
				treeViewer.refresh();
				treeViewer.getControl().getParent().layout();
			});
		};
		rootProperty.addListener(changeListener);
		addDisposeRunnable(() -> rootProperty.removeListener(changeListener));
		return rootProperty;
	}

	public ObjectProperty<T> selectedItemProperty() {
		return createSelectedItemProperty(treeViewer);
	}
}
