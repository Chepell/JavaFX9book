package ch01.metronome;

/**
 * Artem Voytenko
 * 02.03.2019
 */

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Metronome1Main extends Application {
	// проперти для таймлайна
	private DoubleProperty startXVal = new SimpleDoubleProperty(100.0);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Timeline anim = new Timeline(
				new KeyFrame(new Duration(0.0), new KeyValue(startXVal, 100.)),
				new KeyFrame(new Duration(1000.0), new KeyValue(startXVal, 300.,Interpolator.LINEAR))
		);
		anim.setAutoReverse(true); // автовозврат анимации а не начало заново
		anim.setCycleCount(Animation.INDEFINITE);

		// создаю линию с координатами начала и конца
		Line line = new Line(0, 50, 200, 400);
		line.setStrokeWidth(10);
		line.setStroke(Color.BLUE);


		Button startButton = new Button("start");
		startButton.setOnAction(e -> anim.playFromStart());

		Button pauseButton = new Button("pause");
		pauseButton.setOnAction(e -> anim.pause());

		Button resumeButton = new Button("resume");
		resumeButton.setOnAction(e -> anim.play());

		Button stopButton = new Button("stop");
		stopButton.setOnAction(e -> anim.stop());

		// создание горизонтального блока с кнопками и сразу установка расстояния между объектами
		HBox commands = new HBox(25,
				startButton, pauseButton,
				resumeButton, stopButton);

		// задание расположения объкта на сцене
		commands.setLayoutX(60);
		commands.setLayoutY(420);

		// группа с самим метрономом и кнопками
		Group group = new Group(line, commands);

		// создание сцены на основе группы
		Scene scene = new Scene(group, 400, 500);

		// биндинг проперти линии для анимации
		line.startXProperty().bind(startXVal);

		// биндинг свойств запрета работы кнопок в зависисмости от событий анимации
		startButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.STOPPED));
		pauseButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.RUNNING));
		resumeButton.disableProperty().bind(anim.statusProperty().isNotEqualTo(Animation.Status.PAUSED));
		stopButton.disableProperty().bind(anim.statusProperty().isEqualTo(Animation.Status.STOPPED));

		stage.setScene(scene);
		stage.setTitle("Metronome 1");
		stage.show();
	}
}
