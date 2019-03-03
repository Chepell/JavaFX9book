package ch03.lazy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Artem Voytenko
 * 03.03.2019
 * <p>
 * In the half-lazy strategy, the property object is instantiated only if the setter is called with a value that is
 * different from the default value, or if the property getter is called.
 */

public class JavaFXBeanModelHalfLazyExample {
	private static final String DEFAULT_STR = "Hello";
	private StringProperty str;

	public final String getStr() {
		if (str != null) {
			return str.get();
		} else {
			return DEFAULT_STR;
		}
	}

	public final void setStr(String str) {
		if (this.str != null || !str.equals(DEFAULT_STR)) {
			strProperty().set(str);
		}
	}

	public StringProperty strProperty() {
		if (str == null) {
			str = new SimpleStringProperty(this, "str", DEFAULT_STR);
		}
		return str;
	}
}

