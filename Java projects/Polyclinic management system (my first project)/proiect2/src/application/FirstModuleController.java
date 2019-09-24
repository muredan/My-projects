package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

import javax.lang.model.element.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalDateStringConverter;

public class FirstModuleController {

	@FXML
	private TextField txtNume;

	@FXML
	private TextField txtPrenume;

	@FXML
	private TextField txtFunction;

	@FXML
	private TableView<TimeTable> genericTimeTable;
	@FXML
	private TableColumn<TimeTable, String> day;
	@FXML
	private TableColumn<TimeTable, String> start;
	@FXML
	private TableColumn<TimeTable, String> end;
	@FXML
	private TableColumn<TimeTable, String> medicalUnit;

	@FXML
	private TableView<TimeTable> specificTimeTable;
	@FXML
	private TableColumn<TimeTable, String> specificDate;
	@FXML
	private TableColumn<TimeTable, String> specificStart;
	@FXML
	private TableColumn<TimeTable, String> specificEnd;
	@FXML
	private TableColumn<TimeTable, String> specificMedicalUnit;

	@FXML
	private DatePicker dateSpecific;
	@FXML
	private TextField txtStartSpecific;
	@FXML
	private TextField txtEndSpecific;
	@FXML
	private TextField txtMedicalUnintSpecific;

	@FXML
	private TableView<Vacation> vacation;
	@FXML
	private TableColumn<Vacation, String> startVacation;
	@FXML
	private TableColumn<Vacation, String> endVacation;

	@FXML
	private DatePicker dateStartVacation;
	@FXML
	private DatePicker dateEndtVacation;

	@FXML
	private Button modifyGenericTimeTable;

	@FXML
	private VBox searchBox;
	@FXML
	private VBox addSpecificTimeTableBox;
	@FXML
	private VBox addVactionBox;
	@FXML
	private VBox SpecificTimeTableBox;

	@FXML
	private Label errorLabel1;
	@FXML
	private Label errorLabel2;
	@FXML
	private Label errorLabel3;

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
	ObservableList<TimeTable> genericTimeTableList = FXCollections.observableArrayList();
	@FXML
	ObservableList<TimeTable> specificTimeTableList = FXCollections.observableArrayList();
	@FXML
	ObservableList<Vacation> vacationList = FXCollections.observableArrayList();

	private ObservableList<String> results;

	private String CNP = "";
	private String currentCNP;
	private Color green = javafx.scene.paint.Color.GREEN;
	private Color red = javafx.scene.paint.Color.RED;

	@FXML
	private void initialize() throws SQLException {

		modifyGenericTimeTable.managedProperty().bind(modifyGenericTimeTable.visibleProperty());
		searchBox.managedProperty().bind(searchBox.visibleProperty());
		addSpecificTimeTableBox.managedProperty().bind(addSpecificTimeTableBox.visibleProperty());
		addVactionBox.managedProperty().bind(addVactionBox.visibleProperty());
		SpecificTimeTableBox.managedProperty().bind(SpecificTimeTableBox.visibleProperty());

		String userName = Main.getUserName();
		String userCNP = DataBase.app.getCNPUser(userName);
		String function = DataBase.app.getFunctionUser(userCNP);
		String userType = DataBase.app.getTypeUser(userName);

		if (function.equals("Inspector resurse umane") || userType.equals("Administrator")
				|| userType.equals("Super-administrator")) {
			setVisible(true);
			showSeaerchRezult();
		} else {
			setVisible(false);
			if (function.equals("Expert financiar") || function.equals("Inspector resurse umane")) {
				SpecificTimeTableBox.setVisible(false);
			}
			initializeGenericTimeTable(userCNP);
			initializeSpecificTimeTable(userCNP);
			initializeVacation(userCNP);
			specificTimeTable.setEditable(false);
			genericTimeTable.setEditable(false);
		}

	}

	private void setVisible(boolean visible) {
		searchBox.setVisible(visible);
		modifyGenericTimeTable.setVisible(visible);
		addSpecificTimeTableBox.setVisible(visible);
		addVactionBox.setVisible(visible);

	}

	@FXML
	private void initializeTableView() throws SQLException {

		if (!userSearchTableView.getSelectionModel().isEmpty()) {

			currentCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
			//System.out.println(CNP + " " + currentCNP);
			if (!CNP.equals(currentCNP)) {

				initializeGenericTimeTable(currentCNP);
				initializeSpecificTimeTable(currentCNP);
				initializeVacation(currentCNP);

				CNP = currentCNP;
			}
		}

	}

	private void initializeGenericTimeTable(String CNP) throws SQLException {
		genericTimeTableList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getGenericSchedule(CNP, "");
		while (rst.next()) {
			String rstMedicalUnit = rst.getString(4);

			if (rst.getString(4) != null)
				if (rstMedicalUnit.equals("0"))
					rstMedicalUnit = "-";

			genericTimeTableList
					.add(new TimeTable(rst.getString(1), rst.getString(2), rst.getString(3), rstMedicalUnit));
		}

		genericTimeTable.setEditable(true);

		day.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("day"));

		start.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("start"));
		start.setCellFactory(TextFieldTableCell.forTableColumn());
		start.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setStart(event.getNewValue());

			}
		});

		end.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("end"));
		end.setCellFactory(TextFieldTableCell.forTableColumn());
		end.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setEnd(event.getNewValue());

			}
		});

		medicalUnit.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("medicalUnit"));
		medicalUnit.setCellFactory(TextFieldTableCell.forTableColumn());
		medicalUnit.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow())
						.setMedicalUnit(event.getNewValue());

			}
		});

		genericTimeTable.setItems(genericTimeTableList);
	}

	private void initializeSpecificTimeTable(String CNP) throws SQLException {
		specificTimeTableList = FXCollections.observableArrayList();

		ResultSet rst2 = DataBase.app.getSpecificTimeTable(CNP, "");
		// //System.out.println(rst2.getString(1)+rst2.getString(2)+rst2.getString(3)+rst2.getString(4));
		while (rst2.next()) {

			specificTimeTableList
					.add(new TimeTable(rst2.getString(1), rst2.getString(2), rst2.getString(3), rst2.getString(4)));
			//System.out.println(rst2.getString(1) + rst2.getString(2) + rst2.getString(3) + rst2.getString(4));
		}

		specificTimeTable.setEditable(true);

		specificDate.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("day"));

		specificStart.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("start"));
		specificStart.setCellFactory(TextFieldTableCell.forTableColumn());
		specificStart.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setStart(event.getNewValue());

			}
		});

		specificEnd.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("end"));
		specificEnd.setCellFactory(TextFieldTableCell.forTableColumn());
		specificEnd.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setEnd(event.getNewValue());

			}
		});

		specificMedicalUnit.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("medicalUnit"));
		specificMedicalUnit.setCellFactory(TextFieldTableCell.forTableColumn());
		specificMedicalUnit.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TimeTable, String>>() {

			@Override
			public void handle(CellEditEvent<TimeTable, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow())
						.setMedicalUnit(event.getNewValue());

			}
		});

		specificTimeTable.setItems(specificTimeTableList);
	}

	@FXML
	private void initializeVacation(String CNP) throws SQLException {

		vacationList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getVacation(CNP);
		while (rst.next()) {
			vacationList.add(new Vacation(rst.getString(1), rst.getString(2)));
		}

		startVacation.setCellValueFactory(new PropertyValueFactory<Vacation, String>("start"));
		endVacation.setCellValueFactory(new PropertyValueFactory<Vacation, String>("end"));

		vacation.setItems(vacationList);
	}

	@FXML
	private void addVacationButton() throws SQLException {

		LocalDate currentdate = LocalDate.now();
		if (DataBase.app.checkGenericSchedule(currentCNP)) {
			MessageController.displayMessageLabel(errorLabel3, "nu exista orar generic", red);
		} else if (dateStartVacation.getValue() == null || dateEndtVacation.getValue() == null) {
			MessageController.displayMessageLabel(errorLabel3, "completati toate spatiile", red);
		} else if (dateStartVacation.getValue().isAfter(dateEndtVacation.getValue())) {
			MessageController.displayMessageLabel(errorLabel3, "interval gresit", red);
		} else if (dateStartVacation.getValue().isBefore(currentdate)
				|| dateStartVacation.getValue().isEqual(currentdate)) {
			MessageController.displayMessageLabel(errorLabel3, "intervalul nu corespunde cu data curenta", red);
		} else if (!checkVacationIntervals(dateStartVacation.getValue().toString(),
				dateEndtVacation.getValue().toString())) {
			MessageController.displayMessageLabel(errorLabel3, "intervalul se intersecteaza", red);
		} else {
			MessageController.displayMessageLabel(errorLabel3, "adugarea realizata cu succes", green);
			DataBase.app.insertVacation(currentCNP, dateStartVacation.getValue().toString(),
					dateEndtVacation.getValue().toString());
			initializeVacation(currentCNP);
		}
	}

	@FXML
	private void deleteVacationButton() throws SQLException {
		Vacation element = vacation.getSelectionModel().getSelectedItem();
		DataBase.app.deleteVacation(currentCNP, element.getStart());
		initializeVacation(currentCNP);
	}

	@FXML
	private void addSpecificButton() throws SQLException {

		String formatTime = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9]";
		String currenFunction = DataBase.app.getFunctionUser(currentCNP);

		if (DataBase.app.checkGenericSchedule(currentCNP)) {
			MessageController.displayMessageLabel(errorLabel2, "nu exista orar generic", red);
		} else if (currenFunction.equals("Expert financiar") || currenFunction.equals("Inspector resurse umane")) {
			MessageController.displayMessageLabel(errorLabel2, "Functia nu permite orar specific", red);
		} else if (txtStartSpecific.getText().toString().length() == 0
				|| txtEndSpecific.getText().toString().length() == 0
				|| txtMedicalUnintSpecific.getText().toString().length() == 0 || dateSpecific.getValue() == null) {
			MessageController.displayMessageLabel(errorLabel2, "completati toate spatiile", red);
		} else if (!txtStartSpecific.getText().toString().matches(formatTime)
				|| !txtEndSpecific.getText().toString().matches(formatTime)) {
			MessageController.displayMessageLabel(errorLabel2, "format incorect (hh:mm:ss)", red);
		} else if (!DataBase.app.checkMedicalUnit(txtMedicalUnintSpecific.getText().toString())
				|| txtMedicalUnintSpecific.getText().toString().equals("0")) {
			MessageController.displayMessageLabel(errorLabel2, "nu exista uninitatea medicala", red);
		} else if (!dateSpecific.getValue().toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
			MessageController.displayMessageLabel(errorLabel2, "format incorect data (mm/dd/yyyy)", red);

		} else if (!checkTimeIntervals(txtMedicalUnintSpecific.getText(),
				RO.convertToRO(dateSpecific.getValue().getDayOfWeek().toString()),
				Time.valueOf(txtStartSpecific.getText()), Time.valueOf(txtEndSpecific.getText()))) {
			MessageController.displayMessageLabel(errorLabel2, "intervalul nu corespunde cu unitatea medicala", red);
		} else {
			MessageController.displayMessageLabel(errorLabel2, "adaugarea a fost realizata cu succes", green);
			DataBase.app.insertSpecificTimeTable(currentCNP, dateSpecific.getValue().toString(), txtStartSpecific.getText(),
					txtEndSpecific.getText(), txtMedicalUnintSpecific.getText());
			initializeSpecificTimeTable(currentCNP);
		}
	}

	@FXML
	void deleteSpecificButton() throws SQLException {
		TimeTable element = specificTimeTable.getSelectionModel().getSelectedItem();
		DataBase.app.deleteSpecificTimeTable(currentCNP, element.getDay());
		initializeSpecificTimeTable(currentCNP);
	}

	@FXML
	private void modifySpecificButton() throws SQLException {

		int ok = 1;
		String formatTime = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9]";

		for (TimeTable element : specificTimeTableList) {

			if (element.getMedicalUnit() == null || element.getStart() == null || element.getEnd() == null
					|| element.getMedicalUnit().length() == 0 || element.getStart().length() == 0
					|| element.getEnd().length() == 0) {
				MessageController.displayMessageLabel(errorLabel1, "completati toate spatiile", red);
				ok = 0;
				break;
			}
			if (!DataBase.app.checkMedicalUnit(element.getMedicalUnit()) || element.getMedicalUnit().equals("0")) {
				MessageController.displayMessageLabel(errorLabel2,
						"nu exista unitatea medicala pentru data: " + element.getDay(), red);
				ok = 0;
				break;
			}

			if (!element.getStart().matches(formatTime) || !element.getEnd().matches(formatTime)) {
				MessageController.displayMessageLabel(errorLabel2,
						"format incorect (hh:mm:ss) pentru data: " + element.getDay(), red);
				ok = 0;
				break;
			}

			if (!checkTimeIntervals(element.getMedicalUnit(),
					RO.convertToRO(LocalDate.parse(element.getDay()).getDayOfWeek().toString()),
					Time.valueOf(element.getStart()), Time.valueOf(element.getEnd()))) {
				MessageController.displayMessageLabel(errorLabel2,
						"intervalul nu corespunde pentru ziua: " + element.getDay(), red);
				ok = 0;
				break;
			}

		}

		if (ok == 1) {
			MessageController.displayMessageLabel(errorLabel2, "modificarea realizata cu succes", green);
			for (TimeTable element : specificTimeTableList) {
				// //System.out.println(element.getDay()+" "+element.getStart()+" "+"
				// "+element.getEnd()+" "+element.getMedicalUnit());
				DataBase.app.updateSpecificTimeTable(CNP, element.getDay(), element.getStart(), element.getEnd(),
						element.getMedicalUnit());
			}
		}
	}

	private boolean checkTimeIntervals(String MU, String day, Time start, Time end) throws SQLException {

		ResultSet rst = DataBase.app.getMedicalUnitTimeTable(MU, day);
		Time startMU = rst.getTime(1);
		Time endMU = rst.getTime(2);
		if (startMU == null || endMU == null)
			return false;
		if ((start.after(startMU) || start.equals(startMU)) && start.before(endMU)
				&& (end.before(endMU) || end.equals(endMU)) && end.after(startMU) && !start.equals(end)
				&& start.before(end))
			return true;
		return false;
	}

	private boolean checkVacationIntervals(String start, String end) throws SQLException {

		for (Vacation element : vacationList) {
			if ((LocalDate.parse(start).isAfter(LocalDate.parse(element.getStart()))
					&& LocalDate.parse(start).isBefore(LocalDate.parse(element.getEnd())))
					|| (LocalDate.parse(end).isAfter(LocalDate.parse(element.getStart()))
							&& LocalDate.parse(end).isBefore(LocalDate.parse(element.getEnd())))
					|| LocalDate.parse(start).equals(LocalDate.parse(element.getStart()))
					|| LocalDate.parse(start).equals(LocalDate.parse(element.getEnd()))
					|| LocalDate.parse(end).equals(LocalDate.parse(element.getStart()))
					|| LocalDate.parse(end).equals(LocalDate.parse(element.getEnd())))
				return false;

		}

		return true;
	}

	@FXML
	private void modifyButton() throws SQLException {

		int ok = 1;
		String formatTime = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9]";

		for (TimeTable element : genericTimeTableList) {

			if (element.getMedicalUnit() == null || element.getStart() == null || element.getEnd() == null
					|| element.getMedicalUnit().length() == 0 || element.getStart().length() == 0
					|| element.getEnd().length() == 0) {
				MessageController.displayMessageLabel(errorLabel1, "completati toate spatiile", red);
				ok = 0;
				break;
			}
			if (!DataBase.app.checkMedicalUnit(element.getMedicalUnit())) {
				MessageController.displayMessageLabel(errorLabel1,
						"nu exista unitatea medicala pentru ziua: " + element.getDay(), red);
				ok = 0;
				break;
			}

			if (!(element.getMedicalUnit().equals("-") || element.getStart().equals("-")
					|| element.getEnd().equals("-"))) {

				if (!element.getStart().matches(formatTime) || !element.getEnd().matches(formatTime)) {
					MessageController.displayMessageLabel(errorLabel1,
							"format incorect (hh:mm:ss) pentru ziua: " + element.getDay(), red);
					ok = 0;
					break;
				}

				if (!checkTimeIntervals(element.getMedicalUnit(), element.getDay(), Time.valueOf(element.getStart()),
						Time.valueOf(element.getEnd()))) {
					MessageController.displayMessageLabel(errorLabel1,
							"intervalul nu corespunde pentru ziua: " + element.getDay(), red);
					ok = 0;
					break;
				}
			}
		}

		if (ok == 1) {
			MessageController.displayMessageLabel(errorLabel1, "modificarea realizata cu succes", green);
			for (TimeTable element : genericTimeTableList)
				DataBase.app.updateGenericTimeTable(CNP, element.getDay(), element.getStart(), element.getEnd(),
						element.getMedicalUnit());
		}
	}

	@FXML
	private void backButton() throws IOException {
		Main.showMenu(Main.getUserName());
		Main.exitFirstModule();

	}

	@FXML
	private void showSeaerchRezult() throws SQLException {

		ObservableList<UserSearch> userSearchList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getUserDataForSearch(txtNume.getText(), txtPrenume.getText(), txtFunction.getText());

		while (rst.next()) {
			userSearchList.add(new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
		}

		userCNP.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userCNP"));
		userLastName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userLastName"));
		userFirstName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFirstName"));
		userFunction.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFunction"));

		userSearchTableView.setItems(userSearchList);
	}

}
