package com.jake.swtfx.binding;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import javafx.beans.binding.StringExpression;
import javafx.util.Callback;

public class PropertyTableLabelProvider<T> extends BaseLabelProvider implements ITableLabelProvider{
	private Callback<T, StringExpression[]> textExtractor;
	private Callback<T, ObservableObjectValue<Image>[]> imageExtractor;

	public PropertyTableLabelProvider(Callback<T, StringExpression[]> textExtractor) {
		this.textExtractor = textExtractor;
	}

	public PropertyTableLabelProvider(Callback<T, StringExpression[]> textExtractor, Callback<T, ObservableObjectValue<Image>[]> imageExtractor) {
		this.textExtractor = textExtractor;
		this.imageExtractor = imageExtractor;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return imageExtractor.call((T)element)[columnIndex].get();
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		return textExtractor.call((T)element)[columnIndex].get();
	}
}
