package presentation;




import java.util.ArrayList;
import java.util.Collection;import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class WaiterGraficalUserInterface implements Observer {

	private Stage waiterStage=new Stage();
	private Button backButton = new Button("Back");
	private TableView<MenuItem> menuTableView=new TableView<MenuItem>();
	private TableView<Order> orderTableView=new TableView<Order>();
	private Label errorLabel = new Label();
	private TextArea selectedItems=new TextArea();
	
	private List<MenuItem> listForOrder=new ArrayList<MenuItem>();
	
	private TextField tableTextField=new TextField();
	private Button addButton=new Button("add");
	private Button addItemButton=new Button("Add item");
	private Button deleteListButton=new Button("Delete list");
	private Button billButton=new Button("BILL");
	
	private Restaurant restauran;
	
	public WaiterGraficalUserInterface(Restaurant restaurant) {
		this.restauran=restaurant;
		view();
		initializeButtons();
	}

	private void view() {
		BorderPane borderPane = new BorderPane();
		HBox bottomHBox = new HBox();
		SplitPane splitPane=new SplitPane();
		splitPane.setOrientation(Orientation.HORIZONTAL);
		BorderPane layout1=new BorderPane();
		layout1.setMaxWidth(400);
		layout1.setPadding(new Insets(15));
		BorderPane layout2=new BorderPane();

		VBox leftVBox=new VBox(10);
		HBox commandHBox=new HBox(10);
		commandHBox.setAlignment(Pos.CENTER);
		HBox commandListHBox=new HBox(10);
		VBox listItemsHBox=new VBox(10);
		
		HBox menuHBox=new HBox(10);
		
		Label label=new Label("Add new order");
		label.setFont(new Font("Calibri", 20));
		selectedItems.setMaxWidth(180);
		selectedItems.setPrefHeight(600);
		selectedItems.setEditable(false);
		
		tableTextField.setPromptText("Table");
		commandListHBox.getChildren().addAll(addItemButton,deleteListButton);
		listItemsHBox.getChildren().addAll(commandListHBox,selectedItems);
		menuHBox.getChildren().addAll(menuTableView,listItemsHBox);
		commandHBox.getChildren().addAll(tableTextField,addButton);
		
		leftVBox.getChildren().addAll(label,errorLabel,menuHBox,commandHBox);
		layout1.setCenter(leftVBox);
		
		initializeMenuTableView();
		
		HBox billHBox=new HBox();
		billHBox.setPadding(new Insets(20));
		billHBox.setAlignment(Pos.CENTER);
		billHBox.getChildren().add(billButton);
		layout2.setCenter(orderTableView);
		initializeOrderTableView();
		layout2.setBottom(billHBox);
		
		bottomHBox.setPadding(new Insets(20));
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.getChildren().add(backButton);
		
		splitPane.getItems().addAll(layout1,layout2);
		
		borderPane.setCenter(splitPane);
		borderPane.setBottom(bottomHBox);
		Scene scene = new Scene(borderPane, 800, 500);
		waiterStage.setScene(scene);
		waiterStage.setTitle("Waiter");
		layout1.requestFocus();
		waiterStage.show();
	}
	
	private void initializeMenuTableView() {
		menuTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<MenuItem, String> col1=new TableColumn<MenuItem, String>("Name");
		TableColumn<MenuItem, String> col2=new TableColumn<MenuItem, String>("Price");
		
		col1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MenuItem,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<MenuItem, String> param) {
				MenuItem menuItem = param.getValue();
				if (menuItem instanceof BaseProduct) {
					return new SimpleStringProperty(((BaseProduct) menuItem).getName());
				}
				if (menuItem instanceof CompositeProduct) {
					return new SimpleStringProperty(((CompositeProduct) menuItem).getName());
				}
				return null;
			}
		});
		col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MenuItem,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<MenuItem, String> param) {
				MenuItem menuItem = param.getValue();
				if (menuItem instanceof BaseProduct) {
					return new SimpleStringProperty(((BaseProduct) menuItem).computePrice().toString());
				}
				if (menuItem instanceof CompositeProduct) {
					return new SimpleStringProperty(((CompositeProduct) menuItem).computePrice().toString());
				}
				return null;
			}
		});
		menuTableView.getColumns().addAll(col1,col2);
		ObservableList<MenuItem> observableList=FXCollections.observableArrayList(restauran.getMenuList());
		menuTableView.setItems(observableList);
	}
	
	private void initializeOrderTableView() {
		orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Order, Integer> col1=new TableColumn<Order, Integer>("Id");
		TableColumn<Order, String> col2=new TableColumn<Order, String>("Date");
		TableColumn<Order, Integer> col3=new TableColumn<Order, Integer>("Table");
		
		col1.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
		col2.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
		col3.setCellValueFactory(new PropertyValueFactory<Order, Integer>("table"));
		
		orderTableView.getColumns().addAll(col1,col2,col3);
		setOrderTableViewItems();
	}
	
	private void setOrderTableViewItems() {
		ObservableList<Order>observableList=FXCollections.observableArrayList(restauran.getOrdersMap().keySet());
		observableList.sort(new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				return o1.getOrderId().compareTo(o2.getOrderId());
			}
		});
		orderTableView.setItems(observableList);
	}
	
	
	
	
	private void initializeButtons() {
		backButton.setOnAction(e->{
			waiterStage.close();
			MainGraficalUserInterface.waiterButtonVisible();
		});
		
		addItemButton.setOnAction(e->{
			if (!menuTableView.getSelectionModel().isEmpty()) {
				MenuItem menuItem = menuTableView.getSelectionModel().getSelectedItem();
				listForOrder.add(menuItem);
				if (menuItem instanceof BaseProduct)
					selectedItems.appendText(((BaseProduct) menuItem).getName() + "\n");
				if (menuItem instanceof CompositeProduct)
					selectedItems.appendText(((CompositeProduct) menuItem).getName() + "\n");
			}
		});
		deleteListButton.setOnAction(e->{
			selectedItems.clear();
			listForOrder=new ArrayList<MenuItem>();
		});
		
		waiterStage.setOnCloseRequest(e->{
			MainGraficalUserInterface.waiterButtonVisible();
		});
		addButton.setOnAction(e->{
			try {
				restauran.createNewOrder(tableTextField.getText(), listForOrder);

			}catch (AssertionError e1) {
				if (e1.getMessage().equals("Successfully added")) {
					displayMessageLabel(e1.getMessage(), Color.GREEN);
					setOrderTableViewItems();
					selectedItems.clear();
					listForOrder=new ArrayList<MenuItem>();
					tableTextField.clear();
				}else
				displayMessageLabel(e1.getMessage(), Color.RED);
			}
		});
		billButton.setOnAction(e->{
			if(!orderTableView.getSelectionModel().isEmpty())
				restauran.generateBill(orderTableView.getSelectionModel().getSelectedItem());
			setOrderTableViewItems();
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableList<MenuItem> observableList=FXCollections.observableArrayList(restauran.getMenuList());
		menuTableView.setItems(observableList);
		menuTableView.refresh();
		
	}

	public void displayMessageLabel(final String messageText, Color color) {
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				updateMessage(messageText);
				Thread.sleep(4000);
				updateMessage(" ");
				return null;
			}

		};
		errorLabel.textProperty().bind(task.messageProperty());
		errorLabel.setTextFill(color);
		Thread thread = new Thread(task);
		thread.start();
	}


	
}
