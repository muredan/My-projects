package view;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MainView {

	private Button additionButton = new Button("Suma");
	private Button subtractionButton = new Button("Diferență");
	private Button multiplicationButton = new Button("Înmulțire");
	private Button divisionButton = new Button("Împărțire");
	private Button derivationButton = new Button("Derivare");
	private Button integrationButton = new Button("Integrare");

	private Stage primaryStage;

	TextField firstPolynomialTextField = new TextField();
	TextField secondPolynomialTextField = new TextField();

	Label errorLabel = new Label();

	Text resultText = new Text();

	RadioButton firstPolynomialRadioButton = new RadioButton();
	RadioButton secondPolynomialRadioButton = new RadioButton();

	public MainView(Stage stage) {

		primaryStage = stage;

		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20, 20, 20, 20));

		VBox polynomialsVBox = new VBox(10);
		polynomialsVBox.setPrefWidth(400);
		VBox comands1VBox = new VBox(10);
		VBox comands2VBox = new VBox(10);
		HBox comandsHBox = new HBox(20);
		comandsHBox.setPadding(new Insets(27, 0, 0, 0));
		HBox titleHBox = new HBox(15);
		titleHBox.setAlignment(Pos.CENTER_LEFT);
		titleHBox.setPadding(new Insets(0, 0, -10, 0));
		HBox hBox = new HBox(20);
		HBox firstPolynomialHBox = new HBox(10);
		firstPolynomialHBox.setAlignment(Pos.CENTER_LEFT);
		HBox secondPolynomialHBox = new HBox(10);
		secondPolynomialHBox.setAlignment(Pos.CENTER_LEFT);

		firstPolynomialTextField.prefWidthProperty().bind(polynomialsVBox.prefWidthProperty());
		secondPolynomialTextField.prefWidthProperty().bind(polynomialsVBox.prefWidthProperty());

		secondPolynomialHBox.setMaxWidth(400);

		firstPolynomialRadioButton.setSelected(true);

		ToggleGroup group = new ToggleGroup();
		firstPolynomialRadioButton.setToggleGroup(group);
		secondPolynomialRadioButton.setToggleGroup(group);

		Label titleLabel = new Label("Calculator polinoame");
		titleLabel.setFont(Font.font(18));
		errorLabel.setFont(Font.font(14));

		Label resultLabel = new Label("Rezultat:");
		resultLabel.setFont(new Font(16));
		resultLabel.setPadding(new Insets(0, 0, -10, 0));

		TextFlow textFlow = new TextFlow();
		textFlow.getChildren().add(resultText);
		firstPolynomialTextField.setPromptText("*ex: x^3+5x^2+1");
		secondPolynomialTextField.setPromptText("*ex: x^2-x+5x^2+5+7");

		additionButton.setMaxWidth(Double.MAX_VALUE);
		subtractionButton.setMaxWidth(Double.MAX_VALUE);
		multiplicationButton.setMaxWidth(Double.MAX_VALUE);
		divisionButton.setMaxWidth(Double.MAX_VALUE);
		derivationButton.setMaxWidth(Double.MAX_VALUE);
		integrationButton.setMaxWidth(Double.MAX_VALUE);

		comands1VBox.getChildren().addAll(additionButton, subtractionButton, multiplicationButton);
		comands2VBox.getChildren().addAll(divisionButton, derivationButton, integrationButton);

		titleHBox.getChildren().addAll(titleLabel, errorLabel);

		firstPolynomialHBox.getChildren().addAll(firstPolynomialTextField, firstPolynomialRadioButton);
		secondPolynomialHBox.getChildren().addAll(secondPolynomialTextField, secondPolynomialRadioButton);

		polynomialsVBox.getChildren().addAll(titleHBox, firstPolynomialHBox, secondPolynomialHBox, resultLabel, textFlow);

		comandsHBox.getChildren().addAll(comands1VBox, comands2VBox);
		hBox.getChildren().addAll(polynomialsVBox, new Separator(Orientation.VERTICAL), comandsHBox);

		layout.setCenter(hBox);

		Scene scene = new Scene(layout, 650, 250);
		layout.requestFocus();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Assignment 1");
		primaryStage.setResizable(false);

	}

	public void show() {
		primaryStage.show();
	}

	public void showResult(String str) {
		resultText.setText(str);
	}

	public boolean firstPolynomialRadioButtonIsSelected() {
		return firstPolynomialRadioButton.isSelected();
	}

	public boolean secondPolynomialRadioButtonIsSelected() {
		return secondPolynomialRadioButton.isSelected();
	}

	public void displayMessageLabel(final String messageText, Color color) {
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				updateMessage(messageText);
				Thread.sleep(4000);
				updateMessage("");
				return null;

			}

		};
		errorLabel.textProperty().bind(task.messageProperty());
		errorLabel.setTextFill(color);
		Thread thread = new Thread(task);
		thread.start();
	}

	public String getPol1() {
		return firstPolynomialTextField.getText();
	}

	public String getPol2() {
		return secondPolynomialTextField.getText();
	}

	public void addAdditionButtonActionEvent(EventHandler<ActionEvent> eventHandler) {
		additionButton.setOnAction(eventHandler);
	}

	public void addSubtractionButtonActionEvent(EventHandler<ActionEvent> eventHandler) {
		subtractionButton.setOnAction(eventHandler);
	}

	public void addMultiplicationButtonActionEvent(EventHandler<ActionEvent> eventHandler) {
		multiplicationButton.setOnAction(eventHandler);
	}

	public void addDivisionButtonActionEvent(EventHandler<ActionEvent> eventHandler) {
		divisionButton.setOnAction(eventHandler);
	}

	public void addDerivationButtonActionEvent(EventHandler<ActionEvent> eventHandler) {
		derivationButton.setOnAction(eventHandler);
	}

	public void addIntegrationButtonnActionEvent(EventHandler<ActionEvent> eventHandler) {
		integrationButton.setOnAction(eventHandler);
	}
}
