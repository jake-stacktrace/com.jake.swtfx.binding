package com.jake.swtfx.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class ControlWrapper {

	private Control control;
	private Map<RGB,Color> colors;
	private List<Runnable> disposeRunnables;

	public ControlWrapper(Control control) {
		this.control = control;
	}
	
	public BooleanProperty visibleProperty() {
		SimpleBooleanProperty visibleProperty = new SimpleBooleanProperty(control.isVisible());
		ChangeListener<Boolean> changeListener = (observable, oldVisible, newVisible) -> Display.getDefault().asyncExec(() -> {
            control.setVisible(newVisible);
        });
		visibleProperty.addListener(changeListener);
		addDisposeRunnable(() -> visibleProperty.removeListener(changeListener));
		return visibleProperty;		
	}

	protected void addDisposeRunnable(Runnable runnable) {
		if(disposeRunnables == null) {
			disposeRunnables = new ArrayList<>();
			control.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					control.removeDisposeListener(this);
					for(Runnable runnable : disposeRunnables) {
						runnable.run();						
					}
				}
			});
		}
		disposeRunnables.add(runnable);
	}

	public BooleanProperty disabledProperty() {
		SimpleBooleanProperty disabledProperty = new SimpleBooleanProperty(!control.isEnabled());
		ChangeListener<Boolean> changeListener = (observable, oldDisabled, newDisabled) -> Display.getDefault().asyncExec(() -> {
            control.setEnabled(!newDisabled);
        });
		disabledProperty.addListener(changeListener);
		addDisposeRunnable(() -> disabledProperty.removeListener(changeListener));
		return disabledProperty;
	}
	
	public ReadOnlyBooleanProperty focusProperty() {
		SimpleBooleanProperty focusProperty = new SimpleBooleanProperty(control.isFocusControl());
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				focusProperty.set(false);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				focusProperty.set(true);
			}
		};
		control.addFocusListener(focusListener);
		addDisposeRunnable(() -> control.removeFocusListener(focusListener));
		return focusProperty;
	}
	
	public ObjectProperty<RGB> backgroundColorProperty() {
		SimpleObjectProperty<RGB> backgroundColorProperty = new SimpleObjectProperty<>(control.getBackground().getRGB());
		ChangeListener<RGB> changeListener = (observable,oldValue,newValue) -> {
			Display.getDefault().asyncExec(() -> {				
				control.setBackground(getColor(newValue));
			});
		};
		backgroundColorProperty.addListener(changeListener);
		addDisposeRunnable(() -> backgroundColorProperty.removeListener(changeListener));
		return backgroundColorProperty;
	}
	
	public ObjectProperty<RGB> foregroundColorProperty() {
		SimpleObjectProperty<RGB> foregroundColorProperty = new SimpleObjectProperty<>(control.getForeground().getRGB());
		ChangeListener<RGB> changeListener = (observable,oldValue,newValue) -> {
			Display.getDefault().asyncExec(() -> {				
				control.setForeground(getColor(newValue));
			});
		};
		foregroundColorProperty.addListener(changeListener);
		addDisposeRunnable(() -> foregroundColorProperty.removeListener(changeListener));
		return foregroundColorProperty;
	}
	
    private Color getColor(RGB rgb) {
    	if(colors == null) {
    		colors = new HashMap<>();
    		addDisposeRunnable(() -> colors.values().forEach(color -> color.dispose()));
    	}
        Color color = colors.get(rgb);
        if (color == null) {
            Display display = Display.getDefault();
            color = new Color(display, rgb);
            colors.put(rgb, color);
        }
        return color;
    }
    
	protected <T> ListProperty<T> createItemsProperty(StructuredViewer viewer) {
		ListProperty<T> itemsProperty = new SimpleListProperty<T>((ObservableList<T>)viewer.getInput());
		ChangeListener<? super ObservableList<T>> changeListener = (observable,oldValue,newValue) -> {
			Display.getDefault().asyncExec(() -> {
				viewer.setInput(itemsProperty);
				viewer.refresh();
				viewer.getControl().getParent().layout();
			});
		};
		itemsProperty.addListener(changeListener);
		addDisposeRunnable(() -> itemsProperty.removeListener(changeListener));
		return itemsProperty;
	}

	protected <T> ObjectProperty<T> createSelectedItemProperty(StructuredViewer viewer) {
		ObjectProperty<T> selectedItemProperty = new SimpleObjectProperty<>();
		populateFromSelection(selectedItemProperty, viewer.getSelection());
		ISelectionChangedListener selectionChangedListener = event -> populateFromSelection(selectedItemProperty, event.getSelection());
		viewer.addSelectionChangedListener(selectionChangedListener);
		addDisposeRunnable(() -> viewer.removeSelectionChangedListener(selectionChangedListener));
		return selectedItemProperty;
	}

	private <T> void populateFromSelection(ObjectProperty<T> selectedItemProperty, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if(structuredSelection.isEmpty()) {
				selectedItemProperty.set(null);
			} else {
				selectedItemProperty.set((T)structuredSelection.getFirstElement());
			}
		}
	}
}
