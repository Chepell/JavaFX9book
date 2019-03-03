package ch03.lazy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Artem Voytenko
 * 03.03.2019
 * <p>
 * In the full-lazy strategy, the property object is instantiated only if the property getter is called. The
 * getter and setter go through the property object only if it is already instantiated; otherwise, they go through a
 * separate field.
 */

public class JavaFXBeanModelFullLazyExample {
	private static final String DEFAULT_STR = "Hello";
	private StringProperty str;
	private String _str = DEFAULT_STR;

	public final String getStr() {
		if (str != null) {
			return str.get();
		} else {
			return _str;
		}
	}

	public final void setStr(String str) {
		if (this.str != null) {
			this.str.set(str);
		} else {
			_str = str;
		}
	}

	public StringProperty strProperty() {
		if (str == null) {
			str = new SimpleStringProperty(this, "str", _str);
		}
		return str;
	}
}
