package ch03;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * Artem Voytenko
 * 02.03.2019
 */

public class MotivatingExample {
	private static IntegerProperty intProperty;

	public static void main(String[] args) {
		createProperty();
		addAndRemoveInvalidationListener();
		addAndRemoveChangeListener();
		bindAndUnbindOnePropertyToAnother();
	}

	private static void createProperty() {
		System.out.println();
		intProperty = new SimpleIntegerProperty(1024);
		System.out.println("intProperty = " + intProperty);
		System.out.println("intProperty.get() = " + intProperty.get());
		System.out.println("intProperty.getValue() = " + intProperty.getValue().intValue());
	}

	private static void addAndRemoveInvalidationListener() {
		System.out.println();
		final InvalidationListener invalidationListener = o ->
				System.out.println("The observable has been invalidated: " + o + ".");

		intProperty.addListener(invalidationListener);
		System.out.println("Added invalidation listener.");

		System.out.println("Calling intProperty.set(2048).");
		intProperty.set(2048);

		System.out.println("Calling intProperty.setValue(3072).");
		intProperty.setValue(Integer.valueOf(3072));

		System.out.println("Removed invalidation listener.");
		intProperty.removeListener(invalidationListener);

		System.out.println("Calling intProperty.set(4096).");
		intProperty.set(4096);
	}

	private static void addAndRemoveChangeListener() {
		System.out.println();
		final ChangeListener changeListener = (o, oldValue, newValue) ->
//				System.out.println("The observableValue has changed: oldValue = " + oldValue + ", newValue = " + newValue);
				System.out.printf("The observableValue has changed: oldValue = %s, newValue = %s.%n", oldValue,
						newValue);

		System.out.println("Added change listener.");
		intProperty.addListener(changeListener);

		System.out.println("Calling intProperty.set(5120).");
		intProperty.set(5120);

		System.out.println("Removed change listener.");
		intProperty.removeListener(changeListener);

		System.out.println("Calling intProperty.set(6144).");
		intProperty.set(6144);
	}

	private static void bindAndUnbindOnePropertyToAnother() {
		System.out.println();
		IntegerProperty otherProperty = new SimpleIntegerProperty(0);
		System.out.println("otherProperty.get() = " + otherProperty.get());

		System.out.println("Binding otherProperty to intProperty.");
		otherProperty.bind(intProperty);
		System.out.println("otherProperty.get() = " + otherProperty.get());

		System.out.println("Calling intProperty.set(7168).");
		intProperty.set(7168);
		System.out.println("otherProperty.get() = " + otherProperty.get());

		System.out.println("Unbinding otherProperty from intProperty.");
		otherProperty.unbind();
		System.out.println("otherProperty.get() = " + otherProperty.get());

		System.out.println("Calling intProperty.set(8192).");
		intProperty.set(8192);
		System.out.println("otherProperty.get() = " + otherProperty.get());
	}
}
