package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RaportController {

	@FXML
	private TextField txtPatientLastName;
	@FXML
	private TextField txtPatientFirstName;
	@FXML
	private TextField txtPatientCNP;
	@FXML
	private TextField txtRecDoctorLastName;
	@FXML
	private TextField txtRecDoctorFirstName;
	@FXML
	private TextField txtNurseLastName;
	@FXML
	private TextField txtNurseFirstName;
	@FXML
	private TextArea txtSimptoms;
	@FXML
	private TextArea txtRecommendation;
	@FXML
	private TextArea txtDiagnostic;
	@FXML
	private ComboBox<String> services;
	@FXML
	private TableView<Service> servicesTableView;
	@FXML
	private TableColumn<Service, String> investigation;
	@FXML
	private TableColumn<Service, TextArea> result;
	@FXML
	private TextField txtSeal;
	@FXML
	private Label titleRaport;
	@FXML
	private HBox medicRaportHBox;
	@FXML
	private VBox medicRaportVBox;
	@FXML
	private TableView<UnfinishedReport> unfinishedReportTableView;
	@FXML
	private TableColumn<UnfinishedReport, String> idRaport;
	@FXML
	private TableColumn<UnfinishedReport, String> patientCNP;
	@FXML
	private TableColumn<UnfinishedReport, String> patientLastName;
	@FXML
	private TableColumn<UnfinishedReport, String> patientFirstName;
	@FXML
	private TextField txtSearchPatientCNP;
	@FXML
	private TextField txtSearchPatientLastName;
	@FXML
	private TextField txtSearchPatientFirstName;
	@FXML
	private Label errorLabel;
	@FXML
	private ComboBox<String> specialitiesComboBox;

	private ObservableList<Service> servicesList = FXCollections.observableArrayList();



	private String typeRaport;

	private boolean newRaport = true;

	Integer currentIdRaport = null;

	@FXML
	private void initialize() throws SQLException {

		initializeServiceTableView();

		if (DataBase.app.getFunctionUser(DataBase.app.getCNPUser(Main.getUserName())).equals("Medic")
				|| DataBase.app.getTypeUser(Main.getUserName()).equals("Administrator")
				|| DataBase.app.getTypeUser(Main.getUserName()).equals("Super-administrator")) {
			titleRaport.setText("Raport medical");
			typeRaport = "RM";
			initializeUnfinishedReportTableView();

			ResultSet rst = DataBase.app.getMedicSpecialitiesNames(DataBase.app.getCNPUser(Main.getUserName()));
			ObservableList<String> specialitiesList = FXCollections.observableArrayList();

			while (rst.next()) {
				specialitiesList.add(rst.getString(1));
			}

			specialitiesComboBox.setItems(specialitiesList);

		} else if (DataBase.app.getFunctionUser(DataBase.app.getCNPUser(Main.getUserName())).equals("Asistent medical")) {
			titleRaport.setText("Raport analize");
			services.setItems(DataBase.app.getFilteredServices("asistent medical"));
			typeRaport = "RA";
			initializeUnfinishedReportTableView();
			medicRaportHBox.getChildren().clear();
			medicRaportVBox.getChildren().clear();
			txtSeal.managedProperty().bind(txtSeal.visibleProperty());
			txtSeal.setVisible(false);
		}

	}

	@FXML
	public void initializeServicesComboBox() throws SQLException {
		services.setItems(DataBase.app.getServicesFilteredBySpeciality(
				specialitiesComboBox.getSelectionModel().getSelectedItem().toString()));
	}

	@FXML
	void initializeUnfinishedReportTableView() throws SQLException {

		ObservableList<UnfinishedReport> UnfinishedReportList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getUnfinishedRaports(DataBase.app.getCNPUser(Main.getUserName()), typeRaport,
				txtSearchPatientCNP.getText(), txtSearchPatientLastName.getText(), txtSearchPatientFirstName.getText());

		while (rst.next()) {
			UnfinishedReportList
					.add(new UnfinishedReport(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4)));
		}

		idRaport.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("idRaport"));
		patientCNP.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("patientCNP"));
		patientLastName.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("patientLastName"));
		patientFirstName.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("patientFirstName"));

		unfinishedReportTableView.setItems(UnfinishedReportList);
	}

	@FXML
	private void initializeServiceTableView() {

		investigation.setCellValueFactory(new PropertyValueFactory<Service, String>("investigation"));
		result.setCellValueFactory(new PropertyValueFactory<Service, TextArea>("result"));

		servicesTableView.setItems(servicesList);
	}

	@FXML
	private void addServiceResult() {
		specialitiesComboBox.setDisable(true);
		if (services.getValue() != null && checkSelectedService(services.getSelectionModel().getSelectedItem())) {
			servicesList.add(new Service(services.getValue(), new TextArea()));
		}
	}

	private boolean checkSelectedService(String item) {
		for (Service element : servicesList) {
			//System.out.println(element.getInvestigation());
			if (element.getInvestigation().equals(item))
				return false;
		}
		return true;
	}

	@FXML
	private void deleteServiceResult() {
		servicesList.remove(servicesTableView.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void newRaportButton() throws SQLException {
		txtPatientLastName.setText("");
		txtPatientFirstName.setText("");
		txtPatientCNP.setText("");
		txtRecDoctorFirstName.setText("");
		txtRecDoctorLastName.setText("");
		txtNurseFirstName.setText("");
		txtNurseLastName.setText("");
		txtRecommendation.setText("");
		txtDiagnostic.setText("");
		txtSimptoms.setText("");
		servicesList = FXCollections.observableArrayList();
		initializeServiceTableView();
		services.getSelectionModel().clearSelection();

		txtSearchPatientCNP.setText("");
		txtSearchPatientFirstName.setText("");
		txtSearchPatientLastName.setText("");
		unfinishedReportTableView.getSelectionModel().clearSelection();
		newRaport = true;
		specialitiesComboBox.setDisable(false);
		initializeUnfinishedReportTableView();
	}

	@FXML
	private void initializePatientName() throws SQLException {
		ResultSet rst = DataBase.app.getPatientName(txtPatientCNP.getText());
		if (txtPatientCNP.getLength() == 13 && !DataBase.app.checkPatientCNP(txtPatientCNP.getText())) {
			//System.out.println("caca");
			MessageController.displayMessageLabel(errorLabel, "pacient neinregistrat", Color.RED);
		}
		if (rst.next()) {
			txtPatientLastName.setText(rst.getString(1));
			txtPatientFirstName.setText(rst.getString(2));
		} else {
			txtPatientLastName.setText("");
			txtPatientFirstName.setText("");
		}
	}

	@FXML
	private void saveButton() throws SQLException {
		String medicLastName = null;
		String medicFirstName = null;

		ResultSet rst = DataBase.app.getCurrentUserName(DataBase.app.getCNPUser(Main.getUserName()));
		if (rst.next()) {
			medicLastName = rst.getString(1);
			medicFirstName = rst.getString(2);
		}

		if (txtPatientCNP.getLength() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Completati CNP", Color.RED);
		} else if (!DataBase.app.checkPatientCNP(txtPatientCNP.getText())) {
			MessageController.displayMessageLabel(errorLabel, "pacient neinregistrat", Color.RED);
		} else if (txtPatientCNP.getLength() != 13 || !txtPatientCNP.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Frormat CNP gresit", Color.RED);
		} else if (newRaport) {
			DataBase.app.insertRaport(txtPatientCNP.getText(), medicLastName, medicFirstName, DataBase.app.getCNPUser(Main.getUserName()),
					txtRecDoctorLastName.getText(), txtRecDoctorFirstName.getText(), txtNurseLastName.getText(),
					txtNurseFirstName.getText(), LocalDate.now().toString(), txtSimptoms.getText(),
					txtDiagnostic.getText(), txtRecommendation.getText(), "", typeRaport,
					specialitiesComboBox.getSelectionModel().getSelectedItem());

			for (Service element : servicesList) {
				DataBase.app.insertServiceRaport(DataBase.app.getLastidRaport(), DataBase.app.getServiceId(element.getInvestigation()),
						element.getResult().getText());
			}
			newRaport = false;
			initializeUnfinishedReportTableView();
			currentIdRaport = DataBase.app.getLastidRaport();
			MessageController.displayMessageLabel(errorLabel, "Raport salvat cu succes", Color.GREEN);
		} else {
			DataBase.app.updateRaport(currentIdRaport, txtPatientCNP.getText(), medicLastName, medicFirstName,
					DataBase.app.getCNPUser(Main.getUserName()), txtRecDoctorLastName.getText(), txtRecDoctorFirstName.getText(),
					txtNurseLastName.getText(), txtNurseFirstName.getText(), txtSimptoms.getText(),
					txtDiagnostic.getText(), txtRecommendation.getText(), "", typeRaport);

			ResultSet rstServices = DataBase.app.getReportServices(currentIdRaport);

			while (rstServices.next()) {

				String currentService = rstServices.getString(1);
				//System.out.println(currentService);
				int ok = 0;
				for (Service element : servicesList) {
					if (element.getInvestigation().equals(currentService)) {
						DataBase.app.updateResultServiceFromRaport(currentIdRaport, DataBase.app.getServiceId(currentService),
								element.getResult().getText());
						ok = 1;
					}
				}
				if (ok == 0) {

					DataBase.app.deleteServiceFromRaport(currentIdRaport, DataBase.app.getServiceId(currentService));
				}
			}

			for (Service element : servicesList) {
				rstServices = DataBase.app.getReportServices(currentIdRaport);
				int ok = 1;
				while (rstServices.next()) {
					String currentService = rstServices.getString(1);
					if (element.getInvestigation().equals(currentService)) {
						ok = 0;
						break;
					}
				}
				if (ok == 1) {
					DataBase.app.insertServiceRaport(currentIdRaport, DataBase.app.getServiceId(element.getInvestigation()),
							element.getResult().getText());
				}
			}

			initializeUnfinishedReportTableView();
			MessageController.displayMessageLabel(errorLabel, "Raport salvat cu succes", Color.GREEN);
		}

	}

	@FXML
	private void initializeDataRaport() throws SQLException {

		if (!unfinishedReportTableView.getSelectionModel().isEmpty()) {
			ResultSet rst = DataBase.app
					.getAllRaportsData(unfinishedReportTableView.getSelectionModel().getSelectedItem().getIdRaport());

			if (rst.next()) {
				txtPatientLastName.setText(rst.getString(1));
				txtPatientFirstName.setText(rst.getString(2));
				txtPatientCNP.setText(rst.getString(3));
				txtRecDoctorLastName.setText(rst.getString(7));
				txtRecDoctorFirstName.setText(rst.getString(8));
				if (typeRaport.equals("RM")) {
					txtNurseLastName.setText(rst.getString(9));
					txtNurseFirstName.setText(rst.getString(10));
					txtSimptoms.setText(rst.getString(12));
					txtDiagnostic.setText(rst.getString(13));
					txtRecommendation.setText(rst.getString(14));
					specialitiesComboBox.setValue(rst.getString(18));
					initializeServicesComboBox();
					specialitiesComboBox.setDisable(true);
				}
				rst = DataBase.app.getReportServices(
						unfinishedReportTableView.getSelectionModel().getSelectedItem().getIdRaport());
				servicesList = FXCollections.observableArrayList();

				while (rst.next()) {
					TextArea textArea = new TextArea();
					textArea.setText(rst.getString(2));
					servicesList.add(new Service(rst.getString(1), textArea));
				}

			}

			initializeServiceTableView();
			newRaport = false;
			currentIdRaport = unfinishedReportTableView.getSelectionModel().getSelectedItem().getIdRaport();
		}
	}

	@FXML
	private void submitButton() throws SQLException {
		String medicLastName = null;
		String medicFirstName = null;

		ResultSet rst = DataBase.app.getCurrentUserName(DataBase.app.getCNPUser(Main.getUserName()));
		if (rst.next()) {
			medicLastName = rst.getString(1);
			medicFirstName = rst.getString(2);
		}

		if (txtPatientCNP.getLength() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Completati CNP", Color.RED);
		} else if (!DataBase.app.checkPatientCNP(txtPatientCNP.getText())) {
			MessageController.displayMessageLabel(errorLabel, "pacient neinregistrat", Color.RED);
		} else if (txtPatientCNP.getLength() != 13 || !txtPatientCNP.getText().toString().matches("[0-9]+")) {
			MessageController.displayMessageLabel(errorLabel, "Frormat CNP gresit", Color.RED);
		} else if (typeRaport.equals("RM") && txtSeal.getLength() == 0) {
			MessageController.displayMessageLabel(errorLabel, "Puneti parafa", Color.RED);
		} else if (typeRaport.equals("RM")
				&& !txtSeal.getText().equals(DataBase.app.getMedicSeal(DataBase.app.getCNPUser(Main.getUserName())))) {
			MessageController.displayMessageLabel(errorLabel, "Parafa incorecta", Color.RED);
		} else if (newRaport) {
			DataBase.app.insertRaport(txtPatientCNP.getText(), medicLastName, medicFirstName, DataBase.app.getCNPUser(Main.getUserName()),
					txtRecDoctorLastName.getText(), txtRecDoctorFirstName.getText(), txtNurseLastName.getText(),
					txtNurseFirstName.getText(), LocalDate.now().toString(), txtSimptoms.getText(),
					txtDiagnostic.getText(), txtRecommendation.getText(), txtSeal.getText(), typeRaport,
					specialitiesComboBox.getSelectionModel().getSelectedItem());

			for (Service element : servicesList) {
				DataBase.app.insertServiceRaport(DataBase.app.getLastidRaport(), DataBase.app.getServiceId(element.getInvestigation()),
						element.getResult().getText());
			}
			newRaport = false;
			initializeUnfinishedReportTableView();
			currentIdRaport = DataBase.app.getLastidRaport();
			DataBase.app.setRaportFinished(currentIdRaport);
			MessageController.displayMessageLabel(errorLabel, "Raport finalizat cu succes", Color.GREEN);
			newRaportButton();
		} else {

			DataBase.app.updateRaport(currentIdRaport, txtPatientCNP.getText(), medicLastName, medicFirstName,
					DataBase.app.getCNPUser(Main.getUserName()), txtRecDoctorLastName.getText(), txtRecDoctorFirstName.getText(),
					txtNurseLastName.getText(), txtNurseFirstName.getText(), txtSimptoms.getText(),
					txtDiagnostic.getText(), txtRecommendation.getText(), txtSeal.getText(), typeRaport);

			ResultSet rstServices = DataBase.app.getReportServices(currentIdRaport);

			while (rstServices.next()) {

				String currentService = rstServices.getString(1);
				//System.out.println(currentService);
				int ok = 0;
				for (Service element : servicesList) {
					if (element.getInvestigation().equals(currentService)) {
						DataBase.app.updateResultServiceFromRaport(currentIdRaport, DataBase.app.getServiceId(currentService),
								element.getResult().getText());
						ok = 1;
					}
				}
				if (ok == 0) {

					DataBase.app.deleteServiceFromRaport(currentIdRaport, DataBase.app.getServiceId(currentService));
				}
			}

			for (Service element : servicesList) {
				rstServices = DataBase.app.getReportServices(currentIdRaport);
				int ok = 1;
				while (rstServices.next()) {
					String currentService = rstServices.getString(1);
					if (element.getInvestigation().equals(currentService)) {
						ok = 0;
						break;
					}
				}
				if (ok == 1) {
					DataBase.app.insertServiceRaport(currentIdRaport, DataBase.app.getServiceId(element.getInvestigation()),
							element.getResult().getText());
				}
			}

			DataBase.app.setRaportFinished(currentIdRaport);
			MessageController.displayMessageLabel(errorLabel, "Raport finalizat cu succes", Color.GREEN);
			newRaportButton();
		}
	}

	@FXML
	private void deleteButton() throws SQLException {

		if (!unfinishedReportTableView.getSelectionModel().isEmpty()) {
			DataBase.app.deleteUnfinishedRaport(unfinishedReportTableView.getSelectionModel().getSelectedItem().getIdRaport());
			initializeUnfinishedReportTableView();
			newRaportButton();
		}
	}

	@FXML
	private void backButton() {
		Main.exitRaportStage();
	}

}
