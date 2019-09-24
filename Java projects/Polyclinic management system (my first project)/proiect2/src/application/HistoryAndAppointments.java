package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class HistoryAndAppointments {

	@FXML
	private TextField txtPatientCNP;
	@FXML
	private TableView<RaportSearch> raportsDate;
	@FXML
	private TableColumn<RaportSearch,String> idRaport;
	@FXML
	private TableColumn<RaportSearch,String> dateRaport; 
	@FXML
	private TableColumn<RaportSearch,String> typeRaport; 
	@FXML
	private TableView<Appointment> appointmentsTableView;
	@FXML
	private TableColumn<Appointment, String> patientLastName;
	@FXML
	private TableColumn<Appointment, String> patientFirstName;
	@FXML
	private TableColumn<Appointment, String> serviceColumn;
	@FXML
	private TableColumn<Appointment, String> timeColumn;
	@FXML
	private TableColumn<Appointment, String> stateColumn;

	

	@FXML
	private void initialize() throws SQLException {
		initializeAppointmentTableView();

	}

	@FXML
	private void initializeReportListView() throws SQLException {
	
		ResultSet rst=DataBase.app.getReportDates(txtPatientCNP.getText());
		ObservableList<RaportSearch> raportSearchList=FXCollections.observableArrayList();
		
		while(rst.next()) {
			raportSearchList.add(new RaportSearch(rst.getInt(1), rst.getString(2), rst.getString(3)));
		}
		idRaport.setCellValueFactory(new PropertyValueFactory<RaportSearch, String>("idRaport"));
		dateRaport.setCellValueFactory(new PropertyValueFactory<RaportSearch, String>("dateRaport"));
		typeRaport.setCellValueFactory(new PropertyValueFactory<RaportSearch, String>("typeRaport"));
	
		raportsDate.setItems(raportSearchList);
		
		
	}

	private void initializeAppointmentTableView() throws SQLException {

		ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getAllAppointmentsData("", "", DataBase.app.getCNPUser(Main.getUserName()), "", "", LocalDate.now().toString(), "", "");

		while (rst.next()) {
			String patientCNP="";
			if(rst.getString("prezentare").equals("da")) {
				patientCNP=rst.getString("CNP_pacient");
			}
			
			appointmentsList.add(new Appointment("", "", rst.getString("nume_pacient"),
					rst.getString("prenume_pacient"), rst.getString("nume_investigatie"), "",
					LocalDate.now().toString(), "", patientCNP, rst.getString("prezentare"),rst.getString("perioada_inceput")));
		}
		patientLastName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientLastName"));
		patientFirstName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientFirstName"));
		serviceColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("service"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("state"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("time"));

		appointmentsTableView.setItems(appointmentsList);
	}
	
	@FXML
	private void backButton() {
		Main.exitHistoryAndAppointmentsStage();
	}
	@FXML
	private void getHistory() throws SQLException {
		
		if(!appointmentsTableView.getSelectionModel().isEmpty()) {
			
			if(appointmentsTableView.getSelectionModel().getSelectedItem().getState().equals("da")) {
	
				txtPatientCNP.setText(appointmentsTableView.getSelectionModel().getSelectedItem().getCNP());
				initializeReportListView();
			}
		}
	}
	
	@FXML
	private void viewRaportButton() throws IOException {
		if(!raportsDate.getSelectionModel().isEmpty())
		{
			Main.setCurrentRaportSearch(raportsDate.getSelectionModel().getSelectedItem());
			Main.showRaportViewStage();
		}
	}
}
