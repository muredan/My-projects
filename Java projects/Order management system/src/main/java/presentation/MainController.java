package presentation;

import java.util.ArrayList;
import java.util.List;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.validators.ClientValidator;
import bll.validators.OrderValidator;
import bll.validators.ProductValidator;
import bll.validators.ValidationException;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Client;
import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
public class MainController {

	/** The main view. */
	MainView mainView;

	/**
	 * Start.
	 *
	 * @param stage the stage
	 */
	public void start(Stage stage) {
		mainView = new MainView(stage);
		mainView.show();
		initializeMenuItems();

	}

	/**
	 * Initialize menu items.
	 */
	private void initializeMenuItems() {

		mainView.addClientTabEventAction(e -> {

			mainView.clientPane(ClientBLL.getClients());
		});
		mainView.addAddClientButtonEventAction(e -> {

			try {
				Client newClient = ClientValidator.validateClient(mainView.getClientFields());
				ClientBLL.addClient(newClient);
				mainView.addListToClientTable(ClientBLL.getClients());
			} catch (ValidationException e1) {
				mainView.displayMessageLabel(e1.getMessage(), Color.RED);
			}

		});
		mainView.addRemoveClientButtonEventAction(e -> {
			Client client=mainView.getSelectedClient();
			if(client!=null) {
			ClientBLL.removeClient(client);
			mainView.addListToClientTable(ClientBLL.getClients());
			}
		});
		mainView.addSaveClientButtonEventAction(e -> {
			int id = 0;
			try {
				for (Client client : mainView.getClientTableView()) {
					List<String> list = new ArrayList<String>();
					id = client.getId();
					list.add(client.getFirstName());
					list.add(client.getLastName());
					list.add(client.getAddress());
					list.add(client.getPhone());

					ClientValidator.validateClient(list);
				}
				ClientBLL.updateClients(mainView.getClientTableView());
				mainView.displayMessageLabel("Saved successfully", Color.GREEN);
			} catch (ValidationException e1) {
				mainView.displayMessageLabel(e1.getMessage() + " (Client id=" + id + ")", Color.RED);
			}
		});

		mainView.addProductTabEventAction(e -> {

			mainView.productPane(ProductBLL.getProducts());
		});
		mainView.addAddProductButtonEventAction(e -> {
			try {
				Product newProduct = ProductValidator.validateProduct(mainView.getProductFields());
				ProductBLL.addProduct(newProduct);
				mainView.addListToProductTable(ProductBLL.getProducts());
			} catch (ValidationException e1) {
				mainView.displayMessageLabel(e1.getMessage(), Color.RED);
			}
		});
		mainView.addRemoveProductButtonEventAction(e -> {
			Product product = mainView.getSelectedProduct();
			if (product != null) {
				ProductBLL.removeProduct(product);
				mainView.addListToProductTable(ProductBLL.getProducts());
			}
		});
		mainView.addSaveProductButtonEventAction(e -> {

			int id = 0;
			try {
				for (Product product : mainView.getProductTableView()) {
					List<String> list = new ArrayList<String>();
					id = product.getId();
					list.add(product.getName());
					list.add(product.getProducer());
					list.add(product.getStock());
					list.add(product.getPrice());

					ProductValidator.validateProduct(list);
				}
				ProductBLL.updateProducts(mainView.getProductTableView());
				mainView.displayMessageLabel("Saved successfully", Color.GREEN);
			} catch (ValidationException e1) {
				mainView.displayMessageLabel(e1.getMessage() + " (Product id=" + id + ")", Color.RED);
			}
		});

		mainView.addOrderTabEventAction(e -> {
			mainView.orderPane(ClientBLL.getClients(), ProductBLL.getProducts(), OrderBLL.getOrders());
		});

		mainView.addOrderButtonEventAction(e -> {
			Client client = mainView.getSelectedClient();
			Product product = mainView.getSelectedProduct();
			String quantity = mainView.getQuantityTextFieldString();
			if (client == null) {
				mainView.displayMessageLabel("select a client", Color.RED);
			} else if (product == null) {
				mainView.displayMessageLabel("select a product", Color.RED);
			} else {

				try {
					OrderValidator.validateOrder(product, quantity);
					OrderBLL.addOrder(client, product, quantity);
					mainView.displayMessageLabel("Successfully ordered", Color.GREEN);
					mainView.addListToOrderTable(OrderBLL.getOrders());
					mainView.addListToProductTable(ProductBLL.getProducts());
					mainView.addListToClientTable(ClientBLL.getClients());
				} catch (ValidationException e1) {
					mainView.displayMessageLabel(e1.getMessage(), Color.RED);
				}
			}
		});

	}

}
