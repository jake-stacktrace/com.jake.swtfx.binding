# com.jake.swtfx.binding
Supports using SWT Controls with Java FX Bindings

Installation:
For Gradle:
repositories {
  maven {
      url "https://dl.bintray.com/jake-stacktrace/maven"
  }
}

dependencies {
    compile 'com.jake:com.jake.swtfx.binding:1.0.0'
}

Usage:
  SWTBinding.wrap(swtControl).someProperty().bind(observable);

For example:
  Label label = new Label(compositeParent, SWT.NONE);
  StringProperty nameProperty = new SimpleStringProperty();
  SWTBinding.wrap(label).textProperty().bind(nameProperty);
