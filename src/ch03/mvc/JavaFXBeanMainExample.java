package ch03.mvc;

/**
 * Artem Voytenko
 * 03.03.2019
 */

public class JavaFXBeanMainExample {
	public static void main(String[] args) {
		JavaFXBeanModelExample model = new JavaFXBeanModelExample();
		JavaFXBeanViewExample view = new JavaFXBeanViewExample(model);
		JavaFXBeanControllerExample controller = new JavaFXBeanControllerExample(model, view);

		controller.incrementIPropertyOnModel();
		controller.changeStrPropertyOnModel();
		controller.switchColorPropertyOnModel();

		controller.incrementIPropertyOnModel();
		controller.changeStrPropertyOnModel();
		controller.switchColorPropertyOnModel();
	}
}
