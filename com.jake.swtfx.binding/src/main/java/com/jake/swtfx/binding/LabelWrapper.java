package com.jake.swtfx.binding;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.eclipse.swt.graphics.Image;
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

	public ObjectProperty<Image> imageProperty() {
		ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
		ChangeListener<Image> changeListener = (observable, oldValue, newValue) ->
				Display.getDefault().asyncExec(() -> {
					label.setImage(newValue);
					label.getParent().layout(true, true);
				});
		imageProperty.addListener(changeListener);
		addDisposeRunnable(() -> imageProperty.removeListener(changeListener));
		return imageProperty;
	}
}
