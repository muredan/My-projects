package presentation;

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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChefGraficalUserInterface implements Observer {

	private Stage chefStage = new Stage();
	private Button backButton = new Button("Back");
	private TableView<Order> ordersTableView = new TableView<Order>();
	private TreeTableView<MenuItem> menuItemsTreeTableView = new TreeTableView<MenuItem>();
	private Restaurant restaurant;

	public ChefGraficalUserInterface(Restaurant restaurant) {
		this.restaurant = restaurant;
		view();
		initializeButtons();
	}

	private void view() {
		BorderPane borderPane = new BorderPane();
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.HORIZONTAL);
		BorderPane layout1 = new BorderPane();
		BorderPane layout2 = new BorderPane();

		HBox bottomHBox = new HBox();
		bottomHBox.setPadding(new Insets(20));
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.getChildren().add(backButton);

		initializeOrdersTableView();
		initializeMenuItemsTreeTableView();
		Label label1 = new Label("Orders");
		label1.setFont(new Font("Calibri", 20));
		label1.setPadding(new Insets(10));

		Label label2 = new Label("Menu items from order");
		label2.setFont(new Font("Calibri", 20));
		label2.setPadding(new Insets(10));

		layout1.setTop(label1);
		layout1.setCenter(ordersTableView);
		layout2.setTop(label2);
		layout2.setCenter(menuItemsTreeTableView);

		splitPane.getItems().addAll(layout1, layout2);
		borderPane.setCenter(splitPane);
		borderPane.setBottom(bottomHBox);
		Scene scene = new Scene(borderPane, 800, 500);
		chefStage.setScene(scene);
		chefStage.setTitle("Chef");
		chefStage.show();
	}

	private void initializeOrdersTableView() {

		ordersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Order, Integer> col1 = new TableColumn<Order, Integer>("Id");
		TableColumn<Order, String> col2 = new TableColumn<Order, String>("Date");
		TableColumn<Order, Integer> col3 = new TableColumn<Order, Integer>("Table");

		col1.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
		col2.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
		col3.setCellValueFactory(new PropertyValueFactory<Order, Integer>("table"));
		ordersTableView.getColumns().addAll(col1, col2, col3);
		ordersTableView.setItems(FXCollections.observableArrayList(restaurant.getOrdersMap().keySet()));

	}

	private void initializeMenuItemsTreeTableView() {
		menuItemsTreeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
		TreeTableColumn<MenuItem, String> col1 = new TreeTableColumn<MenuItem, String>("Name");
		TreeTableColumn<MenuItem, String> col2 = new TreeTableColumn<MenuItem, String>("Quantity");
		col1.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MenuItem, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<MenuItem, String> arg0) {
						MenuItem menuItem = arg0.getValue().getValue();
						if (menuItem instanceof BaseProduct) {
							return new SimpleStringProperty(((BaseProduct) menuItem).getName());
						}
						if (menuItem instanceof CompositeProduct) {
							return new SimpleStringProperty(((CompositeProduct) menuItem).getName());
						}
						return null;
					}
				});
		col2.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MenuItem, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<MenuItem, String> param) {
						MenuItem menuItem = param.getValue().getValue();
						return new SimpleStringProperty(menuItem.computeQuantity().toString());
					}
				});
		menuItemsTreeTableView.getColumns().addAll(col1, col2);
		menuItemsTreeTableView.setShowRoot(false);

	}

	private void initializeButtons() {
		backButton.setOnAction(e -> {
			chefStage.close();
			MainGraficalUserInterface.chefButtonVisible();
		});
		chefStage.setOnCloseRequest(e -> {
			MainGraficalUserInterface.chefButtonVisible();
		});
		ordersTableView.setOnMouseClicked(e -> {
			if (!ordersTableView.getSelectionModel().isEmpty()) {
				Order order = ordersTableView.getSelectionModel().getSelectedItem();
				TreeItem<MenuItem> root = new TreeItem<MenuItem>();
				for (MenuItem menuItem : restaurant.getMenuItemsFromOrder(order)) {
					setTreeTableViewItems(menuItem, root);
				}
				menuItemsTreeTableView.setRoot(root);
			}
		});
	}

	private void setTreeTableViewItems(MenuItem menuItem, TreeItem<MenuItem> root) {
		if (menuItem instanceof BaseProduct) {
			root.getChildren().add(new TreeItem<MenuItem>(menuItem));
		}
		if (menuItem instanceof CompositeProduct) {
			TreeItem<MenuItem> root1 = new TreeItem<MenuItem>(menuItem);
			for (MenuItem element : ((CompositeProduct) menuItem).getList()) {
				setTreeTableViewItems(element, root1);
			}
			root.getChildren().add(root1);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ordersTableView.setItems(FXCollections.observableArrayList(restaurant.getOrdersMap().keySet()));

	}

}
