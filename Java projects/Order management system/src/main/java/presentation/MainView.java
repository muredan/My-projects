package presentation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;



import bll.ClientBLL;
import bll.ProductBLL;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Client;
import model.Orders;
import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class MainView.
 */
public class MainView {

	/** The primary stage. */
	private Stage primaryStage;
	
	/** The tab pane. */
	private TabPane tabPane = new TabPane();
	
	/** The layout. */
	private BorderPane layout = new BorderPane();
	
	/** The add client button. */
	private Button addClientButton = new Button("Add");
	
	/** The add product button. */
	private Button addProductButton = new Button("Add");
	
	/** The order button. */
	private Button orderButton = new Button("Place order");

	/** The save client button. */
	private Button saveClientButton = new Button("Save");
	
	/** The remove client button. */
	private Button removeClientButton = new Button("Remove");
	
	/** The save product button. */
	private Button saveProductButton = new Button("Save");
	
	/** The remove product button. */
	private Button removeProductButton = new Button("Remove");

	/** The text fields client list. */
	private List<TextField> textFieldsClientList;
	
	/** The text fields product list. */
	private List<TextField> textFieldsProductList;

	/** The client table view. */
	private TableView<Client> clientTableView;
	
	/** The product table view. */
	private TableView<Product> productTableView;
	
	/** The order table view. */
	private TableView<Orders> orderTableView;

	/** The error label. */
	private Label errorLabel = new Label(" ");
	
	/** The quantity text field. */
	private TextField quantityTextField = new TextField();

	/**
	 * Instantiates a new main view.
	 *
	 * @param stage the stage
	 */
	public MainView(Stage stage) {
		primaryStage = stage;
		

		tabPane.getTabs().addAll(new Tab("Client"), new Tab("Product"), new Tab("Order"));
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		clientPane(ClientBLL.getClients());

		layout.setTop(tabPane);
		Scene scene = new Scene(layout, 1100, 570);
		primaryStage.setScene(scene);

	}

	/**
	 * Order pane.
	 *
	 * @param clientsList the clients list
	 * @param productsList the products list
	 * @param ordersList the orders list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void orderPane(ObservableList clientsList, ObservableList productsList, ObservableList ordersList) {
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 10));
		VBox vBox = new VBox(20);

		HBox selectHbox = new HBox(20);
		selectHbox.setAlignment(Pos.CENTER_LEFT);
		VBox quantityVBox = new VBox(10);
		quantityVBox.setAlignment(Pos.CENTER);
		Label label = new Label();
		label.textProperty().bind(errorLabel.textProperty());
		label.textFillProperty().bind(errorLabel.textFillProperty());
		Label quantityLabel = new Label("Quantity");
		quantityLabel.setFont(Font.font(18));
		quantityVBox.getChildren().addAll(label, quantityLabel, quantityTextField, orderButton);

		clientsList.add(new Client());
		clientTableView = createTable(clientsList);
		productsList.add(new Product());
		productTableView = createTable(productsList);
		clientTableView.setEditable(false);
		productTableView.setEditable(false);
		ordersList.add(new Orders());
		orderTableView = createTable(ordersList);
		orderTableView.setEditable(false);
		productTableView.minWidthProperty().bind(clientTableView.widthProperty());
		HBox searchClientsHBox = new HBox(12, new Label("Search:"));
		HBox searchProductsHBox = new HBox(12, new Label("Search:"));
		searchClientsHBox.setAlignment(Pos.CENTER_LEFT);
		searchProductsHBox.setAlignment(Pos.CENTER_LEFT);
		List<TextField> listTextFields1 = new ArrayList<TextField>();
		int index = 0;
		for (Field field : clientsList.get(0).getClass().getDeclaredFields()) {
			TextField textField = new TextField();
			textField.setPromptText(field.getName());
			textField.setMaxWidth(120);
			listTextFields1.add(textField);
			if (index >= 2) {
				break;
			}
			index++;
		}
		searchClientsHBox.getChildren().addAll(listTextFields1);
		filterClientTableView(clientTableView, listTextFields1);
		List<TextField> listTextFields2 = new ArrayList<TextField>();
		index = 0;
		for (Field field : productsList.get(0).getClass().getDeclaredFields()) {
			TextField textField = new TextField();
			textField.setMaxWidth(127);
			textField.setPromptText(field.getName());
			listTextFields2.add(textField);
			if (index >= 2) {
				break;
			}
			index++;
		}
		clientTableView.getItems().remove(clientsList.size() - 1);
		productTableView.getItems().remove(productsList.size() - 1);
		orderTableView.getItems().remove(ordersList.size() - 1);
		searchProductsHBox.getChildren().addAll(listTextFields2);
		filterProductTableView(productTableView, listTextFields2);

		clientTableView.setPrefHeight(230);
		productTableView.setPrefHeight(230);

		orderTableView.setPrefHeight(235);
		VBox clientVBox = new VBox(10);
		VBox productVBox = new VBox(10);

		clientVBox.getChildren().addAll(searchClientsHBox, clientTableView);
		productVBox.getChildren().addAll(searchProductsHBox, productTableView);

		selectHbox.getChildren().addAll(clientVBox, quantityVBox, productVBox);
		vBox.getChildren().addAll(selectHbox, orderTableView);
		borderPane.setCenter(vBox);
		tabPane.getTabs().get(2).setContent(borderPane);
	}

	/**
	 * Client pane.
	 *
	 * @param list the list
	 */
	@SuppressWarnings("rawtypes")
	public void clientPane(ObservableList list) {
		textFieldsClientList = setTabView(tabPane.getTabs().get(0), new Client(), list);
	}

	/**
	 * Product pane.
	 *
	 * @param list the list
	 */
	@SuppressWarnings("rawtypes")
	public void productPane(ObservableList list) {
		textFieldsProductList = setTabView(tabPane.getTabs().get(1), new Product(), list);
	}

	/**
	 * Show.
	 */
	public void show() {
		primaryStage.show();
	}

	/**
	 * Sets the tab view.
	 *
	 * @param tab the tab
	 * @param object the object
	 * @param list the list
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<TextField> setTabView(Tab tab, Object object, ObservableList list) {

		List<List<TextField>> listOfLists = new ArrayList<List<TextField>>();
		listOfLists.add(new ArrayList<TextField>());
		listOfLists.add(new ArrayList<TextField>());

		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.HORIZONTAL);
		splitPane.minHeightProperty().bind(layout.heightProperty());
		BorderPane layout1 = new BorderPane();
		layout1.setPadding(new Insets(20, 20, 20, 20));
		BorderPane layout2 = new BorderPane();
		layout2.setPadding(new Insets(20, 0, 0, 0));
		VBox rightVbox = new VBox(20);
		rightVbox.setAlignment(Pos.TOP_CENTER);

		VBox leftVbox = new VBox(10);
		leftVbox.setAlignment(Pos.TOP_CENTER);
		layout1.setMaxWidth(240);
		Label label = new Label();
		label.textProperty().bind(errorLabel.textProperty());
		label.textFillProperty().bind(errorLabel.textFillProperty());
		leftVbox.getChildren().add(label);
		HBox searchHBox = new HBox(10, new Label("Search: "));
		searchHBox.setAlignment(Pos.CENTER_LEFT);
		searchHBox.setPadding(new Insets(0, 0, 0, 20));

		HBox buttonsHBox = new HBox(20);
		buttonsHBox.setAlignment(Pos.CENTER);

		int index = 0;

		for (Field field : object.getClass().getDeclaredFields()) {

			if (!field.getName().equals("id")) {
				field.setAccessible(true);
				TextField addTextField = new TextField();
				addTextField.setPromptText(field.getName());
				listOfLists.get(0).add(addTextField);
			}

			if (index <= 2) {
				TextField searchTextField = new TextField();
				searchTextField.setPromptText(field.getName());
				listOfLists.get(1).add(searchTextField);
				index++;
			}

		}
		leftVbox.getChildren().addAll(listOfLists.get(0));
		searchHBox.getChildren().addAll(listOfLists.get(1));
		rightVbox.getChildren().add(searchHBox);

		if (object instanceof Client) {
			clientTableView = new TableView<Client>();
			list.add(new Client());
			clientTableView = createTable(list);
			clientTableView.getItems().remove(list.size() - 1);
			leftVbox.getChildren().add(addClientButton);
			buttonsHBox.getChildren().addAll(saveClientButton, removeClientButton);
			rightVbox.getChildren().addAll(clientTableView, buttonsHBox);
			filterClientTableView(clientTableView, listOfLists.get(1));
		} else if (object instanceof Product) {
			productTableView = new TableView<Product>();
			list.add(new Product());
			productTableView = createTable(list);
			productTableView.getItems().remove(list.size() - 1);
			leftVbox.getChildren().add(addProductButton);
			buttonsHBox.getChildren().addAll(saveProductButton, removeProductButton);
			rightVbox.getChildren().addAll(productTableView, buttonsHBox);
			filterProductTableView(productTableView, listOfLists.get(1));
		}
		layout1.setCenter(leftVbox);
		layout2.setCenter(rightVbox);
		splitPane.getItems().addAll(layout1, layout2);
		tab.setContent(splitPane);

		return listOfLists.get(0);
	}

	/**
	 * Creates the table.
	 *
	 * @param objects the objects
	 * @return the table view
	 */
	@SuppressWarnings("rawtypes")
	public TableView createTable(ObservableList<Object> objects) {
		TableView<Object> tableView = new TableView<Object>();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setEditable(true);
		Object element = objects.get(0);
		Class type = element.getClass();
		for (Field field : element.getClass().getDeclaredFields()) {

			if (field.getType().equals(String.class)) {
				TableColumn<Object, String> stringTableColumn = new TableColumn<Object, String>(field.getName());
				tableView.getColumns().add(stringTableColumn);
				stringTableColumn.setCellValueFactory(new PropertyValueFactory<Object, String>(field.getName()));
				stringTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				stringTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Object, String>>() {

					@Override
					public void handle(CellEditEvent<Object, String> event) {
						try {
							PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
							Method method = propertyDescriptor.getWriteMethod();
							method.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()),
									event.getNewValue());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IntrospectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			} else {
				TableColumn<Object, Object> tableColumn = new TableColumn<Object, Object>(field.getName());
				tableView.getColumns().add(tableColumn);
				tableColumn.setCellValueFactory(new PropertyValueFactory<Object, Object>(field.getName()));
			}

		}
		tableView.setItems(objects);
		return tableView;
	}

	/**
	 * Display message label.
	 *
	 * @param messageText the message text
	 * @param color the color
	 */
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

	/**
	 * Adds the add client button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addAddClientButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		addClientButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the save client button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addSaveClientButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		saveClientButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the remove client button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addRemoveClientButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		removeClientButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the add product button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addAddProductButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		addProductButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the remove product button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addRemoveProductButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		removeProductButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the save product button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addSaveProductButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		saveProductButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the order button event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addOrderButtonEventAction(EventHandler<ActionEvent> eventHandler) {
		orderButton.setOnAction(eventHandler);
	}

	/**
	 * Adds the client tab event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addClientTabEventAction(EventHandler<Event> eventHandler) {
		tabPane.getTabs().get(0).setOnSelectionChanged(eventHandler);
	}

	/**
	 * Adds the product tab event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addProductTabEventAction(EventHandler<Event> eventHandler) {
		tabPane.getTabs().get(1).setOnSelectionChanged(eventHandler);
	}

	/**
	 * Adds the order tab event action.
	 *
	 * @param eventHandler the event handler
	 */
	public void addOrderTabEventAction(EventHandler<Event> eventHandler) {
		tabPane.getTabs().get(2).setOnSelectionChanged(eventHandler);
	}

	/**
	 * Gets the clients.
	 *
	 * @return the clients
	 */
	public void getClients() {
		if (!clientTableView.getSelectionModel().isEmpty())
			clientTableView.getSelectionModel().getSelectedItem().print();
	}

	/**
	 * Filter client table view.
	 *
	 * @param tableView the table view
	 * @param list the list
	 */
	public void filterClientTableView(TableView<Client> tableView, List<TextField> list) {

		list.get(0).setOnKeyReleased(e -> {
			tableView.setItems(
					ClientBLL.filterClients(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
		list.get(1).setOnKeyReleased(e -> {
			tableView.setItems(
					ClientBLL.filterClients(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
		list.get(2).setOnKeyReleased(e -> {
			tableView.setItems(
					ClientBLL.filterClients(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
	}

	/**
	 * Filter product table view.
	 *
	 * @param tableView the table view
	 * @param list the list
	 */
	public void filterProductTableView(TableView<Product> tableView, List<TextField> list) {

		list.get(0).setOnKeyReleased(e -> {
			tableView.setItems(
					ProductBLL.filterProducts(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
		list.get(1).setOnKeyReleased(e -> {
			tableView.setItems(
					ProductBLL.filterProducts(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
		list.get(2).setOnKeyReleased(e -> {
			tableView.setItems(
					ProductBLL.filterProducts(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()));
		});
	}

	/**
	 * Gets the client table view.
	 *
	 * @return the client table view
	 */
	public ObservableList<Client> getClientTableView() {
		return clientTableView.getItems();
	}

	/**
	 * Gets the product table view.
	 *
	 * @return the product table view
	 */
	public ObservableList<Product> getProductTableView() {
		return productTableView.getItems();
	}

	/**
	 * Gets the selected client.
	 *
	 * @return the selected client
	 */
	public Client getSelectedClient() {
		if (!clientTableView.getSelectionModel().isEmpty()) {
			return clientTableView.getSelectionModel().getSelectedItem();
		}
		return null;
	}

	/**
	 * Gets the selected product.
	 *
	 * @return the selected product
	 */
	public Product getSelectedProduct() {
		if (!productTableView.getSelectionModel().isEmpty()) {
			return productTableView.getSelectionModel().getSelectedItem();
		}
		return null;
	}

	/**
	 * Gets the client fields.
	 *
	 * @return the client fields
	 */
	public List<String> getClientFields() {
		List<String> list = new ArrayList<String>();
		for (TextField textField : textFieldsClientList) {
			list.add(textField.getText());
		}
		return list;
	}

	/**
	 * Gets the product fields.
	 *
	 * @return the product fields
	 */
	public List<String> getProductFields() {
		List<String> list = new ArrayList<String>();
		for (TextField textField : textFieldsProductList) {
			list.add(textField.getText());
		}
		return list;
	}

	/**
	 * Gets the quantity text field string.
	 *
	 * @return the quantity text field string
	 */
	public String getQuantityTextFieldString() {
		return quantityTextField.getText();
	}

	/**
	 * Adds the list to client table.
	 *
	 * @param list the list
	 */
	public void addListToClientTable(ObservableList<Client> list) {
		clientTableView.setItems(list);
	}

	/**
	 * Adds the list to product table.
	 *
	 * @param list the list
	 */
	public void addListToProductTable(ObservableList<Product> list) {
		productTableView.setItems(list);
	}

	/**
	 * Adds the list to order table.
	 *
	 * @param list the list
	 */
	public void addListToOrderTable(ObservableList<Orders> list) {
		orderTableView.setItems(list);
	}

}
