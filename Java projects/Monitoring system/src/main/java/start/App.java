package start;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.MainGraficalUserInterface;

public class App extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception {
		new MainGraficalUserInterface(primaryStage);
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
