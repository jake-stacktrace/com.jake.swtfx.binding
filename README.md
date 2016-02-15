# com.jake.swtfx.binding
Supports using SWT Controls with Java FX Bindings

<pre>
Installation (Gradle):

repositories {
  jcenter()
}

dependencies {
    compile 'com.jake:com.jake.swtfx.binding:1.0.1'
}

Usage:
  SWTBinding.wrap(swtControl).someProperty().bind(observable);

For example:
  Label label = new Label(compositeParent, SWT.NONE);
  StringProperty nameProperty = new SimpleStringProperty();
  SWTBinding.wrap(label).textProperty().bind(nameProperty);
</pre>
