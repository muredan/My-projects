package model;

import java.util.concurrent.ConcurrentLinkedQueue;

import view.MainView;


@SuppressWarnings("serial")
public class QueueSimulator extends ConcurrentLinkedQueue<Customer> {

	
	private Integer id;
	private MyThread threadForRemove=new MyThread();
	private Integer totalServiceTime=0;
	private Integer totalWaitingTime=0;

	public QueueSimulator(Integer id,MainView mainView) {
		super();
		this.id = id;
		this.threadForRemove.start(this,mainView);
	}

	public Integer getTotalServiceTime() {
		return totalServiceTime;
	}
	
	public void addServiceTime(Integer serviceTime) {
		totalServiceTime+=serviceTime;
	}
	
	public void addWaitingTime(Integer waitingTime) {
		totalWaitingTime+=waitingTime;
	}
	
	public void interruptThreadForRemove() {
		threadForRemove.stop();
	}


	public Integer getTotalWaitingTime() {
		return totalWaitingTime;
	}


	public Integer getId() {
		return id;
	}

	public Integer getFirstCustomerServiceTime() {
		return threadForRemove.getFirstCustomerServiceTime();
		
	}
	
	public Integer getQueueWaitingTime() {
		
		Integer firstCustomerServiceTime=0;
		Integer waitingTime=0;
		if(this.peek() != null) {
			firstCustomerServiceTime=this.getFirstCustomerServiceTime();
		}
		for(Customer element:this) {
			waitingTime+=element.getServiceTime();
		}
		waitingTime-=firstCustomerServiceTime;
		return waitingTime;
	}

}
