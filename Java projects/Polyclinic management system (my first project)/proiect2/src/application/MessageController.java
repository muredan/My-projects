package application;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MessageController {

	
	
	public static void displayMessageLabel(Label label,String messageText, Color color) {
		Task task=new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				updateMessage(messageText);
				Thread.sleep(2000);
				updateMessage("");
				return null;
				
			}
		
		};
		label.textProperty().bind(task.messageProperty());
		label.setTextFill(color);
		Thread thread=new Thread(task);
		thread.start();
	}
	
}
