package com.jake.swtfx.binding;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class LabelWrapper extends ControlWrapper {
	private final Label label;

	public LabelWrapper(Label label) {
		super(label);
		this.label = label;
	}

	public StringProperty textProperty() {
		StringProperty textProperty = new SimpleStringProperty(label.getText());
		ChangeListener<String> changeListener = (observable, oldText, newText) -> Display.getDefault().asyncExec(() -> {
            label.setText(newText == null ? "" : newText);
            label.getParent().layout(true, true);
        });
		textProperty.addListener(changeListener);
		addDisposeRunnable(() -> textProperty.removeListener(changeListener));
		return textProperty;		
	}
	
	public static void main(String[] args) {
		SimpleStringProperty simpleStringProperty2 = new SimpleStringProperty();
		SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
		simpleStringProperty.bindBidirectional(simpleStringProperty2);
		simpleStringProperty2.set("foo");
		System.out.println(simpleStringProperty2.get());
	}
}
