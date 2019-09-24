package business;

import java.io.Serializable;

public class BaseProduct  implements MenuItem,Serializable {

	private String name;
	private Integer quantity;
	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BaseProduct(String name, Integer quantity, Double price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public Double computePrice() {
			return price;
	}

	@Override
	public Integer computeQuantity() {
		return quantity;
	}
	


}
