package presentation;

import business.Restaurant;
import data.RestaurantSerializator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGraficalUserInterface {

	private static Button waiterButton = new Button("Waiter");
	private static Button administartorButton = new Button("Administrator");
	private static Button chefButton = new Button("Chef");
	private Stage primaryStage;
	private static Restaurant restaurant;
	private static WaiterGraficalUserInterface waiterGraficalUserInterface;
	private static ChefGraficalUserInterface chefGraficalUserInterface;

	public MainGraficalUserInterface(Stage stage) {
		primaryStage = stage;
		view();
		initialzeButtons();
		restaurant=RestaurantSerializator.deserializator();
	}

	private void view() {
		BorderPane borderPane = new BorderPane();
		VBox vBox = new VBox(30);
		vBox.setAlignment(Pos.CENTER);
		vBox.setMaxWidth(100);
		Label label = new Label("USER");
		label.setFont(new Font("Calibri", 24));
		administartorButton.setMaxWidth(Double.MAX_VALUE);
		waiterButton.setMaxWidth(Double.MAX_VALUE);
		chefButton.setMaxWidth(Double.MAX_VALUE);

		vBox.getChildren().addAll(label, administartorButton, waiterButton, chefButton);

		borderPane.setCenter(vBox);
		Scene scene = new Scene(borderPane, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void initialzeButtons() {

		waiterButton.setOnAction(e -> {
			waiterButton.setDisable(true);
			waiterGraficalUserInterface=new WaiterGraficalUserInterface(restaurant);
			restaurant.addObserver(waiterGraficalUserInterface);
		});

		administartorButton.setOnAction(e -> {
			administartorButton.setDisable(true);
			new AdministratorGraficalUserInterface(restaurant);
		});

		chefButton.setOnAction(e -> {
			chefButton.setDisable(true);
			chefGraficalUserInterface=new ChefGraficalUserInterface(restaurant);
			restaurant.addObserver(chefGraficalUserInterface);
		});
		primaryStage.setOnCloseRequest(e->{
			RestaurantSerializator.serializator(restaurant);
			Platform.exit();
		});

	}
	
	public static void administratorButtonVisible() {
		administartorButton.setDisable(false);
	}
	public static void waiterButtonVisible() {
		waiterButton.setDisable(false);
		restaurant.deleteObserver(waiterGraficalUserInterface);
	}
	public static void chefButtonVisible() {
		chefButton.setDisable(false);
		restaurant.deleteObserver(chefGraficalUserInterface);
	}
}
