package model;

public class Customer {
	
	
	private Integer arrivalTime;
	private Integer serviceTime;
	private Integer waitingTime;
	private Integer id;
	
	
	
	public Customer(Integer arrivalTime, Integer serviceTime, Integer id) {
		super();
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.id = id;
	}
	public Integer getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Integer getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}
	public Integer getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	


	
	
	

	

}
