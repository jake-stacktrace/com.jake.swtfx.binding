package com.jake.swtfx.binding;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class ButtonWrapper extends ControlWrapper {
	private Button button;

	public ButtonWrapper(Button button) {
		super(button);
		this.button = button;
	}

	public StringProperty textProperty() {
		StringProperty textProperty = new SimpleStringProperty(button.getText());
		ChangeListener<String> changeListener = (observable, oldText, newText) ->
				Display.getDefault().asyncExec(() -> {
            button.setText(newText == null ? "" : newText);
            button.getParent().layout(true, true);
        });
		textProperty.addListener(changeListener);
		addDisposeRunnable(() -> textProperty.removeListener(changeListener));
		return textProperty;
	}

	public ObjectProperty<Image> imageProperty() {
		ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
		ChangeListener<Image> changeListener = (observable, oldValue, newValue) ->
			Display.getDefault().asyncExec(() -> {
			button.setImage(newValue);
			button.getParent().layout(true, true);
		});
		imageProperty.addListener(changeListener);
		addDisposeRunnable(() -> imageProperty.removeListener(changeListener));
		return imageProperty;
	}
	
	public void addSelectionListener(SelectionListener listener) {
		button.addSelectionListener(listener);
	}
}
