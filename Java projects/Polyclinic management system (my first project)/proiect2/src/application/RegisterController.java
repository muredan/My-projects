package application;

import java.awt.Checkbox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;

public class RegisterController {

	@FXML
	private TextField txtPatientFirstName;
	@FXML
	private TextField txtPatientLastName;
	@FXML
	private TextField txtPatientCNP;
	@FXML
	private TextField txtPatientFirstNameRegister;
	@FXML
	private TextField txtPatientLastNameRegister;
	@FXML
	private TextField txtPatientAddressRegister;
	@FXML
	private TextField txtPatientPhoneRegister;

	@FXML
	private TableView<Appointment> appointmentsTableView;
	@FXML
	private TableColumn<Appointment, String> doctorLastName;
	@FXML
	private TableColumn<Appointment, String> doctorFirstName;
	@FXML
	private TableColumn<Appointment, String> patientLastName;
	@FXML
	private TableColumn<Appointment, String> patientFirstName;
	@FXML
	private TableColumn<Appointment, String> serviceColumn;
	@FXML
	private TableColumn<Appointment, String> medicalUnitColumn;
	@FXML
	private TableColumn<Appointment, String> dateColumn;
	@FXML
	private TableColumn<Appointment, String> timeColumn;
	@FXML
	private TableColumn<Appointment, String> state;
	@FXML
	private Label errorLabel;

	

	@FXML
	private void initialize() throws SQLException, ParseException {

		initializeAppointmentTableView();

	}

	@FXML
	private void initializeAppointmentTableView() throws SQLException {

		ResultSet rst = DataBase.app.getAllAppointmentsData("", "", "", txtPatientLastName.getText(),
				txtPatientFirstName.getText(), LocalDate.now().toString(), "", "nu");

		ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

		while (rst.next()) {
			appointmentsList.add(new Appointment(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4),
					rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
					rst.getString(10), ""));
		}
		doctorLastName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorLastName"));
		doctorFirstName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorFirstName"));
		patientLastName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientLastName"));
		patientFirstName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientFirstName"));
		serviceColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("service"));
		medicalUnitColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("medicalUnit"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
		state.setCellValueFactory(new PropertyValueFactory<Appointment, String>("state"));

		appointmentsTableView.setItems(appointmentsList);

	}

	@FXML
	private void check() throws SQLException {

		if (txtPatientCNP.getText().toString().length() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Setati CNP", Color.RED);
		} else if (txtPatientCNP.getLength() != 13 || !txtPatientCNP.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Format CNP gresit", Color.RED);
		} else if (!DataBase.app.checkPatientCNP(txtPatientCNP.getText())) {
			if (txtPatientFirstNameRegister.getText().toString().length() == 0
					|| txtPatientLastNameRegister.getText().toString().length() == 0
					|| txtPatientAddressRegister.getText().toString().length() == 0
					|| txtPatientPhoneRegister.getText().toString().length() == 0) {
				MessageController.displayMessageLabel(errorLabel, "Nu exista (Completati spatiile)", Color.RED);
			} else if (!txtPatientPhoneRegister.getText().toString().matches("[0-9]+")) {
				MessageController.displayMessageLabel(errorLabel, "Format telefon gresit", Color.RED);
			} else {
				DataBase.app.insertPatient(txtPatientCNP.getText(), txtPatientLastNameRegister.getText(),
						txtPatientFirstNameRegister.getText(), txtPatientAddressRegister.getText(),
						txtPatientPhoneRegister.getText());
				DataBase.app.updateStateAppointment(txtPatientCNP.getText(),
						appointmentsTableView.getSelectionModel().getSelectedItem().getCNP(),
						appointmentsTableView.getSelectionModel().getSelectedItem().getDate(),
						appointmentsTableView.getSelectionModel().getSelectedItem().getStart());
				initializeAppointmentTableView();
				MessageController.displayMessageLabel(errorLabel, "Inregitrat / prezenta cu succes ", Color.GREEN);
			}
		} else {
			DataBase.app.updateStateAppointment(txtPatientCNP.getText(),
					appointmentsTableView.getSelectionModel().getSelectedItem().getCNP(),
					appointmentsTableView.getSelectionModel().getSelectedItem().getDate(),
					appointmentsTableView.getSelectionModel().getSelectedItem().getStart());
			initializeAppointmentTableView();
			MessageController.displayMessageLabel(errorLabel, "Prezenta cu succes ", Color.GREEN);
		}

	}

	@FXML
	private void registerButton() throws SQLException {

		if (txtPatientCNP.getText().toString().length() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Setati CNP", Color.RED);
		} else if (txtPatientCNP.getLength() != 13 || !txtPatientCNP.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Format CNP gresit", Color.RED);
		} else if (DataBase.app.checkPatientCNP(txtPatientCNP.getText())) {
			MessageController.displayMessageLabel(errorLabel, "Deja inregistrat", Color.RED);
		} else if (txtPatientFirstNameRegister.getText().toString().length() == 0
				|| txtPatientLastNameRegister.getText().toString().length() == 0
				|| txtPatientAddressRegister.getText().toString().length() == 0
				|| txtPatientPhoneRegister.getText().toString().length() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Completati spatiile", Color.RED);
		} else if (!txtPatientPhoneRegister.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Format telefon gresit", Color.RED);
		} else {
			DataBase.app.insertPatient(txtPatientCNP.getText(), txtPatientLastNameRegister.getText(),
					txtPatientFirstNameRegister.getText(), txtPatientAddressRegister.getText(),
					txtPatientPhoneRegister.getText());
			MessageController.displayMessageLabel(errorLabel, "Inregitrat  cu succes ", Color.GREEN);
		}

	}

	@FXML
	void getPatientNameFromTableView() {

		if (!appointmentsTableView.getSelectionModel().isEmpty()) {
			txtPatientLastNameRegister
					.setText(appointmentsTableView.getSelectionModel().getSelectedItem().getPatientLastName());
			txtPatientFirstNameRegister
					.setText(appointmentsTableView.getSelectionModel().getSelectedItem().getPatientFirstName());
		}
	}

	@FXML
	private void backBurron() {
		Main.exitRegister();
	}

}
