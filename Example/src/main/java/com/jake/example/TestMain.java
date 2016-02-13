package com.jake.example;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestMain {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		SampleView view = new SampleView(shell);
		SampleModel model = new SampleModel();
		new SampleBinder().bind(view, model);
		
		
		shell.open();

		while (!shell.isDisposed()) {
		  if (!display.readAndDispatch())
		   {
		    display.sleep();
		   }
		}
		display.dispose();
	}
}
