package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView {

	private Stage primaryStage;

	private TextField minArrivalTimeTextField = new TextField();
	private TextField maxArrivalTimeTextField = new TextField();

	private TextField minServiceTimeTextField = new TextField();
	private TextField maxServiceTimeTextField = new TextField();

	private TextField numberOfQueuesTextField = new TextField();
	private TextField simulationTimeTextField = new TextField();
	private TextArea eventsTextArea = new TextArea();
	private TextField avgWaitingTimeTextField = new TextField();
	private TextField avgServiceTimeTextField = new TextField();
	private TextField emptyQueueTimeTextField = new TextField();
	private TextField peakTimeTimeTextField = new TextField();
	private List<GridPane> gridPaneList;
	
	private Label errorLabel = new Label();
	private VBox outputVBox = new VBox(30);

	private Button startButton = new Button("Start");

	
	public MainView(Stage stage) {
		primaryStage = stage;
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20, 20, 20, 20));
		VBox mainVBox = new VBox(10);
		mainVBox.setAlignment(Pos.TOP_LEFT);
		HBox titleHBox = new HBox(10);
		titleHBox.setPadding(new Insets(0, 0, -10, 0));
		titleHBox.setAlignment(Pos.CENTER_LEFT);
		HBox mainHBox = new HBox(30);
		mainHBox.setPadding(new Insets(10, 10, 10, 10));

		GridPane parametersGridPane = new GridPane();
		parametersGridPane.setHgap(15);
		parametersGridPane.setVgap(10);

		minArrivalTimeTextField.setMaxWidth(35);
		maxArrivalTimeTextField.setMaxWidth(35);
		minServiceTimeTextField.setMaxWidth(35);
		maxServiceTimeTextField.setMaxWidth(35);
		numberOfQueuesTextField.setMaxWidth(35);
		simulationTimeTextField.setMaxWidth(35);

		Label titleLabel = new Label("Simulator");
		titleLabel.setFont(Font.font(24));

		// GridPane parameters
		Label titleParametersLabel = new Label("Parametri");
		titleParametersLabel.setFont(Font.font(18));
		Label arrivalTimeLabel = new Label("Timpul sosirii");
		arrivalTimeLabel.setFont(Font.font(14));
		Label serviceTimeLabel = new Label("Timpul serviciului");
		serviceTimeLabel.setFont(Font.font(14));
		Label numberOfQueuesLabel = new Label("Nr. cozi");
		numberOfQueuesLabel.setFont(Font.font(14));
		Label simulationTimeLabel = new Label("Interval Simulare");
		simulationTimeLabel.setFont(Font.font(14));
		Label maxLabel = new Label("MAX");
		maxLabel.setFont(Font.font(16));
		Label minLabel = new Label("MIN");
		minLabel.setFont(Font.font(16));

		titleParametersLabel.setPadding(new Insets(0, 0, -15, 0));
		maxLabel.setPadding(new Insets(0, 0, -10, 0));
		minLabel.setPadding(new Insets(0, 0, -10, 0));

		parametersGridPane.setMaxWidth(300);
		// parametersGridPane.setGridLinesVisible(true);

		parametersGridPane.add(titleParametersLabel, 0, 0);
		parametersGridPane.add(errorLabel, 1, 0, 2, 1);
		errorLabel.setPadding(new Insets(0, 0, -10, 0));
		parametersGridPane.add(arrivalTimeLabel, 0, 2);
		parametersGridPane.add(serviceTimeLabel, 0, 3);
		parametersGridPane.add(numberOfQueuesLabel, 0, 5);
		parametersGridPane.add(simulationTimeLabel, 0, 6);
		parametersGridPane.add(minLabel, 1, 1);
		parametersGridPane.add(maxLabel, 2, 1);

		parametersGridPane.add(minArrivalTimeTextField, 1, 2);
		parametersGridPane.add(maxArrivalTimeTextField, 2, 2);

		parametersGridPane.add(minServiceTimeTextField, 1, 3);
		parametersGridPane.add(maxServiceTimeTextField, 2, 3);

		parametersGridPane.add(numberOfQueuesTextField, 1, 5);
		parametersGridPane.add(simulationTimeTextField, 1, 6);

		GridPane.setHalignment(maxLabel, HPos.CENTER);
		GridPane.setHalignment(minLabel, HPos.CENTER);
		GridPane.setHalignment(errorLabel, HPos.LEFT);
		GridPane.setHalignment(startButton, HPos.CENTER);
		parametersGridPane.add(startButton, 0, 8, 3, 1);

		// GridPane parameters

		GridPane resultsGridPane = new GridPane();
		resultsGridPane.setHgap(15);
		resultsGridPane.setVgap(10);

		Label titleResultLabel = new Label("Rezultate");
		titleResultLabel.setFont(Font.font(18));

		Label avgWaitingTimeLabel = new Label("Media timpului de asteptare");
		avgWaitingTimeLabel.setFont(Font.font(14));
		Label avgServiceTimeLabel = new Label("Media timpului de servire");
		avgServiceTimeLabel.setFont(Font.font(14));
		Label emptyQueueTimeLabel = new Label("Timpul fara clienti");
		emptyQueueTimeLabel.setFont(Font.font(14));
		Label peakTimeLabel = new Label("Interval de varf");
		peakTimeLabel.setFont(Font.font(14));
		Label eventsLabel = new Label("Evenimente");
		eventsLabel.setFont(Font.font(14));
		Label animationLabel = new Label("Animatie");
		animationLabel.setFont(Font.font(18));

		eventsTextArea.setMaxHeight(120);
		eventsTextArea.setMinWidth(500);
		eventsTextArea.setEditable(false);
		titleResultLabel.setPadding(new Insets(0, 0, -10, 0));
		eventsLabel.setPadding(new Insets(0, 0, -10, 0));
		avgWaitingTimeTextField.setMaxWidth(80);
		avgWaitingTimeTextField.setEditable(false);
		avgServiceTimeTextField.setMaxWidth(80);
		avgServiceTimeTextField.setEditable(false);
		peakTimeTimeTextField.setMaxWidth(80);
		peakTimeTimeTextField.setEditable(false);
		emptyQueueTimeTextField.setMaxWidth(80);
		emptyQueueTimeTextField.setEditable(false);

		resultsGridPane.add(titleResultLabel, 0, 0, 3, 1);
		resultsGridPane.add(eventsLabel, 0, 1);
		resultsGridPane.add(eventsTextArea, 0, 2, 1, 5);
		resultsGridPane.add(avgWaitingTimeLabel, 1, 2);
		resultsGridPane.add(avgWaitingTimeTextField, 2, 2);
		resultsGridPane.add(avgServiceTimeLabel, 1, 3);
		resultsGridPane.add(avgServiceTimeTextField, 2, 3);
		resultsGridPane.add(emptyQueueTimeLabel, 1, 4);
		resultsGridPane.add(emptyQueueTimeTextField, 2, 4);
		resultsGridPane.add(peakTimeLabel, 1, 5);
		resultsGridPane.add(peakTimeTimeTextField, 2, 5);
		resultsGridPane.add(animationLabel,0,8);
		GridPane.setHalignment(titleResultLabel, HPos.CENTER);

		
		outputVBox.setMinHeight(600);

		titleHBox.getChildren().add(titleLabel);
		outputVBox.getChildren().add(resultsGridPane);
		Separator separator=new Separator(Orientation.VERTICAL);
		separator.minHeightProperty().bind(parametersGridPane.heightProperty());
		mainHBox.getChildren().addAll(parametersGridPane, separator, outputVBox);

		mainVBox.getChildren().addAll(titleHBox,new Separator(Orientation.HORIZONTAL), mainHBox);

		layout.setCenter(mainVBox);
		Scene scene = new Scene(layout);
		layout.requestFocus();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Assignment 2");
		primaryStage.setMaximized(true);
		
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

	public void show() {
		primaryStage.show();
	}

	public void addStartButtonActionEvent(EventHandler<ActionEvent> event) {
		startButton.setOnAction(event);
	}

	public void setEventTexArea(String str) {
		eventsTextArea.appendText(str);
	}

	public String getMinArrivalTimeText() {
		return minArrivalTimeTextField.getText();
	}

	public String getMaxArrivalTimeText() {
		return maxArrivalTimeTextField.getText();
	}

	public String getMinServiceTimeText() {
		return minServiceTimeTextField.getText();
	}

	public String getMaxServiceTimeText() {
		return maxServiceTimeTextField.getText();
	}

	public String getNumberOfQueuesText() {
		return numberOfQueuesTextField.getText();
	}

	public String getSimulationTimeText() {
		return simulationTimeTextField.getText();
	}

	public void setAvgWaitingTimeText(String str) {
		this.avgWaitingTimeTextField.setText(str);
		;
	}

	public void setAvgServiceTimeText(String str) {
		this.avgServiceTimeTextField.setText(str);
	}

	public void setEmptyQueueTimeText(String str) {
		this.emptyQueueTimeTextField.setText(str);
	}

	public void setPeakTimeTimeText(String str) {
		this.peakTimeTimeTextField.setText(str);

	}
	public void clearEventsTextArea() {
		eventsTextArea.clear();
	}
	public void startButtonDisable(Boolean disable) {
		startButton.setDisable(disable);
	}

	public void createQueues(Integer numberOfQueues) {
		
		gridPaneList = new ArrayList<GridPane>();
		VBox animationVbox = new VBox(15);
		animationVbox.setMinHeight(300);

		for (int i = 0; i < numberOfQueues; i++) {
			ImageView imageView = new ImageView(getClass().getResource("/images/cashier.png").toExternalForm());
			imageView.setFitHeight(60);
			imageView.setFitWidth(60);
			GridPane gridPane = new GridPane();
			gridPane.addRow(0, imageView);
			gridPaneList.add(gridPane);
		}

		if (outputVBox.getChildren().size() == 2)
			outputVBox.getChildren().remove(1);

		animationVbox.getChildren().addAll(gridPaneList);
		outputVBox.getChildren().add(animationVbox);
	}

	public void addPersonIcon(Integer queueNumber) {
		Integer index=new Random().nextInt(6)+1;
		ImageView imageView = new ImageView(getClass().getResource("/images/walker"+index+".png").toExternalForm());
		imageView.setFitHeight(60);
		imageView.setFitWidth(60);
		gridPaneList.get(queueNumber).addRow(0, imageView);
	}

	public void removePersonIcon(int queueNumber) {
		if(gridPaneList.get(queueNumber).getChildren().size()!=1)
			gridPaneList.get(queueNumber).getChildren().remove(1);

	}
}
