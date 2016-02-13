package com.jake.swtfx.binding;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.Viewer;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class PropertyTreeContentProvider<T> implements ITreeContentProvider, ListChangeListener<T> {
	private final Callback<T, ObservableList<T>> childrenExtractor;
	private final Callback<T, T> parentExtractor;
	private Viewer viewer;

	public PropertyTreeContentProvider(Callback<T, ObservableList<T>> extractor, Callback<T, T> parentExtractor) {
		this.childrenExtractor = extractor;
		this.parentExtractor = parentExtractor;
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return extractChildren((T)parentElement).toArray();
	}

	private ObservableList<T> extractChildren(T parentElement) {
		return childrenExtractor.call(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		return parentExtractor.call((T)element);
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		if(oldInput != null) {
			Object[] oldArray = (Object[])oldInput;
			T oldRoot = (T)oldArray[0];
			removeListeners(oldRoot);
		}
		if(newInput != null) {
			Object []newArray = (Object[]) newInput;
			T root = (T)newArray[0];
			addListeners(root);
		}
	}

	private void addListeners(T root) {
		ObservableList<T> children = extractChildren(root);
		children.addListener(this);
		for(T child : children) {
			addListeners(child);
		}
	}

	private void removeListeners(T oldRoot) {
		ObservableList<T> children = extractChildren(oldRoot);
		children.removeListener(this);
		for(T child : children) {
			removeListeners(child);
		}
	}

	@Override
	public void onChanged(javafx.collections.ListChangeListener.Change<? extends T> change) {
		viewer.refresh();
		viewer.getControl().getParent().layout();
	}
}
