package business;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import data.FileWriter;

public class Restaurant extends Observable implements RestaurantProcessing, Serializable {

	private List<MenuItem> menuList = new ArrayList<MenuItem>();
	private Map<Order, List<MenuItem>> ordersMap = new HashMap<Order, List<MenuItem>>();
	private Integer id = 1;

	public List<MenuItem> getMenuList() {
		return menuList;
	}

	@Override
	public void createNewMenuItem(String name, String quantity, String price) {
		assert name.length() != 0 && quantity.length() != 0 && price.length() != 0 : "Empty space";
		assert quantity.matches("[1-9]([0-9]+)?") : "Quantity format";
		assert price.matches("[1-9]([0-9]+)?(.[0-9]+)?") : "Price format";

		menuList.add(new BaseProduct(name, Integer.valueOf(quantity), Double.valueOf(price)));

		setChanged();
		notifyObservers();

		assert false : "Successfully added";

	}

	@Override
	public void createNewMenuItem(String name, List<MenuItem> list) {
		assert name.length() != 0 : "Empty space";
		assert !list.isEmpty() : "Empty list";
		assert list.size() > 1 : "Min 2 items";

		CompositeProduct compositeProduct = new CompositeProduct(name);
		compositeProduct.setList(list);

		menuList.add(compositeProduct);
		setChanged();
		notifyObservers();
		assert false : "Successfully added";

	}

	@Override
	public void deleteMenuItem(MenuItem menuItem) {

		menuList.remove(menuItem);
		setChanged();
		notifyObservers();
		assert false : "Successfully deleted";

	}

	@Override
	public void deleteMenuItem(MenuItem menuItem, MenuItem parent) {
		((CompositeProduct) parent).removeMenuItem(menuItem);
		
		if (((CompositeProduct) parent).getList().size() <= 1) {
			menuList.remove(parent);
			for (MenuItem element : menuList) {
					if (element instanceof CompositeProduct) {
						deleteProductFromAllComposite(parent, element);
					}
			}
			
		}
		setChanged();
		notifyObservers();
		assert false : "Successfully deleted";
	}

	private void deleteProductFromAllComposite(MenuItem menuItem, MenuItem parent) {
		
		CompositeProduct compositeProduct = (CompositeProduct) parent;
		for(Iterator<MenuItem> iterator=compositeProduct.getList().iterator();iterator.hasNext();) {
			MenuItem menuItem2=iterator.next();
			if (menuItem2.equals(menuItem)) {
				iterator.remove();
			} else if (menuItem2 instanceof CompositeProduct) {
				deleteProductFromAllComposite(menuItem, menuItem2);
			}
		}
	}

	@Override
	public void editMenuItem(String name, String quantity, String price, MenuItem menuItem) {
		assert name.length() != 0 && quantity.length() != 0 && price.length() != 0 : "Empty space";
		assert quantity.matches("[1-9]([0-9]+)?") : "Quantity format";
		assert price.matches("[1-9]([0-9]+)?(.[0-9]+)?") : "Price format";

		((BaseProduct) menuItem).setName(name);
		((BaseProduct) menuItem).setPrice(Double.valueOf(price));
		((BaseProduct) menuItem).setQuantity(Integer.valueOf(quantity));

		setChanged();
		notifyObservers();
		assert false : "Edited successfully";
	}

	@Override
	public void editMenuItem(String name, MenuItem menuItem) {
		assert name.length() != 0 : "Empty space";

		((CompositeProduct) menuItem).setName(name);

		setChanged();
		notifyObservers();
		assert false : "Edited successfully";
	}

	@Override
	public void createNewOrder(String table, List<MenuItem> list) {
		assert table.length() != 0 : "Empty space";
		assert !list.isEmpty() : "Empty list";
		assert table.matches("[1-9]([0-9]+)?") : "Tabley format";

		Format dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Order order = new Order(id, dateFormat.format(new Date()), Integer.valueOf(table));
		ordersMap.put(order, list);
		id++;
		
		
		assert !ordersMap.isEmpty() : "map gol";
		setChanged();
		notifyObservers();
		assert false : "Successfully added";
	

	}

	public Map<Order, List<MenuItem>> getOrdersMap() {
		return ordersMap;
	}

	@Override
	public Double computePriceForOrder(List<MenuItem> list) {
		Double total=0.0;
		for(MenuItem menuItem:list) {
			total+=menuItem.computePrice();
		}
		return total;
	}

	@Override
	public void generateBill(Order order) {
		
		
		FileWriter writer=new FileWriter("bill.txt");
		writer.writeToFile(order,ordersMap.get(order),computePriceForOrder(ordersMap.get(order)));
		ordersMap.remove(order);
		setChanged();
		notifyObservers();

	}

	@Override
	public List<MenuItem> getMenuItemsFromOrder(Order order) {
		return ordersMap.get(order);
	}

}
