package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.LOCATION_FORWARD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.converter.LocalDateTimeStringConverter;

public class BillController {
	@FXML
	private TableView<UnfinishedReport> unpaidServicesTable;
	@FXML
	private TableColumn<UnfinishedReport, String> idRaport;
	@FXML
	private TableColumn<UnfinishedReport, String> patientLastName;
	@FXML
	private TableColumn<UnfinishedReport, String> patientFirstName;
	@FXML
	private TextArea billArea;
	@FXML
	private TextArea titleArea;
	@FXML
	private TextArea dataArea;
	@FXML
	private TextField txtPatientLastName;
	@FXML
	private TextField txtPatientFirstName;
	@FXML
	private TextField txtPatientCNP;
	@FXML
	private Label printLabel;

	private Double total = new Double(0);


	@FXML
	private void initialize() throws SQLException {
		initializeTableView();

	}

	@FXML
	private void initializeTableView() throws SQLException {
		ObservableList<UnfinishedReport> toBePaidList = FXCollections.observableArrayList();
		ResultSet rst = DataBase.app.getPacientRaport(txtPatientCNP.getText(), txtPatientLastName.getText(),
				txtPatientFirstName.getText());
		while (rst.next()) {

			int currentRaportId = rst.getInt(1);
			ResultSet currentService = DataBase.app.getReportServices(currentRaportId);
			while (currentService.next())
				if (currentService.getString(3) == null) {
					toBePaidList.add(
							new UnfinishedReport(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4)));
					break;
				}
		}
		idRaport.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("idRaport"));
		patientLastName.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("patientLastName"));
		patientFirstName.setCellValueFactory(new PropertyValueFactory<UnfinishedReport, String>("patientFirstName"));

		unpaidServicesTable.setItems(toBePaidList);
	}

	@FXML
	private void initializeBillArea() throws SQLException {
		total = 0.0;
		titleArea.setText("");
		billArea.setText("");
		dataArea.setText("");
		List<ResultSet> medicData = new ArrayList<ResultSet>();

		if (!unpaidServicesTable.getSelectionModel().isEmpty()) {
			titleArea.setFont(Font.font("Times New Roman", 20));
			dataArea.setFont(Font.font("Times New Roman", 14));
			titleArea.appendText("---------------------------------------------------\n");
			titleArea.appendText("Va multumim ca ne-ati ales!\n");
			titleArea.appendText("POLICLINICA <<Ca la tigani>>!\n");
			titleArea.appendText("---------------------------------------------------\n");
			titleArea.appendText("BON FISCAL");
			Integer currentRaportId = unpaidServicesTable.getSelectionModel().getSelectedItem().getIdRaport();
			String currentRaportType = null;
			String currentTime = LocalDateTime.now().toString();
			ResultSet currentUser = DataBase.app.getCurrentUserName(DataBase.app.getCNPUser(Main.getUserName()));
			String currentUserName = null;
			while (currentUser.next()) {
				currentUserName = currentUser.getString(1) + " " + currentUser.getString(2);
			}

			ResultSet rstRaportData = DataBase.app.getAllRaportsData(currentRaportId);
			if (rstRaportData.next()) {

				currentRaportType = rstRaportData.getString(17);
				//System.out.println(currentRaportType);

				if (currentRaportType.equals("RM")) {

					total = 0.0;
					String currentRaportMedicCNP = rstRaportData.getString(6);
					medicData = DataBase.app.getAllUserData(currentRaportMedicCNP, "Medic");
					String currentMedicGrade = medicData.get(1).getString(2);
					String currentMedicName = rstRaportData.getString(4) + " " + rstRaportData.getString(5);
					ResultSet rst = DataBase.app.getReportServices(currentRaportId);
					while (rst.next()) {

						if (rst.getString(3) == null) {
							Double price = new Double(0);
							if (currentMedicGrade.equals("Specialist")) {
								price = rst.getDouble(4);
							} else {
								price = rst.getDouble(4) * 0.1;

							}
							total += price;
							billArea.appendText(rst.getString(1) + "\n");
							billArea.appendText(price + "\n");

							// DataBase.app.payService(currentRaportId, rst.getString(5));
							//System.out.println(total);
						}
					}
					dataArea.appendText("TOTAL \t\t\t\t\t\t\t\t\t " + total + "\n\n\n");
					dataArea.appendText("---------------------------------------------------\n");
					dataArea.appendText("Medic " + currentMedicGrade + " " + currentMedicName + "\n");
				} else {
					total = 0.0;
					String currentNurseName = rstRaportData.getString(4) + " " + rstRaportData.getString(5);
					ResultSet rst = DataBase.app.getReportServices(currentRaportId);
					while (rst.next()) {

						if (rst.getString(3) == null) {
							Double price = rst.getDouble(4);
							total += price;
							billArea.appendText(rst.getString(1) + "\n");
							billArea.appendText(price + "\n");
							//System.out.println(total);
						}
					}
					dataArea.appendText("TOTAL \t\t\t\t\t\t\t\t\t " + total + "\n\n\n");
					dataArea.appendText("------------------------------------------------------------------\n");
					dataArea.appendText("Asistent medical " + currentNurseName + "\n");
				}
				dataArea.appendText("RECEPTIONER \t" + currentUserName + "\n");
				dataArea.appendText(currentTime + "\n");
			}
		}
	}

	@FXML
	private void buttonBack() {
		Main.exitBillStage();
	}

	@FXML
	private void printButton() throws SQLException {
		String currentAuthorCNP = null;
		if (!unpaidServicesTable.getSelectionModel().isEmpty()) {
			Integer currentRaportId = unpaidServicesTable.getSelectionModel().getSelectedItem().getIdRaport();
			ResultSet rst = DataBase.app.getReportServices(currentRaportId);
			ResultSet rstRaportData = DataBase.app.getAllRaportsData(currentRaportId);
			String date = new String();
			String speciality = new String();

			if (rstRaportData.next()) {
				currentAuthorCNP = rstRaportData.getString(6);
				//System.out.println(currentAuthorCNP);
				date = rstRaportData.getString(11);
				speciality = rstRaportData.getString(18);
			}

			while (rst.next()) {

				DataBase.app.payService(currentRaportId, rst.getString(5));
			}
			MessageController.displayMessageLabel(printLabel, "Bon printat cu succes!", Color.GREEN);

			String dayOfWeek = RO.convertToRO(LocalDate.parse(date).getDayOfWeek().toString());
			ResultSet specificTimeTable = DataBase.app.getSpecificTimeTable(currentAuthorCNP, date);

			String medicalUnit = new String();

			if (specificTimeTable.next())
				medicalUnit = DataBase.app.getMedicalUnitForSpecificDay(currentAuthorCNP, date, "");
			else
				medicalUnit = DataBase.app.getMedicalUnitForSpecificDay(currentAuthorCNP, "", dayOfWeek);

			DataBase.app.processBill(currentAuthorCNP, total,/*LocalDate.now().toString()*/"2018-12-18", medicalUnit, speciality);
			initializeTableView();
			initializeBillArea();
		}
	}
}
