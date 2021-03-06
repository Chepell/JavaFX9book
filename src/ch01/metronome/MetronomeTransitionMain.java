package ch01.metronome;

/**
 * Artem Voytenko
 * 02.03.2019
 */

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MetronomeTransitionMain extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		// круг с координатами, диаметром и цветом
		Circle circle = new Circle(100, 50, 4, Color.BLUE);

		// создание анимации нужной продолжительности для конкретного объекта
		TranslateTransition anim = new TranslateTransition(new Duration(1000.0), circle);
		anim.setFromX(0);
		anim.setToX(200);
		// будет еще и по оси Y бегать
//		anim.setFromY(0);
//		anim.setToY(200);
		anim.setAutoReverse(true);
		anim.setCycleCount(Animation.INDEFINITE);
		anim.setInterpolator(Interpolator.LINEAR);

		Button startButton = new Button("start");
		startButton.setOnAction(e -> anim.playFromStart());

		Button pauseButton = new Button("pause");
		pauseButton.setOnAction(e -> anim.pause());

		Button resumeButton = new Button("resume");
		resumeButton.setOnAction(e -> anim.play());

		Button stopButton = new Button("stop");
		stopButton.setOnAction(e -> anim.stop());

		HBox commands = new HBox(10,
				startButton, pauseButton,
				resumeButton, stopButton);

		// расположение группы
		commands.setLayoutX(60);
		commands.setLayoutY(420);


		Group group = new Group(circle, commands);
		Scene scene = new Scene(group, 400, 500);

		startButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.STOPPED));
		pauseButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.RUNNING));
		resumeButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.PAUSED));
		stopButton.disableProperty().bind(anim.statusProperty().isEqualTo(Animation.Status.STOPPED));

		stage.setScene(scene);
		stage.setTitle("Metronome using TranslateTransition");
		stage.show();
	}
}