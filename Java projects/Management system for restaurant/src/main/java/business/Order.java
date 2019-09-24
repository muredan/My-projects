package business;

import java.io.Serializable;

public class Order implements Serializable{
	private Integer orderId;
	private String date;
	private Integer table;

	public Order(Integer orderId, String date, Integer table) {
		super();
		this.orderId = orderId;
		this.date = date;
		this.table = table;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTable() {
		return table;
	}

	public void setTable(Integer table) {
		this.table = table;
	}

	@Override
	public boolean equals(Object object) {

		if (object == this)
			return true;
		if (!(object instanceof Order)) {
			return false;
		}

		Order order = (Order) object;

		return order.orderId == this.orderId && order.date.equals(this.date) && order.table == this.table;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + orderId;
		result = 31 * result + date.hashCode();
		result = 31 * result + table;
		return result;
	}

}
