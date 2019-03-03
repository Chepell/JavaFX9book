package ch03;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Locale;

/**
 * Artem Voytenko
 * 03.03.2019
 * <p>
 * Recall that to form a triangle, the three sides must satisfy the following
 * conditions:
 * a + b > c, b + c > a, c + a > b.
 * When the preceding conditions are satisfied, the area of the triangle can be calculated using Heron’s
 * formula:
 * Area = sqrt(s * (s – a) * (s – b) * (s – c))
 * where s is the semiperimeter:
 * s = (a + b + c) / 2.
 */

public class HeronsFormulaExample {
	public static void main(String[] args) {
		DoubleProperty a = new SimpleDoubleProperty(0);
		DoubleProperty b = new SimpleDoubleProperty(0);
		DoubleProperty c = new SimpleDoubleProperty(0);

		DoubleBinding s = a.add(b).add(c).divide(2.0D);

		final DoubleBinding areaSquared = new When(
				a.add(b).greaterThan(c)
						.and(b.add(c).greaterThan(a))
						.and(c.add(a).greaterThan(b)))
				.then(s.multiply(s.subtract(a))
						.multiply(s.subtract(b))
						.multiply(s.subtract(c)))
				.otherwise(0.0D);

		a.set(3);
		b.set(4);
		c.set(5);
		System.out.printf(Locale.US, "Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
						" the area of the triangle is %3.2f\n", a.get(), b.get(), c.get(),
				Math.sqrt(areaSquared.get()));
		a.set(2);
		b.set(2);
		c.set(2);
		System.out.printf(Locale.US, "Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
						" the area of the triangle is %3.2f\n", a.get(), b.get(), c.get(),
				Math.sqrt(areaSquared.get()));
	}
}