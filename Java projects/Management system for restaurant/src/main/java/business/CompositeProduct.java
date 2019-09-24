package business;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct implements MenuItem,Serializable{

	private String name;
	private List<MenuItem> list=new ArrayList<MenuItem>(); 
	
	public List<MenuItem> getList() {
		return list;
	}

	public CompositeProduct(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Double computePrice() {
		Double total=0.0;
		for(MenuItem element:list) {
			total+=element.computePrice();
		}
		return total;
	}
	
	
	public void setList(List<MenuItem> list) {
		this.list = list;
	}

	public void addMenuItem (MenuItem menuItem) {
		list.add(menuItem);
	}
	public void removeMenuItem(MenuItem menuItem) {
		list.remove(menuItem);
	}

	@Override
	public Integer computeQuantity() {
		Integer total=0;
		for(MenuItem element:list) {
			total+=element.computeQuantity();
		}
		return total;
	}


}
