package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ModifyUserDataController {



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
	private TextField txtFunctionForSearch;

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
	private TextField txtNumeForSearch;

	@FXML
	private TextField txtPrenumeForSearch;

	@FXML
	private AnchorPane searchPane;

	@FXML
	private BorderPane modifyPane;

	@FXML
	private SplitPane splitPane;
	@FXML
	private TableView<UserSearch> userSearchTableView;
	@FXML
	private TableColumn<UserSearch, String> userCNP;
	@FXML
	private TableColumn<UserSearch, String> userLastName;
	@FXML
	private TableColumn<UserSearch, String> userFirstName;
	@FXML
	private TableColumn<UserSearch, String> userFunction;

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
	private ObservableList<String> results;
	private String currentCNP;
	private String currentFunvtion;

	@FXML
	private void initialize() throws SQLException {

		showSeaerchRezult();

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

		String userName = Main.getUserName();
		if (!DataBase.app.getTypeUser(userName).equals("Super-administrator")){
			typeList.remove(2);
			typeList.remove(1);
		}
			

		typeUser.setItems(typeList);
		functions.setItems(functionsList);
		tipAsistent.setItems(tipAsistentList);
		gradAsistent.setItems(gradAsistentList);
		gradMedic.setItems(gradMedicList);

		if (Main.isAccountDate()) {
			splitPane.getItems().remove(0);
			modifyPane.setPrefSize(javafx.scene.control.Control.USE_COMPUTED_SIZE,
					javafx.scene.control.Control.USE_COMPUTED_SIZE);
			if (!(DataBase.app.getTypeUser(Main.getUserName()).equals("Administrator")
					|| DataBase.app.getTypeUser(Main.getUserName()).equals("Super-administrator"))) {
				// txtNume.setDisable(true);
				txtNume.setEditable(false);
				txtPrenume.setEditable(false);
				txtCNP.setEditable(false);
				txtAdresa.setEditable(false);
				txtTelefon.setEditable(false);
				txtEmail.setEditable(false);
				txtIBAN.setEditable(false);
				txtNrContract.setEditable(false);
				functions.setDisable(true);
				txtDataAngajarii.setDisable(true);
				txtSalariu.setEditable(false);
				txtNrOre.setEditable(false);
				for (CheckBox element : checkboxesSpecialties)
					element.setDisable(true);
				for (CheckBox element : checkboxesSkills)
					element.setDisable(true);
				gradMedic.setDisable(true);
				txtCodParafa.setEditable(false);
				txtTitluStiintfic.setEditable(false);
				txtPostDidactic.setEditable(false);
				txtPostDidactic.setEditable(false);
				txtProcentNegociat.setEditable(false);
				tipAsistent.setDisable(true);
				gradAsistent.setDisable(true);
				typeUser.setDisable(true);
			}

			showUserData();
		}

	}

	@FXML
	private void setMedicNULL() {

		for (CheckBox element : checkboxesSpecialties) {
			if (element.isSelected()) {
				element.setSelected(false);
			}

		}

		for (CheckBox element : checkboxesSkills) {
			if (element.isSelected()) {
				element.setSelected(false);
			}

		}

		gradMedic.setValue(null);
		txtCodParafa.setText("");
		txtTitluStiintfic.setText("");
		txtPostDidactic.setText("");
		txtProcentNegociat.setText("");
	}

	@FXML
	private void setAsistentNULL() {
		tipAsistent.setValue(null);
		gradAsistent.setValue(null);
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
	private void showUserData() throws SQLException {

		if (!userSearchTableView.getSelectionModel().isEmpty() || Main.isAccountDate()) {
			List<ResultSet> data = new ArrayList<ResultSet>();
			if (Main.isAccountDate())
				currentCNP = DataBase.app.getCNPUser(Main.getUserName());
			else {

				currentCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
			}

			String function = DataBase.app.getFunctionUser(currentCNP);
			System.out.println(currentCNP + "   " + function);
			data = DataBase.app.getAllUserData(currentCNP, function);

			ResultSet rst = data.get(0);
			txtCNP.setText(rst.getString(1));
			txtNume.setText(rst.getString(2));
			txtPrenume.setText(rst.getString(3));
			txtUsername.setText(rst.getString(4));
			password1.setText(DataBase.app.decryptPassword(DataBase.app.getUserName(currentCNP)));
			password2.setText(DataBase.app.decryptPassword(DataBase.app.getUserName(currentCNP)));
			typeUser.setValue(rst.getString(6));
			functions.setValue(rst.getString(7));
			txtAdresa.setText(rst.getString(8));
			txtTelefon.setText(rst.getString(9));
			txtEmail.setText(rst.getString(10));
			txtIBAN.setText(rst.getString(11));
			txtNrContract.setText(rst.getString(12));
			txtSalariu.setText(rst.getString(13));
			txtNrOre.setText(rst.getString(14));
			txtDataAngajarii.setValue(LocalDate.parse((rst.getString(15))));

			if (function.equals("Asistent medical")) {
				setMedicNULL();
				rst = data.get(1);
				tipAsistent.setValue(rst.getString(2));
				gradAsistent.setValue(rst.getString(3));
				System.out.println(rst.getString(2) + "   " + rst.getString(3));
				nurseData.setVisible(true);
			} else if (function.equals("Medic")) {
				setAsistentNULL();
				rst = data.get(1);

				gradMedic.setValue(rst.getString(2));
				txtCodParafa.setText(rst.getString(3));
				txtTitluStiintfic.setText(rst.getString(4));
				txtPostDidactic.setText(rst.getString(5));
				txtProcentNegociat.setText(rst.getString(6));
				medicData.setVisible(true);

				List<Integer> medicSkills = DataBase.app.getMedicSkills(currentCNP);
				List<Integer> medicSpecialties = DataBase.app.getMedicSpecialties(currentCNP);

				for (Integer index : medicSkills) {
					checkboxesSkills.get(index - 1).setSelected(true);
				}

				for (Integer index : medicSpecialties) {

					checkboxesSpecialties.get(index - 1).setSelected(true);
				}

			} else {
				setMedicNULL();
				setAsistentNULL();
			}
		}
	}

	@FXML
	private void showSeaerchRezult() throws SQLException

	{

		ObservableList<UserSearch> userSearchList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getUserDataForSearch(txtNumeForSearch.getText(), txtPrenumeForSearch.getText(),
				txtFunctionForSearch.getText());

		while (rst.next()) {
			if (DataBase.app.getTypeUser(Main.getUserName()).equals("Super-administrator")) {

				userSearchList
						.add(new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));

			} else if (DataBase.app.getTypeUser(Main.getUserName()).equals("Administrator")) {
				if (rst.getString(5).equals("Angajat")) {
					userSearchList.add(
							new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
				}
			}

		}

		userCNP.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userCNP"));
		userLastName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userLastName"));
		userFirstName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFirstName"));
		userFunction.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFunction"));

		userSearchTableView.setItems(userSearchList);

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
	private void buttonOk() throws SQLException, IOException, InterruptedException {
		errorLabel1.setTextFill(javafx.scene.paint.Color.RED);
		errorLabel2.setTextFill(javafx.scene.paint.Color.RED);
		errorLabel3.setTextFill(javafx.scene.paint.Color.RED);

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
		} else if (!txtCNP.getText().toString().equals(currentCNP) && DataBase.app.checkCNP(txtCNP.getText())) {
			displayMessage("Exista acest CNP");
		} else if (!txtUsername.getText().toString().equals(DataBase.app.getUserName(currentCNP))
				&& (txtUsername.getText().toString().length() != 0
						&& DataBase.app.checUserName(txtUsername.getText().toString()))) {
			displayMessage("Username already exists");
		} else if (txtUsername.getText().toString().length() == 0
				&& (password1.getText().toString().length() != 0 || password2.getText().toString().length() != 0)) {

			displayMessage("Setati un username");
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

			String function = DataBase.app.getFunctionUser(currentCNP);

			errorLabel1.setTextFill(javafx.scene.paint.Color.GREEN);
			errorLabel2.setTextFill(javafx.scene.paint.Color.GREEN);
			errorLabel3.setTextFill(javafx.scene.paint.Color.GREEN);

			displayMessage("Modificare realizata cu succes");

			if (Main.isAccountDate() || DataBase.app.getUserName(currentCNP).equals(Main.getUserName())) {
				Main.setUserName(txtUsername.getText());

			}

			DataBase.app.setForeignKeyChecks(0);
			DataBase.app.updateUser(currentCNP, txtCNP.getText(), txtNume.getText(), txtPrenume.getText(), txtUsername.getText(),
					password1.getText(), typeUser.getValue(), functions.getValue(), txtAdresa.getText(),
					txtTelefon.getText(), txtEmail.getText(), txtIBAN.getText(), txtNrContract.getText(),
					txtSalariu.getText(), txtNrOre.getText(), txtDataAngajarii.getValue().toString());

			if (functions.getValue() != null) {

				if (functions.getValue().equals("Medic")) {

					setAsistentNULL();
					String grad = "";
					if (gradMedic.getValue() != null)
						grad = gradMedic.getValue();

					if (function.equals("Medic"))
						DataBase.app.updateMedicData(currentCNP, txtCNP.getText(), grad, txtCodParafa.getText(),
								txtTitluStiintfic.getText(), txtPostDidactic.getText(), txtProcentNegociat.getText());
					else
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
				} else if (functions.getValue().equals("Asistent medical")) {

					setMedicNULL();

					String tip = "";
					String grad = "";

					if (tipAsistent.getValue() != null)
						tip = tipAsistent.getValue();
					if (gradAsistent.getValue() != null)
						grad = gradAsistent.getValue();

					if (function.equals("Asistent medical"))
						DataBase.app.updateAsistent(currentCNP, txtCNP.getText(), tip, grad);
					else
						DataBase.app.insertAsistent(txtCNP.getText(), tip, grad);
				} else {
					setMedicNULL();
					setAsistentNULL();
				}

			}
			// initialize();
			currentCNP = txtCNP.getText().toString();
			showSeaerchRezult();

			DataBase.app.setForeignKeyChecks(1);

		}

	}

	@FXML
	private void buttonCancel() {
		main.exitModifyUserDataStage();
	}
}
