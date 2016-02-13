package com.jake.example;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class SampleView extends Composite {

	private Text textField;
	private Label label;
	private Label summaryLabel;
	private Label listSelectionLabel;
	private Button radioButton;
	private Button pushButton;
	private ListViewer listViewer;
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Label treeSelectionLabel;
	private Label tableSelectionLabel;

	public SampleView(Composite control) {
		super(control, SWT.NONE);
		setLayout(new GridLayout(1, false));
		
		textField = new Text(this, SWT.NONE);
		textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label = new Label(this, SWT.NONE);
		radioButton = new Button(this, SWT.RADIO);
		radioButton.setText("Option 1");
		pushButton = new Button(this, SWT.PUSH);
		pushButton.setText("Add Button");
		summaryLabel = new Label(this, SWT.NONE);
		
		listSelectionLabel = new Label(this, SWT.NONE);
		listViewer = new ListViewer(this, SWT.NONE);
		listViewer.setContentProvider(new ArrayContentProvider());
		
		treeSelectionLabel = new Label(this, SWT.NONE);
		treeViewer = new TreeViewer(this, SWT.NONE);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		treeViewer.setContentProvider(new PropertyTreeContentProvider<Person>(person -> person.getChildren(), person -> person.getParent()));
		
		tableSelectionLabel = new Label(this, SWT.NONE);
		tableViewer = new TableViewer(this, SWT.NONE);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new PropertyTableLabelProvider<Person>(person -> 
			new StringExpression[] {person.nameProperty(), person.ageProperty().asString()}));
		
		TableColumn nameColumn = new TableColumn(tableViewer.getTable(), SWT.CENTER);
		nameColumn.setText("Name");
		nameColumn.setWidth(100);
		
		TableColumn ageColumn = new TableColumn(tableViewer.getTable(), SWT.CENTER);
		ageColumn.setText("Age");
		ageColumn.setWidth(100);
	}
	
	public TextWrapper getTextField() {
		return SWTBinding.wrap(textField);
	}

	public ButtonWrapper getPushButton() {
		return SWTBinding.wrap(pushButton);
	}
	
	public LabelWrapper getLabel() {
		return SWTBinding.wrap(label);
	}
	
	public ListWrapper<String> getList() {
		return SWTBinding.wrap(listViewer);
	}

	public LabelWrapper getListSelectionLabel() {
		return SWTBinding.wrap(listSelectionLabel);
	}
	
	public LabelWrapper getSummaryLabel() {
		return SWTBinding.wrap(summaryLabel);
	}

	public TableWrapper<Person> getTable() {
		return SWTBinding.wrap(tableViewer);
	}
	
	public TreeWrapper<Person> getTree() {
		return SWTBinding.wrap(treeViewer);
	}

	public LabelWrapper getTableSelectionLabel() {
		return SWTBinding.wrap(tableSelectionLabel);
	}
	
	public LabelWrapper getTreeSelectionLabel() {
		return SWTBinding.wrap(treeSelectionLabel);
	}
}
