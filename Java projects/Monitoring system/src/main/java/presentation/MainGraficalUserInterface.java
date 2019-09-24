package presentation;


import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.TabExpander;

import business.CountPerDay;
import business.MonitoredData;
import business.MonitoredDataManagement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainGraficalUserInterface {
	
	private Stage primaryStage;
	private TextField totalDaysTextField;
	private TableView<Map.Entry<String, Long>> countTableView=new TableView<Map.Entry<String, Long>>();
	private TableView<CountPerDay> countPerDaysTableView=new TableView<CountPerDay>();
	private TableView<MonitoredData> countDurationTableView=new TableView<MonitoredData>();
	private TableView<Map.Entry<String, Long>> countDurationPerActivityTableView=new TableView<Map.Entry<String, Long>>();
	private TableView<String> filterTableView=new TableView<String>();
	private MonitoredDataManagement monitoredDataManagement=new MonitoredDataManagement();
	
	public MainGraficalUserInterface(Stage stage) {
		this.primaryStage=stage;
		//monitoredDataManagement.printList();
		view();
		
	}
	private void view(){
		BorderPane borderPane=new BorderPane();
		borderPane.setPadding(new Insets(10));
	
		GridPane gridPane=new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		countTableView.prefHeightProperty().bind(borderPane.heightProperty());
		countTableView.prefWidthProperty().bind(borderPane.widthProperty());
		countPerDaysTableView.prefHeightProperty().bind(borderPane.heightProperty());
		countPerDaysTableView.prefWidthProperty().bind(borderPane.widthProperty());
		countDurationTableView.prefHeightProperty().bind(borderPane.heightProperty());
		countDurationTableView.prefWidthProperty().bind(borderPane.widthProperty());
		filterTableView.prefHeightProperty().bind(borderPane.heightProperty());
		filterTableView.prefWidthProperty().bind(borderPane.widthProperty());
		countDurationPerActivityTableView.prefHeightProperty().bind(borderPane.heightProperty());
		countDurationPerActivityTableView.prefWidthProperty().bind(borderPane.widthProperty());
		
		gridPane.add(countDurationTableView, 0, 0, 2, 1);
		
		gridPane.addRow(1, countTableView,countPerDaysTableView);
		gridPane.addRow(2, countDurationPerActivityTableView,filterTableView);
		
		initializeCountTableView();
		initializeCountPerDaysTableView();
		initializeCountDuration();
		initializeCountDurationPerActivityTableView();
		initializefilterTableView();
		
		TextField textField=new TextField(monitoredDataManagement.getNumberOfDays());
		textField.setEditable(false);
		Label label=new Label("Number of days");
		HBox hBox=new HBox(10);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.getChildren().addAll(label,textField);
		hBox.setPadding(new Insets(10));
		
		borderPane.setTop(hBox);
		borderPane.setCenter(gridPane);
		
		Scene scene=new Scene(borderPane, 800, 500);
		primaryStage.setScene(scene);
		borderPane.requestFocus();
		primaryStage.setTitle("Assignment 5");
		primaryStage.show();
	}
	
	private void initializeCountTableView() {
		countTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Map.Entry<String, Long>, String> col1=new TableColumn<Map.Entry<String,Long>, String>("Activity");
		TableColumn<Map.Entry<String, Long>, Integer> col2=new TableColumn<Map.Entry<String,Long>, Integer>("Appearances");
		
		col1.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getKey()));
		col2.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getValue().intValue()).asObject());
		
		countTableView.getColumns().addAll(col1,col2);
		
		countTableView.setItems(FXCollections.observableArrayList(monitoredDataManagement.countAppearances().entrySet()));
		
		
	}
	private void initializeCountPerDaysTableView() {
		countPerDaysTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<CountPerDay, String> col1=new TableColumn<CountPerDay, String>("Activity");
		TableColumn<CountPerDay, Integer> col2=new TableColumn<CountPerDay, Integer>("Day");
		TableColumn<CountPerDay, Long> col3=new TableColumn<CountPerDay, Long>("Appearances");
		
		col1.setCellValueFactory(new PropertyValueFactory<CountPerDay, String>("activityName"));
		col2.setCellValueFactory(new PropertyValueFactory<CountPerDay, Integer>("date"));
		col3.setCellValueFactory(new PropertyValueFactory<CountPerDay, Long>("count"));
		
		countPerDaysTableView.getColumns().addAll(col1,col2,col3);
		
		Map<String, Map<String, Long>> map=monitoredDataManagement.countPerDays();
	
		for(Entry<String, Map<String, Long>> element: map.entrySet()) {
//			System.out.println(element.getKey());
			for(Entry<String, Long> element2: element.getValue().entrySet()) {
//				System.out.println("\t\t"+element2.getKey()+"  "+element2.getValue());
				countPerDaysTableView.getItems().add(new CountPerDay(element.getKey(), element2.getKey(), element2.getValue()));
			}
		}
	}
	private void initializeCountDuration() {
		countDurationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<MonitoredData, String> col1=new TableColumn<MonitoredData, String>("Activity");
		TableColumn<MonitoredData, String> col2=new TableColumn<MonitoredData, String>("Start");
		TableColumn<MonitoredData, String> col3=new TableColumn<MonitoredData, String>("End");
		TableColumn<MonitoredData, Integer> col4=new TableColumn<MonitoredData, Integer>("Duration");
		
		col1.setCellValueFactory(new PropertyValueFactory<MonitoredData, String>("activityName"));
		col2.setCellValueFactory(new PropertyValueFactory<MonitoredData, String>("startTime"));
		col3.setCellValueFactory(new PropertyValueFactory<MonitoredData, String>("startTime"));
		col4.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getDuration().intValue()).asObject());
		
		countDurationTableView.getColumns().addAll(col1,col2,col3,col4);
		countDurationTableView.setItems(FXCollections.observableArrayList(monitoredDataManagement.getList()));
	}
	private void initializeCountDurationPerActivityTableView() {
		countDurationPerActivityTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Map.Entry<String, Long>, String> col1=new TableColumn<Map.Entry<String,Long>, String>("Activity");
		TableColumn<Map.Entry<String, Long>, Integer> col2=new TableColumn<Map.Entry<String,Long>, Integer>("Duration");
		
		col1.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getKey()));
		col2.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getValue().intValue()).asObject());
		
		countDurationPerActivityTableView.getColumns().addAll(col1,col2);
		countDurationPerActivityTableView.setItems(FXCollections.observableArrayList(monitoredDataManagement.countDurationPerActivityTableView().entrySet()));
		
	}
	private void initializefilterTableView() {
		filterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<String, String> col1=new TableColumn<String, String>("Activity");
		
		col1.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue()));
		
		filterTableView.getColumns().add(col1);
		filterTableView.setItems(FXCollections.observableArrayList(monitoredDataManagement.filter()));
		
	}
	

}
