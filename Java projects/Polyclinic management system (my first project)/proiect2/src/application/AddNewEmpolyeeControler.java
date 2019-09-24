package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class AddNewEmpolyeeControler {

	

	@FXML
	private ComboBox<String> functions;

	@FXML
	private ComboBox<String> typeUser;

	@FXML
	private ComboBox<String> gradAsistent;

	@FXML
	private ComboBox<String> tipAsistent;

	@FXML
	private ComboBox<String> gradMedic;

	@FXML
	private GridPane medicData;

	@FXML
	private GridPane nurseData;

	@FXML
	private GridPane specialties;

	@FXML
	private GridPane skills;

	@FXML
	private GridPane generalData;

	@FXML
	private TextField txtNume;

	@FXML
	private TextField txtPrenume;

	@FXML
	private TextField txtCNP;

	@FXML
	private TextField txtAdresa;

	@FXML
	private TextField txtTelefon;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtIBAN;

	@FXML
	private TextField txtNrContract;

	@FXML
	private DatePicker txtDataAngajarii;

	@FXML
	private TextField txtSalariu;

	@FXML
	private TextField txtNrOre;

	@FXML
	private TextField txtCodParafa;

	@FXML
	private TextField txtTitluStiintfic;

	@FXML
	private TextField txtPostDidactic;

	@FXML
	private TextField txtProcentNegociat;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField password1;

	@FXML
	private PasswordField password2;

	@FXML
	private Label errorLabel1;

	@FXML
	private Label errorLabel2;

	@FXML
	private Label errorLabel3;

	@FXML
	List<CheckBox> checkboxesSpecialties = new ArrayList<CheckBox>();

	@FXML
	List<CheckBox> checkboxesSkills = new ArrayList<CheckBox>();

	ObservableList<String> functionsList = FXCollections.observableArrayList("Asistent medical", "Medic", "Receptioner",
			"Expert financiar", "Inspector resurse umane");

	ObservableList<String> typeList = FXCollections.observableArrayList("Angajat", "Administrator",
			"Super-administrator");

	ObservableList<String> tipAsistentList = FXCollections.observableArrayList("Generalist", "Laborator", "Radiologie");

	ObservableList<String> gradAsistentList = FXCollections.observableArrayList("Principal", "Secundar");
	ObservableList<String> gradMedicList = FXCollections.observableArrayList("Specialist", "Primar");

	private Main main;

	@FXML
	private void initialize() throws SQLException {

		List<String> specialtiesList = new ArrayList<String>();
		specialtiesList = DataBase.app.getSpecialties();

		int i = 1;
		for (String element : specialtiesList) {
			CheckBox currentCheckBox = new CheckBox();
			currentCheckBox.setText(element);
			specialties.addRow(i++, currentCheckBox);
			checkboxesSpecialties.add(currentCheckBox);
		}

		List<String> skillsList = new ArrayList<String>();
		skillsList = DataBase.app.getSkills();

		i = 1;
		for (String element : skillsList) {
			CheckBox currentCheckBox = new CheckBox();
			currentCheckBox.setText(element);
			skills.addRow(i++, currentCheckBox);
			checkboxesSkills.add(currentCheckBox);
		}

		String userName = main.getUserName();
		if (!DataBase.app.getTypeUser(userName).equals("Super-administrator")) {
			typeList.remove(2);
			typeList.remove(1);
		}

		typeUser.setItems(typeList);
		functions.setItems(functionsList);
		tipAsistent.setItems(tipAsistentList);
		gradAsistent.setItems(gradAsistentList);
		gradMedic.setItems(gradMedicList);
	}

	@FXML
	private void getfunction() {

		if (functions.getValue().equals("Medic")) {
			medicData.setVisible(true);
		} else
			medicData.setVisible(false);

		if (functions.getValue().equals("Asistent medical")) {
			nurseData.setVisible(true);
		} else
			nurseData.setVisible(false);

	}

	@FXML
	public void displayMessage(String messageText) {

		Task task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {

				updateMessage(messageText);
				Thread.sleep(2000);
				updateMessage("");

				return null;
			}

		};

		errorLabel1.textProperty().bind(task.messageProperty());
		errorLabel2.textProperty().bind(task.messageProperty());
		errorLabel3.textProperty().bind(task.messageProperty());

		Thread thread = new Thread(task);
		thread.start();
	}

	@FXML
	private void buttonOk() throws SQLException, InterruptedException, IOException {

		int checkSkills = 0;
		int checkSpecialities = 0;
		String dataAngajarii;

		if (functions.getValue() != null)
			if (functions.getValue().toString().equals("Medic")) {

				for (CheckBox element : checkboxesSpecialties) {
					if (element.isSelected()) {
						checkSpecialities = 1;
						break;
					}

				}

				for (CheckBox element : checkboxesSkills) {
					if (element.isSelected()) {
						checkSkills = 1;
						break;
					}
				}

			}

		if (txtNume.getLength() == 0 || txtPrenume.getLength() == 0 || typeUser.getValue() == null
				|| functions.getValue() == null || txtAdresa.getLength() == 0 || txtTelefon.getLength() == 0
				|| txtEmail.getLength() == 0 || txtIBAN.getLength() == 0 || txtNrContract.getLength() == 0
				|| txtSalariu.getLength() == 0 || txtNrOre.getLength() == 0 || txtDataAngajarii.getValue() == null) {
			displayMessage("Completati toate spatiile");
		}

		else if (functions.getValue().toString().equals("Medic")
				&& (gradMedic.getValue() == null || txtCodParafa.getLength() == 0 || txtTitluStiintfic.getLength() == 0
						|| txtPostDidactic.getLength() == 0 || txtProcentNegociat.getLength() == 0 || checkSkills == 0
						|| checkSpecialities == 0)) {

			displayMessage("Completati toate spatiile");
		} else if (functions.getValue().toString().equals("Asistent medical")
				&& (gradAsistent.getValue() == null || tipAsistent.getValue() == null)) {

			displayMessage("Completati toate spatiile");

		} else if (txtCNP.getLength() != 13 || !txtCNP.getText().toString().matches("[0-9]+")) {
			displayMessage("Format gresita CNP");

		} else if (DataBase.app.checkCNP(txtCNP.getText())) {
			displayMessage("Exista acest CNP");
		} else if (txtUsername.getText().toString().length() != 0
				&& DataBase.app.checUserName(txtUsername.getText().toString())) {
			displayMessage("Username already exists");
		}

		else if (txtUsername.getText().toString().length() == 0
				&& (password1.getText().toString().length() != 0 || password2.getText().toString().length() != 0)) {

			displayMessage("Set an username");

		} else if (txtUsername.getLength() != 0 && (password1.getLength() == 0 || password2.getLength() == 0)) {
			displayMessage("setati o parola");
		}

		else if (!password1.getText().toString().equals(password2.getText().toString())) {
			displayMessage("Password error");
		} else if (typeUser.getValue() == null) {
			displayMessage("Setati tipul utilizatorului");
		} else if (!txtSalariu.getText().matches("[0-9]+.[0-9]+") && !txtSalariu.getText().matches("[0-9]+")) {
			displayMessage("Format salariu gresit");
		} else if (!txtNrOre.getText().matches("[0-9]+")) {
			displayMessage("Format nr.ore gresit");
		} else if (functions.getSelectionModel().getSelectedItem().equals("Medic")
				&& (!txtProcentNegociat.getText().matches("[0-9]+.[0-9]+")
						&& !txtProcentNegociat.getText().matches("[0-9]+"))) {
			displayMessage("Format procent negociat gresit");
		} else if (!txtNrContract.getText().matches("[0-9]+")) {
			displayMessage("Format nr.contract gresit");
		} else if (!txtTelefon.getText().matches("[0-9]+")) {
			displayMessage("Format nr. de telefon gresit");
		} else {

			DataBase.app.insertUser(txtCNP.getText(), txtNume.getText(), txtPrenume.getText(), txtUsername.getText(),
					password1.getText(), typeUser.getValue(), functions.getValue(), txtAdresa.getText(),
					txtTelefon.getText(), txtEmail.getText(), txtIBAN.getText(), txtNrContract.getText(),
					txtSalariu.getText(), txtNrOre.getText(), txtDataAngajarii.getValue().toString());

			if (functions.getValue() != null) {

				if (functions.getValue().equals("Medic")) {
					String grad = "";
					if (gradMedic.getValue() != null)
						grad = gradMedic.getValue();

					DataBase.app.insertMedicData(txtCNP.getText(), grad, txtCodParafa.getText(), txtTitluStiintfic.getText(),
							txtPostDidactic.getText(), txtProcentNegociat.getText());

					int i = 1;

					for (CheckBox element : checkboxesSpecialties) {
						if (element.isSelected()) {
							DataBase.app.insertMedicSpecialties(txtCNP.getText(), i);
						}
						i++;
					}

					i = 1;

					for (CheckBox element : checkboxesSkills) {
						if (element.isSelected()) {
							DataBase.app.insertMedicSkills(txtCNP.getText(), i);
						}
						i++;
					}
				}
				if (functions.getValue().equals("Asistent medical")) {
					String tip = "";
					String grad = "";

					if (tipAsistent.getValue() != null)
						tip = tipAsistent.getValue();
					if (gradAsistent.getValue() != null)
						grad = gradAsistent.getValue();

					DataBase.app.insertAsistent(txtCNP.getText(), tip, grad);
				}

			}
			main.showNextAdd();
		}

	}

	@FXML
	private void buttonCancel() {
		main.exitAddStage();
	}
}
