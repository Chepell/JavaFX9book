package ch01.onscene;

/**
 * Artem Voytenko
 * 01.03.2019
 */

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OnTheSceneMain extends Application {
	private Scene sceneRef;
	private DoubleProperty fillVals = new SimpleDoubleProperty(255.0);
	private ObservableList<Cursor> cursors = FXCollections.observableArrayList(
			Cursor.DEFAULT,
			Cursor.CROSSHAIR,
			Cursor.WAIT,
			Cursor.TEXT,
			Cursor.HAND,
			Cursor.MOVE,
			Cursor.N_RESIZE,
			Cursor.NE_RESIZE,
			Cursor.E_RESIZE,
			Cursor.SE_RESIZE,
			Cursor.S_RESIZE,
			Cursor.SW_RESIZE,
			Cursor.W_RESIZE,
			Cursor.NW_RESIZE,
			Cursor.NONE
	);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		// значения слайдера мин, макс и начальное положение
		Slider sliderRef = new Slider(0, 255, 255);
		sliderRef.setOrientation(Orientation.VERTICAL);

		// биндинг проперти поля к слайдеру
		fillVals.bind(sliderRef.valueProperty());


		// в чойс бокс передаю свой список значений курсоров
		ChoiceBox<Cursor> choiceBoxRef = new ChoiceBox<>(cursors);
		// выбор значения по умолчанию
		choiceBoxRef.getSelectionModel().select(4);


		HBox hbox = new HBox(sliderRef, choiceBoxRef);
		hbox.setSpacing(10);

		// создание тексовых объектов и назначение им стилей их css
		Text textSceneX = new Text();
		textSceneX.getStyleClass().add("emphasized-text");

		Text textSceneY = new Text();
		textSceneY.getStyleClass().add("emphasized-text");

		Text textSceneW = new Text();
		textSceneW.getStyleClass().add("emphasized-text");

		Text textSceneH = new Text();
		textSceneH.getStyleClass().add("emphasized-text");
		textSceneH.setId("sceneHeightText"); // назначение айди ноде

		// гиперссылка
		Hyperlink hyperlink = new Hyperlink("lookup");

		// обработчик гиперссылки
		hyperlink.setOnAction(e -> {
			System.out.println("sceneRef:" + sceneRef);
			// доступ к ноде сцены по айди
			Text textRef = (Text) sceneRef.lookup("#sceneHeightText");
			System.out.println(textRef.getText());
		});

		// группа для радиокнопок
		final ToggleGroup toggleGrp = new ToggleGroup();

		RadioButton radio1 = new RadioButton("css/onTheScene.css");
		radio1.setSelected(true);
		radio1.setToggleGroup(toggleGrp);

		RadioButton radio2 = new RadioButton("css/changeOfScene.css");
		radio2.setToggleGroup(toggleGrp);

		Label labelStageX = new Label();
		labelStageX.setId("stageX");

		Label labelStageY = new Label();
		labelStageY.setId("stageY");

		Label labelStageW = new Label();
		Label labelStageH = new Label();

		// плавающая панель будет корневым узом с вертикальной
		// ориентацией, далее расстоение между стобцами и между строками
		FlowPane sceneRoot = new FlowPane(Orientation.VERTICAL, 25, 10,
				hbox, textSceneX, textSceneY, textSceneW, textSceneH, hyperlink,
				radio1, radio2, labelStageX, labelStageY, labelStageW, labelStageH);

		sceneRoot.setPadding(new Insets(0, 20, 40, 0));
		sceneRoot.setColumnHalignment(HPos.LEFT);
		// отступы от границы окна приложения
		sceneRoot.setLayoutX(20);
		sceneRoot.setLayoutY(40);

		// создание сцены из коренвой ноды
		sceneRef = new Scene(sceneRoot, 600, 250);
		// применение стиля к ноде
		sceneRef.getStylesheets().add("css/onTheScene.css");
		// установка сцены на окно приложения
		stage.setScene(sceneRef);

		// биндинг курсора на сцене к значению в чекбоксе
		sceneRef.cursorProperty().bind(choiceBoxRef.getSelectionModel().selectedItemProperty());

		// чейнж лисенер на изменение значения проперти и заполнение сцены цветом
		fillVals.addListener((ov, oldValue, newValue) -> {
			double fillValue = newValue.doubleValue() / 256.0;
			sceneRef.setFill(new Color(fillValue, fillValue, fillValue, 1.0));
		});

		// Setup various property binding
		textSceneX.textProperty()
				.bind(new SimpleStringProperty("Scene x: ").concat(sceneRef.xProperty().asString()));
		textSceneY.textProperty()
				.bind(new SimpleStringProperty("Scene y: ").concat(sceneRef.yProperty().asString()));

		textSceneW.textProperty()
				.bind(new SimpleStringProperty("Scene width: ").concat(sceneRef.widthProperty().asString()));
		textSceneH.textProperty()
				.bind(new SimpleStringProperty("Scene height: ").concat(sceneRef.heightProperty().asString()));

		labelStageX.textProperty()
				.bind(new SimpleStringProperty("Stage x: ").concat(sceneRef.getWindow().xProperty().asString()));
		labelStageY.textProperty()
				.bind(new SimpleStringProperty("Stage y: ").concat(sceneRef.getWindow().yProperty().asString()));


		labelStageW.textProperty().bind(new SimpleStringProperty("Stage width: ")
				.concat(sceneRef.getWindow().widthProperty().asString()));

		labelStageH.textProperty().bind(new SimpleStringProperty("Stage height: ")
				.concat(sceneRef.getWindow().heightProperty().asString()));

		// или вот так, короче через обращение ко всему окну
//		labelStageX.textProperty()
//				.bind(new SimpleStringProperty("Stage x: ").concat(stage.xProperty().asString()));
//		labelStageY.textProperty()
//				.bind(new SimpleStringProperty("Stage y: ").concat(stage.yProperty().asString()));
//
//		labelStageX.textProperty()
//				.bind(new SimpleStringProperty("Stage x: ").concat(stage.widthProperty().asString()));
//		labelStageY.textProperty()
//				.bind(new SimpleStringProperty("Stage y: ").concat(stage.heightProperty().asString()));


		// инвалидейт лисенер на изменение стостояния группы с радиокнопками
		toggleGrp.selectedToggleProperty().addListener(ov -> {
			String radioButtonText = ((RadioButton) toggleGrp.getSelectedToggle()).getText();
			// убираю старый стиль и добавляю новый
			sceneRef.getStylesheets().clear();
			sceneRef.getStylesheets().addAll("/" + radioButtonText);
		});

		stage.setTitle("On the Scene");
		stage.show();

		// отдельная текстовая нода, которя уже постфактум добавляется в правающую панель - корневую ноду
		// при создании оперделяю местоположение относительно ноды в которую буду добавляться
		Text addedTextRef = new Text(30, -20, "");
		addedTextRef.setManaged(false); // не управлять элементом в ноде, будет всегда первым сверху
		addedTextRef.setTextOrigin(VPos.CENTER);
		addedTextRef.setFill(Color.BLUE);
		addedTextRef.setFont(Font.font("Sans Serif", FontWeight.BOLD, 16));

		// Bind the text of the added Text node to the fill property of the Scene
		addedTextRef.textProperty().bind(new SimpleStringProperty("Scene fill: ").concat(sceneRef.fillProperty()));

		// Add to the Text node to the FlowPane
		((FlowPane) sceneRef.getRoot()).getChildren().add(addedTextRef);
	}
}