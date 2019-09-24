package business;

import java.util.List;


public interface RestaurantProcessing {

	public void createNewMenuItem(String name,String quantity,String price);
	public void createNewMenuItem(String name,List<MenuItem> list);
	public void deleteMenuItem(MenuItem menuItem);
	public void deleteMenuItem(MenuItem menuItem,MenuItem parent);
	public void editMenuItem(String name,String quantity,String price,MenuItem menuItem);
	public void editMenuItem(String name,MenuItem menuItem);
	public void createNewOrder(String table,List<MenuItem>list);
	public Double computePriceForOrder(List<MenuItem> list);
	public void generateBill(Order order);
	public List<MenuItem> getMenuItemsFromOrder(Order order);
}
