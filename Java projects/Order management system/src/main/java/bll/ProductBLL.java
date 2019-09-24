package bll;

import dao.ProductDAO;
import javafx.collections.ObservableList;
import model.Orders;
import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductBLL.
 */
public class ProductBLL {
		
	/** The product DAO. */
	private static ProductDAO productDAO = new ProductDAO();
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public static ObservableList<Product> getProducts(){
		return productDAO.findByFilters("", "", "");
	}
	
	/**
	 * Adds the product.
	 *
	 * @param product the product
	 */
	public static void addProduct(Product product) {
		productDAO.inserObject(product);
	}
	
	/**
	 * Removes the product.
	 *
	 * @param product the product
	 */
	public static void removeProduct(Product product) {
		for(Orders order:OrderBLL.getOrders()) {
			if(order.getIdProduct()==product.getId()) {
				OrderBLL.removeOrder(order);
			}
		}
		productDAO.deleteObject(product.getId());
	}
	
	/**
	 * Update products.
	 *
	 * @param list the list
	 */
	public static void updateProducts(ObservableList<Product> list) {
		productDAO.updateTable(list);
	}
	
	/**
	 * Filter products.
	 *
	 * @param filter1 the filter 1
	 * @param filter2 the filter 2
	 * @param filter3 the filter 3
	 * @return the observable list
	 */
	public static ObservableList<Product> filterProducts(String filter1,String filter2,String filter3){
		return productDAO.findByFilters(filter1, filter2, filter3);	
	}
	
	/**
	 * Update prouct.
	 *
	 * @param product the product
	 */
	public static void updateProuct(Product product) {
		productDAO.updateObject(product);
	}
}
