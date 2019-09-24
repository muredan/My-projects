package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuController {

	@FXML
	private Menu actionOnUser;
	@FXML
	private Button medicButton;

	

	@FXML
	private void initialize() throws SQLException {

		medicButton.managedProperty().bindBidirectional(medicButton.visibleProperty());

		String userName = Main.getUserName();
		//System.out.println(userName);
		String userType = DataBase.app.getTypeUser(userName);

		if (userType.equals("Administrator") || userType.equals("Super-administrator"))
			actionOnUser.setVisible(true);
		else
			actionOnUser.setVisible(false);

		if (!DataBase.app.getFunctionUser(DataBase.app.getCNPUser(userName)).equals("Medic")
				&& !DataBase.app.getFunctionUser(DataBase.app.getCNPUser(userName)).equals("Asistent medical")
				&& !DataBase.app.getFunctionUser(DataBase.app.getCNPUser(userName)).equals("Receptioner")
				&& !(userType.equals("Administrator") || userType.equals("Super-administrator"))) {
			medicButton.setVisible(false);
		}

	}

	@FXML
	private void goAddUserStage() throws IOException {

		Main.showAddStage();
	}

	@FXML
	private void goSearchUserStage() throws IOException {
		Main.showModifyUserDataStage(false);
	}

	@FXML
	private void goSeeAcountData() throws IOException {
		Main.showModifyUserDataStage(true);
	}

	@FXML
	private void goDeleteUserStage() throws IOException {

		Main.showDeleteUser();
	}

	@FXML
	private void goFirstModule() throws IOException {

		Main.goFirstModule();
		Main.exitPrimaryStage();
	}

	@FXML
	private void goSecondModule() throws IOException {

		Main.showSecondModule();
		Main.exitPrimaryStage();

	}

	@FXML
	private void goThirdModule() throws IOException {
		Main.showThirdModule();
	}

	@FXML
	private void goLogOut() throws IOException {

		Main.showMainView();
	}
}
