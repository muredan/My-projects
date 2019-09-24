package bll;

import dao.OrderDAO;
import javafx.collections.ObservableList;
import model.Bill;
import model.Client;
import model.Orders;
import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderBLL.
 */
public class OrderBLL {

	/** The order DAO. */
	private static OrderDAO orderDAO=new OrderDAO();
	
	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public static ObservableList<Orders> getOrders(){
		return orderDAO.findByFilters("", "", "");
	}
	
	/**
	 * Adds the order.
	 *
	 * @param client the client
	 * @param product the product
	 * @param quantity the quantity
	 */
	public static void addOrder(Client client,Product product,String quantity) {
		Integer newStock=Integer.valueOf(product.getStock())-Integer.valueOf(quantity);
		product.setStock(newStock.toString());
		ProductBLL.updateProuct(product);
		Double total=Double.valueOf(product.getPrice())*Double.valueOf(quantity);
		Orders orders=new Orders(0, client.getId(), client.getFirstName(), client.getLastName(),product.getId(), product.getName(), product.getProducer(), Integer.valueOf(quantity), total);
		orderDAO.inserObject(orders);
		Bill.generate(orders, client);
	}
	
	/**
	 * Removes the order.
	 *
	 * @param order the order
	 */
	public static void removeOrder(Orders order) {
		orderDAO.deleteObject(order.getId());
	}
	
}
