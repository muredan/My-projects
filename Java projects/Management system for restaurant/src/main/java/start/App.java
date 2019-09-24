package start;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.MainGraficalUserInterface;

/**
 * Hello world!
 *
 */
public class App extends Application
{
	@Override
	public void start(Stage stage) throws Exception {
		new MainGraficalUserInterface(stage);
	}
}
