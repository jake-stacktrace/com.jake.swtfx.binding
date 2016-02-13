package com.jake.swtfx.binding;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class TextWrapper extends ControlWrapper {
	private Text text;

	public TextWrapper(Text text) {
		super(text);
		this.text = text;
	}

	public StringProperty textProperty() {
		StringProperty textProperty = new SimpleStringProperty(text.getText());
		ModifyListener modifyListener = modifyEvent -> {
			textProperty.set(text.getText());
		};
		ChangeListener<String> changeListener = (observable, oldText, newText) -> Display.getDefault().asyncExec(() -> {
            if(!text.getText().equals(newText)) {
                text.removeModifyListener(modifyListener);
                text.setText(newText == null ? "" : newText);
                text.addModifyListener(modifyListener);
            }
        });
		text.addModifyListener(modifyListener);
		textProperty.addListener(changeListener);
		addDisposeRunnable(() -> {
			textProperty.removeListener(changeListener);
			text.removeModifyListener(modifyListener);
		});
		return textProperty;		
	}
}
