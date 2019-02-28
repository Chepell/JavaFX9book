package ch01.audioApp;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Artem Voytenko
 * 27.02.2019
 */

public class AudioConfigMain extends Application {
	// A reference to the model
	private AudioConfigModel acModel = new AudioConfigModel();

	private Text textDb;
	private Slider slider;
	private CheckBox mutingCheckBox;
	private ChoiceBox genreChoiceBox;
	private Color color = Color.color(0.66, 0.67, 0.69);


	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {


		Text title = new Text(65, 12, "Audio Configuration");
		title.setTextOrigin(VPos.TOP);
		title.setFill(Color.WHITE);
		title.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));

		textDb = new Text();
		textDb.setLayoutX(18);
		textDb.setLayoutY(69);
		textDb.setTextOrigin(VPos.TOP);
		textDb.setFill(Color.web("#131021"));
		textDb.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

		Text mutingText = new Text(18, 113, "Muting");
		mutingText.setTextOrigin(VPos.TOP);
		mutingText.setFont(Font.font("SanSerif", FontWeight.BOLD, 18));
		mutingText.setFill(Color.web("#131021"));

		Text genreText = new Text(18, 154, "Genre");
		genreText.setTextOrigin(VPos.TOP);
		genreText.setFill(Color.web("#131021"));
		genreText.setFont(Font.font("SanSerif", FontWeight.BOLD, 18));

		slider = new Slider();
		slider.setLayoutX(135);
		slider.setLayoutY(69);
		slider.setPrefWidth(162);
		slider.setMin(acModel.minDecibels);
		slider.setMax(acModel.maxDecibels);

		mutingCheckBox = new CheckBox();
		mutingCheckBox.setLayoutX(280);
		mutingCheckBox.setLayoutY(113);

		genreChoiceBox = new ChoiceBox();
		genreChoiceBox.setLayoutX(204);
		genreChoiceBox.setLayoutY(154);
		genreChoiceBox.setPrefWidth(93);
		genreChoiceBox.setItems(acModel.genres);

		Stop[] stops = new Stop[]{
				new Stop(0, Color.web("0xAEBBCC")),
				new Stop(1, Color.web("0x6D84A3"))
		};

		LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		Rectangle rectangle = new Rectangle(0, 0, 320, 45);
		rectangle.setFill(linearGradient);

		Rectangle rectangle2 = new Rectangle(0, 43, 320, 300);
		rectangle2.setFill(Color.rgb(199, 206, 213));

		Rectangle rectangle3 = new Rectangle(8, 54, 300, 130);
		rectangle3.setArcHeight(20);
		rectangle3.setArcWidth(20);
		rectangle3.setFill(Color.WHITE);
		rectangle3.setStroke(color);

		Line line1 = new Line(9, 97, 309, 97);
		line1.setStroke(color);

		Line line2 = new Line(9, 141, 309, 141);
		line2.setFill(color);

		Group group = new Group(rectangle, title, rectangle2, rectangle3, textDb, slider, line1,
				mutingText, mutingCheckBox, line2, genreText, genreChoiceBox);
		Scene scene = new Scene(group, 320, 343);

		textDb.textProperty().bind(acModel.selectedDBs.asString().concat(" dB"));

		slider.valueProperty().bindBidirectional(acModel.selectedDBs);
		slider.disableProperty().bind(acModel.muting);

		mutingCheckBox.selectedProperty().bindBidirectional(acModel.muting);

		acModel.genreSelectionModel = genreChoiceBox.getSelectionModel();
		acModel.addListenerToGenreSelectionModel();
		acModel.genreSelectionModel.selectFirst();


		stage.setScene(scene);
		stage.setTitle("Audio Configuration");
		stage.setResizable(false);
//		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
