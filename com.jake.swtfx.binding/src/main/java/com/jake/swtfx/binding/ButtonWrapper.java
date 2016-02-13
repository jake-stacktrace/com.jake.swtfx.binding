package com.jake.swtfx.binding;

import org.eclipse.swt.events.SelectionListener;
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
		ChangeListener<String> changeListener = (observable, oldText, newText) -> Display.getDefault().asyncExec(() -> {
            button.setText(newText == null ? "" : newText);
            button.getParent().layout(true, true);
        });
		textProperty.addListener(changeListener);
		addDisposeRunnable(() -> textProperty.removeListener(changeListener));
		return textProperty;		
	}
	
	public void addSelectionListener(SelectionListener listener) {
		button.addSelectionListener(listener);
	}
}
