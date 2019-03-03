package ch01.coach;

/**
 * Artem Voytenko
 * 28.02.2019
 */

import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class StageCoachMain extends Application {
	StringProperty title = new SimpleStringProperty();
	Text textStageX;
	Text textStageY;
	Text textStageW;
	Text textStageH;
	Text textStageF;
	CheckBox checkBoxResizable;
	CheckBox checkBoxFullScreen;
	double dragAnchorX;
	double dragAnchorY;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		// получаю стиль для окна
		StageStyle stageStyle = getStageStyle();

		// сохраняю ссылку на текущее окно
		var stageRef = stage;

		// корневая нода для группировки всех объектов в окне
		Group rootGroup;
		TextField titleTextField;

		Button toBackButton = new Button("toBack()");
		toBackButton.setOnAction(e -> stageRef.toBack());

		Button toFrontButton = new Button("toFront()");
		toFrontButton.setOnAction(e -> stageRef.toFront());

		Button closeButton = new Button("close()");
		closeButton.setOnAction(e -> stageRef.close());

		// голубой прямоугольник как бекграунд окна
		Rectangle blue = new Rectangle(250, 350, Color.SKYBLUE);

		// скругления углов
		blue.setArcHeight(30);
		blue.setArcWidth(30);

		textStageX = new Text();
//		textStageX.setTextOrigin(VPos.TOP);
		textStageY = new Text();
//		textStageY.setTextOrigin(VPos.TOP);
		textStageH = new Text();
//		textStageH.setTextOrigin(VPos.TOP);
		textStageW = new Text();
//		textStageW.setTextOrigin(VPos.TOP);
		textStageF = new Text();
//		textStageF.setTextOrigin(VPos.TOP);

		checkBoxResizable = new CheckBox("resizable");
		// чекбокс изменения размеров устанавливаю в дизейбл для сцен
		checkBoxResizable.setDisable(stageStyle == StageStyle.TRANSPARENT || stageStyle == StageStyle.UNDECORATED);

		checkBoxFullScreen = new CheckBox("fullScreen");
		titleTextField = new TextField("Stage Coach!");
		Label titleLabel = new Label("title");

		HBox titleBox = new HBox(titleLabel, titleTextField);
		titleBox.setSpacing(4);

		VBox contentBox = new VBox(
				textStageX, textStageY, textStageW, textStageH, textStageF,
				checkBoxResizable, checkBoxFullScreen,
				titleBox, toBackButton, toFrontButton, closeButton);
		contentBox.setLayoutX(30);
		contentBox.setLayoutY(20);
		contentBox.setSpacing(8);

		// на корневую группу добавляю элементы как в стопку слева на право
		rootGroup = new Group(blue, contentBox);

		// на основе корневой группы создаю сцену
		Scene scene = new Scene(rootGroup);
		scene.setFill(Color.TRANSPARENT);

		// и добавляю сцену на окно
		stage.setScene(scene);

		// при нажачтии кнопки мыши фиксирую ее координаты относительно экрана и окна приложения
		rootGroup.setOnMousePressed((MouseEvent me) -> {
			dragAnchorX = me.getScreenX() - stageRef.getX();
			dragAnchorY = me.getScreenY() - stageRef.getY();
		});

		// теперь при перетягивании устанвливаю новые координаты сцене
		rootGroup.setOnMouseDragged((MouseEvent me) -> {
			stageRef.setX(me.getScreenX() - dragAnchorX);
			stageRef.setY(me.getScreenY() - dragAnchorY);
		});

		// биндю текстовые метки к нужным проперти с окна
		textStageX.textProperty()
				.bind(new SimpleStringProperty("x: ").concat(stageRef.xProperty().asString()));

		textStageY.textProperty()
				.bind(new SimpleStringProperty("y: ").concat(stageRef.yProperty().asString()));

		textStageW.textProperty()
				.bind(new SimpleStringProperty("width: ").concat(stageRef.widthProperty().asString()));

		textStageH.textProperty()
				.bind(new SimpleStringProperty("height: ").concat(stageRef.heightProperty().asString()));

		textStageF.textProperty()
				.bind(new SimpleStringProperty("focused: ").concat(stageRef.focusedProperty().asString()));

		// по умолчанию устанавливаю окно неизменяемым
		stage.setResizable(false);

		// биндю двухсторонне свойство чекбокса к проперти изменения ока
		checkBoxResizable.selectedProperty().bindBidirectional(stage.resizableProperty());

		// добавляю к проперти чекбокса инвалидейт лисенер
		checkBoxFullScreen.selectedProperty().addListener(o ->
				// при обновлении значений вызываю метод установки полного экрана
				// и значение текущее забираю из пропертис чекбокса
				stageRef.setFullScreen(checkBoxFullScreen.selectedProperty().getValue()));

		// объекту StringProperty биндю к полю ввода текста в приложении
//		title.bind(titleTextField.textProperty());
		// биндю тайтл сцены к объекту StringProperty, который уже прибенден к полю ввода текста
//		stage.titleProperty().bind(title);

		// реализация биндинга тайтла окна к полю ввода без использования дополнительного поля свойств
		stage.titleProperty().bind(titleTextField.textProperty());

		// назначаю стиль для окна, стиль в начале выбирается
		// на основе переданного при запуске в программу параметра
		stage.initStyle(stageStyle);

		// метод реагирующий на закрытие программы стандартными срезствами винды, т.е. крестик
		stage.setOnCloseRequest((WindowEvent we) -> System.out.println("Stage is closing"));

		// вывожу окно на экран
		stage.show();

		// границы экрана монитора в виде объекта прямоугольника
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

		// задаю расположение окна, делитель 2 что бы в самом центре экрана его разместить
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 3);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 3);
	}

	/**
	 * получение стиля сцены на основе переданного параметра при запуске программы в командной строке
	 *
	 * @return возвращает объект сцены
	 */
	private StageStyle getStageStyle() {
		// получаю список аргументов
		List<String> unnamedParams = getParameters().getUnnamed();
		// создаю объект для сцены по умолчанию
		StageStyle stageStyle = StageStyle.DECORATED;
		// если список аргументов не пуст, то беру первый аргумент и ищу три варианта визуализации сцен
		if (!unnamedParams.isEmpty()) {
			String stageStyleParam = unnamedParams.get(0);
			if (stageStyleParam.equalsIgnoreCase("transparent")) {
				stageStyle = StageStyle.TRANSPARENT;
			} else if (stageStyleParam.equalsIgnoreCase("undecorated")) {
				stageStyle = StageStyle.UNDECORATED;
			} else if (stageStyleParam.equalsIgnoreCase("utility")) {
				stageStyle = StageStyle.UTILITY;
			}
		}
		return stageStyle;
	}
}
