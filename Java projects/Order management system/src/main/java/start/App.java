package start;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.MainController;

// TODO: Auto-generated Javadoc
/**
 * The Class App.
 */
public class App extends Application{
	
	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		new MainController().start(primaryStage);
	}

}
