package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LogInController {



	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;

	@FXML
	private Label txtLabel;
	

	@FXML
	private void LogIn() throws IOException, SQLException {

		if (txtUserName.getText().toString().length() != 0
				&& DataBase.app.checkAccount(txtUserName.getText(), txtPassword.getText())) {

			Main.showMenu(txtUserName.getText().toString());

		} else {
			MessageController.displayMessageLabel(txtLabel, "username or password incorrect", Color.RED);
			
		}
	}
	
	@FXML
	private void SignUp() throws IOException {
		
		Main.goSignUp();
	}

}
