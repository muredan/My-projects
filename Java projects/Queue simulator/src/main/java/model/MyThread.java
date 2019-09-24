package model;


import javafx.application.Platform;
import view.MainView;

public class MyThread {

	Thread thread;
	Integer firstCustomerServiceTime;

	public void start(QueueSimulator queueSimulator, MainView mainView) {

		thread = new Thread(() -> {
			try {
				while (true) {
					if (queueSimulator.peek() != null) {
						
						Integer serviceTime=queueSimulator.peek().getServiceTime();
						firstCustomerServiceTime=0;
						for(int i=1;i<=serviceTime;i++) {
							Thread.sleep(1000);
							firstCustomerServiceTime++;
						}

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								if (queueSimulator.peek() != null) {
									mainView.setEventTexArea("client " + queueSimulator.peek().getId()
											+ ": a plecat de la casa " + (queueSimulator.getId() + 1) + "\n");
									queueSimulator.remove();
									mainView.removePersonIcon(queueSimulator.getId());
								}

							}
						});
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		});
		thread.start();
	}

	public Integer getFirstCustomerServiceTime() {
		return firstCustomerServiceTime;
	}

	public void stop() {
		thread.interrupt();

	}

}
