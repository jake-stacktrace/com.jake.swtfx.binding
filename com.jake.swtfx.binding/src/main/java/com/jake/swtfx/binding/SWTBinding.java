package com.jake.swtfx.binding;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SWTBinding {
	public static TreeWrapper wrap(TreeViewer treeViewer) {
		return new TreeWrapper(treeViewer);
	}
	public static LabelWrapper wrap(Label label) {
		return new LabelWrapper(label);
	}
	public static TextWrapper wrap(Text text) {
		return new TextWrapper(text);
	}
	public static ListWrapper wrap(ListViewer listViewer) {
		return new ListWrapper(listViewer);
	}
	public static ButtonWrapper wrap(Button button) {
		return new ButtonWrapper(button);
	}
	public static TableWrapper wrap(TableViewer tableViewer) {
		return new TableWrapper(tableViewer);
	}
	public static ControlWrapper wrap(Control control) {
		return new ControlWrapper(control);
	}
}
