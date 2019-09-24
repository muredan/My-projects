package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

public class AppointmentController {

	@FXML
	private TableView<AvailableAppoinment> availableAppointmentsTableView;
	@FXML
	private TableColumn<AvailableAppoinment, String> firstName;
	@FXML
	private TableColumn<AvailableAppoinment, String> lastName;
	@FXML
	private TableColumn<AvailableAppoinment, String> medicalUnit;

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
	private ComboBox appointmentTime;
	@FXML
	private ComboBox service;
	@FXML
	private DatePicker appointmentDate;
	@FXML
	private TextField txtPatientFitrstName;
	@FXML
	private TextField txtPatientLastName;

	@FXML
	private Label errorLabel;

	@FXML
	private TextField txtDoctorLastName;
	@FXML
	private TextField txtDoctorFirstName;
	@FXML
	private TextField txtPatientLastNameSearch;
	@FXML
	private TextField txtPatientFirstNameSearch;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox serviceSearch;

	

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	@FXML
	private void initialize() throws SQLException, ParseException {

		service.setItems(DataBase.app.getAllServices());
		serviceSearch.setItems(DataBase.app.getAllServices());
		initializeAppointmentTableView();

	}

	private boolean checkVacation(String CNP, String dateCheck) throws SQLException {

		ResultSet rst = DataBase.app.getVacation(CNP);
		LocalDate date = LocalDate.parse(dateCheck);

		while (rst.next()) {
			LocalDate start = LocalDate.parse(rst.getString(1));
			LocalDate end = LocalDate.parse(rst.getString(2));

			if (((date.isAfter(start) && date.isBefore(end)) || date.equals(start) || date.equals(end)))
				return false;
		}
		return true;
	}

	ObservableList<String> currentStartList = FXCollections.observableArrayList();

	@FXML
	private void searchServices(KeyEvent event) throws SQLException {

		String currentWord = new String();
		String text = event.getText();

		//System.out.println(DataBase.app.getSpecificService(text));
		service.getSelectionModel().select(DataBase.app.getSpecificService(text));
		service.setValue(DataBase.app.getSpecificService(text));

	}

	@FXML
	private void searchAppointment() throws SQLException {

		if(appointmentDate.getValue()!=null && !service.getSelectionModel().isEmpty()) {
		
		ObservableList<AvailableAppoinment> availableAppointmentsList = FXCollections.observableArrayList();
		appointmentTime.setItems(null);

		String currentService = service.getSelectionModel().getSelectedItem().toString();
		ResultSet rst = DataBase.app.getMedicForSpecificService(currentService);
		String date = appointmentDate.getValue().toString();
		String dayOfWeek = RO.convertToRO(LocalDate.parse(date).getDayOfWeek().toString());
		String currentMedicalUnit = new String();

		while (rst.next()) {

			ObservableList<String> startList = FXCollections.observableArrayList();
			String currentCNP = rst.getString(3);

			Date start = new Date();
			Date end = new Date();

			Calendar calendar = Calendar.getInstance();
			ResultSet specificTimeTable = DataBase.app.getSpecificTimeTable(currentCNP, date);
			ResultSet genericTimeTable = DataBase.app.getGenericSchedule(currentCNP, dayOfWeek);

			if (!checkVacation(currentCNP, date))
				continue;
			else if (specificTimeTable.next()) {
				start = Time.valueOf(specificTimeTable.getString(2));
				end = Time.valueOf(specificTimeTable.getString(3));
				currentMedicalUnit = DataBase.app.getMedicalUnitForSpecificDay(currentCNP, date, "");
			} else if (!genericTimeTable.next())
				continue;
			else if (genericTimeTable.getString(2) == null || genericTimeTable.getString(2).equals("-"))
				continue;
			else {
				start = Time.valueOf(genericTimeTable.getString(2));
				end = Time.valueOf(genericTimeTable.getString(3));
				currentMedicalUnit = DataBase.app.getMedicalUnitForSpecificDay(currentCNP, "", dayOfWeek);
			}

			for (calendar.setTime(start); calendar.getTime().before(end); calendar.add(Calendar.MINUTE, 5)) {

				Calendar aux = Calendar.getInstance();
				aux.setTime(calendar.getTime());
				aux.add(Calendar.MINUTE, DataBase.app.getServiceTime(currentService));

				if (checkIntervals(Time.valueOf(timeFormat.format(calendar.getTime())),
						Time.valueOf(timeFormat.format(aux.getTime())), date, currentCNP))
					startList.add(timeFormat.format(calendar.getTime()).toString());

			}
			if (!startList.isEmpty())
				availableAppointmentsList.add(new AvailableAppoinment(rst.getString(1), rst.getString(2),
						currentMedicalUnit, startList, currentCNP));

		}

		lastName.setCellValueFactory(new PropertyValueFactory<AvailableAppoinment, String>("lastName"));
		firstName.setCellValueFactory(new PropertyValueFactory<AvailableAppoinment, String>("firstName"));
		medicalUnit.setCellValueFactory(new PropertyValueFactory<AvailableAppoinment, String>("medicalUnit"));

		availableAppointmentsTableView.setItems(availableAppointmentsList);
		}
	}

	private boolean checkIntervals(Time start, Time end, String date, String CNP) throws SQLException {

		ResultSet rst = DataBase.app.getAllApointments(CNP, date);
		while (rst.next()) {
			Time startAppointment = rst.getTime(1);
			Time endAppointment = rst.getTime(2);

			if ((start.after(startAppointment) && start.before(endAppointment))
					|| (end.after(startAppointment) && end.before(endAppointment)) || start.equals(startAppointment)
					|| start.equals(endAppointment) || end.equals(startAppointment) || end.equals(endAppointment)) {
				return false;
			}
		}

		return true;
	}

	@FXML
	private void addAppointment() throws SQLException {

		if (txtPatientFitrstName.getText().toString().length() == 0
				|| txtPatientLastName.getText().toString().length() == 0 || appointmentDate.getValue() == null
				|| service.getValue() == null) {
			MessageController.displayMessageLabel(errorLabel, "Completati toate campurile!", Color.RED);
		}else if (appointmentTime.getSelectionModel().isEmpty()) {
			MessageController.displayMessageLabel(errorLabel, "Setati o ora", Color.RED);
		} else {
			String start = appointmentTime.getSelectionModel().getSelectedItem().toString();
			Time startTime = Time.valueOf(start);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			calendar.add(Calendar.MINUTE, DataBase.app.getServiceTime(service.getSelectionModel().getSelectedItem().toString()));
			String end = timeFormat.format(calendar.getTime());
			String medicCNP = availableAppointmentsTableView.getSelectionModel().getSelectedItem().getMedicCNP();
			String unit = availableAppointmentsTableView.getSelectionModel().getSelectedItem().getMedicalUnit();
			String serviceId = DataBase.app.getServiceId(service.getSelectionModel().getSelectedItem().toString());

			DataBase.app.insertAppointment(txtPatientLastName.getText(), txtPatientFitrstName.getText(), start, end,
					appointmentDate.getValue().toString(), medicCNP, serviceId, unit);
			initializeAppointmentTableView();
			searchAppointment();
		}
	}

	@FXML
	private void setAppoinmtmentTimeComboBox() {
		if (!availableAppointmentsTableView.getSelectionModel().isEmpty()) {
			appointmentTime
					.setItems(availableAppointmentsTableView.getSelectionModel().getSelectedItem().getStartList());
		}

	}

	@FXML
	private void backButton() {
		Main.exitAppointmentStage();
	}

	@FXML
	private void initializeAppointmentTableView() throws SQLException {
		String datePicker = new String();
		String serviceComboBox = new String();

		if (date.getValue() == null)
			datePicker = "";
		else
			datePicker = date.getValue().toString();

		if (serviceSearch.getSelectionModel().getSelectedItem() == null)
			serviceComboBox = "";
		else
			serviceComboBox = serviceSearch.getSelectionModel().getSelectedItem().toString();

		ResultSet rst = DataBase.app.getAllAppointmentsData(txtDoctorLastName.getText(), txtDoctorFirstName.getText(), "",
				txtPatientLastNameSearch.getText(), txtPatientFirstNameSearch.getText(), datePicker, serviceComboBox,
				"");

		ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

		while (rst.next()) {
			LocalDate currentDate = LocalDate.parse(rst.getString(7));
			if (LocalDate.now().equals(currentDate) || LocalDate.now().isBefore(currentDate))
				appointmentsList.add(new Appointment(rst.getString(1), rst.getString(2), rst.getString(3),
						rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8),
						rst.getString(9), "nu", ""));
		}
		doctorLastName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorLastName"));
		doctorFirstName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorFirstName"));
		patientLastName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientLastName"));
		patientFirstName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientFirstName"));
		serviceColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("service"));
		medicalUnitColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("medicalUnit"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));

		appointmentsTableView.setItems(appointmentsList);

	}

	@FXML
	private void reset() throws SQLException {
		txtDoctorFirstName.setText("");
		txtDoctorLastName.setText("");
		txtPatientFirstNameSearch.setText("");
		txtPatientLastNameSearch.setText("");
		serviceSearch.setValue(null);
		date.setValue(null);
		initializeAppointmentTableView();
	}

	@FXML
	private void deleteAppointment() throws SQLException {
		DataBase.app.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getCNP(),
				appointmentsTableView.getSelectionModel().getSelectedItem().getDate(),
				appointmentsTableView.getSelectionModel().getSelectedItem().getStart());
		initializeAppointmentTableView();
	}
}
