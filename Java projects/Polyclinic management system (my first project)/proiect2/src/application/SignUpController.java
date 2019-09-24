package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

	@FXML
	private TextField txtCNP;
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField password1;
	@FXML
	private PasswordField password2;
	@FXML
	private Label errorLabel;



	@FXML
	private void cancelButton() throws IOException {
		Main.showMainView();
	}

	@FXML
	private void signUp() throws SQLException, IOException, InterruptedException {

		if (txtCNP.getText().toString().length() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Completati CNP", javafx.scene.paint.Color.RED);
		}

		else if (txtCNP.getLength() != 13 || !txtCNP.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Format gresita CNP", javafx.scene.paint.Color.RED);

		} else if (!DataBase.app.checkCNP(txtCNP.getText())) {

			MessageController.displayMessageLabel(errorLabel, "Nu exista acest CNP in baza de date",
					javafx.scene.paint.Color.RED);
		} else if (DataBase.app.getUserName(txtCNP.getText()).length() != 0) {
			MessageController.displayMessageLabel(errorLabel, "Cont deja inregistrat", javafx.scene.paint.Color.RED);
		} else if (txtUserName.getText().toString().length() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Setati username", javafx.scene.paint.Color.RED);
		}

		else if (txtUserName.getText().toString().length() != 0 && DataBase.app.checUserName(txtUserName.getText().toString())) {
			MessageController.displayMessageLabel(errorLabel, "Username deja existent", javafx.scene.paint.Color.RED);

		}

		else if (txtUserName.getText().toString().length() == 0
				&& (password1.getText().toString().length() != 0 || password2.getText().toString().length() != 0)) {

			MessageController.displayMessageLabel(errorLabel, "Setati un username", javafx.scene.paint.Color.RED);

		} else if (txtUserName.getLength() != 0 && (password1.getLength() == 0 || password2.getLength() == 0)) {
			MessageController.displayMessageLabel(errorLabel, "Setati o parola", javafx.scene.paint.Color.RED);
		}

		else if (!password1.getText().toString().equals(password2.getText().toString())) {
			MessageController.displayMessageLabel(errorLabel, "Password error", javafx.scene.paint.Color.RED);

		} else {
			MessageController.displayMessageLabel(errorLabel, "Inregistrare realizata cu succes",
					javafx.scene.paint.Color.GREEN);
			DataBase.app.updateAfterSignUp(txtCNP.getText(), txtUserName.getText(), password1.getText());

			Main.showMenu(txtUserName.getText());
		}
	}

}
