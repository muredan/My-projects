package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ThirdModuleController {
	@FXML
	private Button appointmentButton;
	@FXML
	private Button registerButton;
	@FXML
	private Button historyAndAppointmentsButton;
	@FXML
	private Button raportStageButton;
	@FXML
	private Button billButton;
	@FXML
	private VBox medicModulesVBox;
	@FXML
	private VBox receptionistVBox;



	@FXML
	private void initialize() throws SQLException {
		historyAndAppointmentsButton.managedProperty().bind(historyAndAppointmentsButton.visibleProperty());
		String currentFunction = DataBase.app.getFunctionUser(DataBase.app.getCNPUser(Main.getUserName()));
		String currentUserType = DataBase.app.getTypeUser(Main.getUserName());
		boolean type = (currentUserType.equals("Super-administrator") || currentUserType.equals("Administrator"));

		if (currentFunction.equals("Medic") && !type) {
			receptionistVBox.getChildren().clear();
		} else if (currentFunction.equals("Asistent medical") && !type) {
			receptionistVBox.getChildren().clear();
			historyAndAppointmentsButton.setVisible(false);
		} else if (currentFunction.equals("Receptioner") && !type) {
			medicModulesVBox.getChildren().clear();
		}

	}

	@FXML
	private void showAppointmentButton() throws IOException {
		Main.showAppointments();
	}

	@FXML
	private void backButton() throws IOException {
		Main.showMenu(Main.getUserName());
	}

	@FXML
	private void showRegisterButton() throws IOException {
		Main.showRegister();
	}

	@FXML
	private void showHistoryAndAppointmentsButton() throws IOException {
		Main.showHistoryAndAppointmentsStage();
	}

	@FXML
	private void showRaportStageButton() throws IOException {
		Main.showRaportStage();
	}

	@FXML
	private void showBillStage() throws IOException {
		Main.showBillStage();
	}
}
