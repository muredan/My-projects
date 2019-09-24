package presentation;

import java.util.ArrayList;
import java.util.List;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdministratorGraficalUserInterface {

	private Stage administratorStage = new Stage();
	private Button backButton = new Button("Back");
	private Button addButton = new Button("Add");
	private Button removeButton = new Button("Remove");
	private RadioButton baseProductRadioButton = new RadioButton("Base Product");
	private RadioButton compositeProductRadioButton = new RadioButton("Composite Product");
	private Button addToListButton = new Button("Add base product");
	private VBox productBox = new VBox(10);

	private TextField nameTextField;
	private TextField quantityTextField;
	private TextField priceTextField;

	private TreeTableView<MenuItem> menuTreeTableView = new TreeTableView<MenuItem>();

	private TextArea compositeProductViewTextArea = new TextArea();
	private List<MenuItem> listForCompositeProduct = new ArrayList<MenuItem>();
	private Label errorLabel = new Label();
	private Restaurant restaurant;

	private TreeItem<MenuItem> root = new TreeItem<MenuItem>();

	public AdministratorGraficalUserInterface(Restaurant restaurant) {
		this.restaurant = restaurant;
		view();
		initializeButtons();
	}

	private void view() {
		BorderPane borderPane = new BorderPane();
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.HORIZONTAL);

		BorderPane layout1 = new BorderPane();
		layout1.setMaxWidth(220);
		layout1.setPadding(new Insets(15));
		BorderPane layout2 = new BorderPane();

		VBox leftVBox = new VBox(10);

		Label label = new Label("Add menu item");
		label.setFont(new Font("Calibri", 20));

		ToggleGroup toggleGroup = new ToggleGroup();
		baseProductRadioButton.setSelected(true);
		baseProductRadioButton.setToggleGroup(toggleGroup);
		compositeProductRadioButton.setToggleGroup(toggleGroup);
		compositeProductRadioButton.setPadding(new Insets(0, 0, 10, 0));

		HBox addButtonHBox = new HBox();
		addButtonHBox.setAlignment(Pos.CENTER);
		addButtonHBox.getChildren().add(addButton);
		productBox.setAlignment(Pos.CENTER);

		nameTextField = new TextField();
		nameTextField.setPromptText("Name");
		compositeProductViewTextArea.setEditable(false);

		leftVBox.getChildren().addAll(label, errorLabel, baseProductRadioButton, compositeProductRadioButton,
				nameTextField, productBox, addButtonHBox);
		createBaseProductBox();

		initializeTreeTableView();

		HBox bottomHBox = new HBox();
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setPadding(new Insets(20, 0, 20, 0));
		bottomHBox.getChildren().add(backButton);

		HBox tableComandsHBox = new HBox(20);
		tableComandsHBox.setAlignment(Pos.CENTER);
		tableComandsHBox.setPadding(new Insets(10));
		tableComandsHBox.getChildren().addAll(removeButton);

		layout1.setCenter(leftVBox);
		layout2.setCenter(menuTreeTableView);
		layout2.setBottom(tableComandsHBox);

		splitPane.getItems().addAll(layout1, layout2);
		borderPane.setCenter(splitPane);
		borderPane.setBottom(bottomHBox);
		Scene scene = new Scene(borderPane, 800, 500);
		administratorStage.setTitle("Administrator");
		administratorStage.setScene(scene);
		administratorStage.show();
	}

	@SuppressWarnings("unchecked")
	private void initializeTreeTableView() {
		TreeTableColumn<MenuItem, String> col1 = new TreeTableColumn<MenuItem, String>("Name");
		TreeTableColumn<MenuItem, String> col2 = new TreeTableColumn<MenuItem, String>("Quantity");
		TreeTableColumn<MenuItem, String> col3 = new TreeTableColumn<MenuItem, String>("Price");
		menuTreeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

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
		col3.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MenuItem, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<MenuItem, String> param) {
						MenuItem menuItem = param.getValue().getValue();
						return new SimpleStringProperty(menuItem.computePrice().toString());
					}
				});

		col1.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col1.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<MenuItem, String>>() {

			@Override
			public void handle(CellEditEvent<MenuItem, String> event) {
				MenuItem menuItem = menuTreeTableView.getTreeItem(event.getTreeTablePosition().getRow()).getValue();
				try {
					if (menuItem instanceof BaseProduct) {
						restaurant.editMenuItem(event.getNewValue(), ((BaseProduct) menuItem).getQuantity().toString(),
								((BaseProduct) menuItem).getPrice().toString(), menuItem);
					}
					if (menuItem instanceof CompositeProduct) {
						restaurant.editMenuItem(event.getNewValue(), menuItem);
					}
				} catch (AssertionError e) {
					if (e.getMessage().equals("Edited successfully")) {
						displayMessageLabel(e.getMessage(), Color.GREEN);
					} else {
						displayMessageLabel(e.getMessage(), Color.RED);
					}
				} finally {
					menuTreeTableView.refresh();
				}

			}
		});

		col2.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col2.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<MenuItem, String>>() {

			@Override
			public void handle(CellEditEvent<MenuItem, String> event) {
				MenuItem menuItem = menuTreeTableView.getTreeItem(event.getTreeTablePosition().getRow()).getValue();
				try {
					if (menuItem instanceof BaseProduct) {
						restaurant.editMenuItem(((BaseProduct) menuItem).getName(), event.getNewValue(),
								((BaseProduct) menuItem).getPrice().toString(), menuItem);
					}
				} catch (AssertionError e) {
					if (e.getMessage().equals("Edited successfully")) {
						displayMessageLabel(e.getMessage(), Color.GREEN);
					} else {
						displayMessageLabel(e.getMessage(), Color.RED);
					}
				} finally {
					menuTreeTableView.refresh();
				}

			}

		});

		col3.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col3.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<MenuItem, String>>() {

			@Override
			public void handle(CellEditEvent<MenuItem, String> event) {
				MenuItem menuItem = menuTreeTableView.getTreeItem(event.getTreeTablePosition().getRow()).getValue();
				try {
					if (menuItem instanceof BaseProduct) {
						restaurant.editMenuItem(((BaseProduct) menuItem).getName(),
								((BaseProduct) menuItem).getQuantity().toString(), event.getNewValue(), menuItem);
					}
				} catch (AssertionError e) {
					if (e.getMessage().equals("Edited successfully")) {
						displayMessageLabel(e.getMessage(), Color.GREEN);
					} else {
						displayMessageLabel(e.getMessage(), Color.RED);
					}
				} finally {
					menuTreeTableView.refresh();
				}
			}
		});

		menuTreeTableView.setEditable(true);
		menuTreeTableView.getColumns().addAll(col1, col2, col3);

		setTreeTableViewList(restaurant.getMenuList());

		menuTreeTableView.setShowRoot(false);
	}

	private void setTreeTableViewList(List<MenuItem> list) {

		root = new TreeItem<MenuItem>();
		for (MenuItem menuItem : list) {
			setTreeTableViewItems(menuItem, root);
		}
		menuTreeTableView.setRoot(root);
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

	private void initializeButtons() {
		backButton.setOnAction(e -> {
			administratorStage.close();
			MainGraficalUserInterface.administratorButtonVisible();

		});

		baseProductRadioButton.setOnMouseClicked(e -> {
			createBaseProductBox();
		});

		compositeProductRadioButton.setOnMouseClicked(e -> {
			productBox.getChildren().clear();
			nameTextField.clear();
			resetCompositeProductArea();
			productBox.getChildren().addAll(addToListButton, compositeProductViewTextArea);

		});
		addToListButton.setOnAction(e -> {
			if (!menuTreeTableView.getSelectionModel().isEmpty()) {
				MenuItem menuItem = menuTreeTableView.getSelectionModel().getSelectedItem().getValue();
				listForCompositeProduct.add(menuItem);
				if (menuItem instanceof BaseProduct)
					compositeProductViewTextArea.appendText(((BaseProduct) menuItem).getName() + "\n");
				if (menuItem instanceof CompositeProduct)
					compositeProductViewTextArea.appendText(((CompositeProduct) menuItem).getName() + "\n");
			}
		});

		administratorStage.setOnCloseRequest(e -> {
			MainGraficalUserInterface.administratorButtonVisible();
		});
		addButton.setOnAction(e -> {
			try {
				if (baseProductRadioButton.isSelected()) {
					restaurant.createNewMenuItem(nameTextField.getText(), quantityTextField.getText(),
							priceTextField.getText());
				} else {
					restaurant.createNewMenuItem(nameTextField.getText(), listForCompositeProduct);
				}

			} catch (AssertionError e1) {

				if (e1.getMessage().equals("Successfully added")) {
					displayMessageLabel(e1.getMessage(), Color.GREEN);
					resetCompositeProductArea();
					resetBasedProductArea();
					setTreeTableViewItems(restaurant.getMenuList().get(restaurant.getMenuList().size() - 1), root);

				} else {
					displayMessageLabel(e1.getMessage(), Color.RED);
				}

			}
		});

		removeButton.setOnAction(e -> {
			if (!menuTreeTableView.getSelectionModel().isEmpty()) {
				MenuItem menuItem = menuTreeTableView.getSelectionModel().getSelectedItem().getValue();
				TreeItem<MenuItem> treeItem = menuTreeTableView.getSelectionModel().getSelectedItem();
				try {
					if (treeItem.getParent().getValue() != null) {
						restaurant.deleteMenuItem(menuItem, treeItem.getParent().getValue());	
					} else {
						restaurant.deleteMenuItem(menuItem);
						
					}

				} catch (AssertionError e2) {
					displayMessageLabel(e2.getMessage(), Color.GREEN);
				} finally {
					setTreeTableViewList(restaurant.getMenuList());
				}
			}
		});

	}

	private void resetCompositeProductArea() {
		compositeProductViewTextArea.clear();
		listForCompositeProduct = new ArrayList<MenuItem>();
	}

	private void resetBasedProductArea() {
		nameTextField.clear();
		quantityTextField.clear();
		priceTextField.clear();
	}

	private void createBaseProductBox() {
		productBox.getChildren().clear();

		nameTextField.clear();

		quantityTextField = new TextField();
		quantityTextField.setPromptText("Quantity");

		priceTextField = new TextField();
		priceTextField.setPromptText("Price");

		productBox.getChildren().addAll(quantityTextField, priceTextField);

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
