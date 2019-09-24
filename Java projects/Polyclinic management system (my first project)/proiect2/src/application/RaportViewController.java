package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RaportViewController {
	@FXML
	private TextField txtPatientLastName;
	@FXML
	private TextField txtPatientFirstName;
	@FXML
	private TextField txtPatientCNP;
	@FXML
	private TextField txtDoctorLastName;
	@FXML
	private TextField txtDoctorFirstName;
	@FXML
	private TextField txtRecDoctorLastName;
	@FXML
	private TextField txtRecDoctorFirstName;
	@FXML
	private TextField txtNurseLastName;
	@FXML
	private TextField txtNurseFirstName;
	@FXML
	private TextField txtNurseLastName1;
	@FXML
	private TextField txtNurseFirstName1;
	@FXML
	private TextField txtSeal;
	@FXML
	private TextArea txtSimptoms;
	@FXML
	private TextArea txtRecommendation;
	@FXML
	private TextArea txtDiagnostic;
	@FXML
	private TableView<Service> servicesTableView;
	@FXML
	private TableColumn<Service, String> investigation;
	@FXML
	private TableColumn<Service, TextArea> result;
	@FXML
	private VBox medicRaportVBox;
	@FXML
	private VBox nurseRaportVBox;
	@FXML
	private VBox optionalNurseRaportVBox;
	@FXML
	private HBox medicRaportHBox;
	@FXML
	private HBox parafaRaportHBox;
	@FXML
	private Label title;
    
	private ObservableList<Service> servicesList=FXCollections.observableArrayList();
	

	
	
	@FXML
	private void initialize() throws SQLException
	{
		ResultSet rst=DataBase.app.getAllRaportsData(Main.getCurrentRaportSearch().getIdRaport());
		String function=DataBase.app.getFunctionUser(DataBase.app.getCNPUser(Main.getUserName()));
		if(Main.getCurrentRaportSearch().getTypeRaport().equals("RM") && rst.next())
		{
			title.setText("Raport medical");
			nurseRaportVBox.getChildren().clear();
			txtPatientLastName.setText(rst.getString(1));
			txtPatientFirstName.setText(rst.getString(2));
			txtPatientCNP.setText(rst.getString(3));
			txtDoctorLastName.setText(rst.getString(4));
			txtDoctorFirstName.setText(rst.getString(5));
			txtRecDoctorLastName.setText(rst.getString(7));
			txtRecDoctorFirstName.setText(rst.getString(8));
		    txtNurseLastName1.setText(rst.getString(9));
			txtNurseFirstName1.setText(rst.getString(10));
			txtSimptoms.setText(rst.getString(12));
			txtRecommendation.setText(rst.getString(14));
		    txtDiagnostic.setText(rst.getString(13));
		    txtSeal.setText(rst.getString(15));
			
		}
		else {
			if(Main.getCurrentRaportSearch().getTypeRaport().equals("RA") && rst.next())
			{
				title.setText("Raport analize");
				medicRaportHBox.getChildren().clear();
				medicRaportVBox.getChildren().clear();
				optionalNurseRaportVBox.getChildren().clear();
				parafaRaportHBox.getChildren().clear();
				txtPatientLastName.setText(rst.getString(1));
				txtPatientFirstName.setText(rst.getString(2));
				txtPatientCNP.setText(rst.getString(3));
				txtRecDoctorLastName.setText(rst.getString(7));
				txtRecDoctorFirstName.setText(rst.getString(8));
			    txtNurseLastName.setText(rst.getString(4));
				txtNurseFirstName.setText(rst.getString(5));
				
			}
		}
		intializeServiceTableView();
	}
	@FXML
	private void intializeServiceTableView() throws SQLException {
		
		
		ResultSet rst=DataBase.app.getReportServices(Main.getCurrentRaportSearch().getIdRaport());
		while(rst.next())
		{
			TextArea result=new TextArea();
			result.setText(rst.getString(2));
			result.setEditable(false);
			servicesList.add(new Service(rst.getString(1),result));
		}
		investigation.setCellValueFactory(new PropertyValueFactory<Service,String>("investigation"));
		result.setCellValueFactory(new PropertyValueFactory<Service,TextArea>("result"));
		servicesTableView.setItems(servicesList);
	}
	
	@FXML
	private void backButton() {
		Main.exitRaportViewStage();
	}
}
