package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Customer;
import model.InfoQueues;
import model.QueueSimulator;
import view.MainView;

public class MainController {

	MainView mainView;
	private Integer peakTime;
	private Integer time;
	private Integer maxNumberOfCustomers;
	private Integer simulationTime;
	private Integer minArrivalTime;
	private Integer maxArrivalTime;
	private Integer minServiceTime;
	private Integer maxServiceTime;
	private Integer numberOfQueues;
	private InfoQueues infoQueues;

	public void start(Stage stage) {
		mainView = new MainView(stage);
		mainView.show();
		infoQueues=new InfoQueues();
		initialzeButtonsActionEvent();
	}

	public void initialzeButtonsActionEvent() {

		mainView.addStartButtonActionEvent(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				resetTextFields();
				if (initializeParameters()) {
					List<QueueSimulator> queuesList = createQueues(numberOfQueues);
					mainView.startButtonDisable(true);
					Thread thread = new Thread(() -> {
						time = 1;
						peakTime = 0;
						maxNumberOfCustomers = 0;
						Integer index = 1;
						Integer emptyTime = 0;
						while (true) {
							Integer arrivalTime = new Random().nextInt(maxArrivalTime - minArrivalTime + 1)
									+ minArrivalTime;
							Integer serviceTime = new Random().nextInt(maxServiceTime - minServiceTime + 1)
									+ minServiceTime;
							time += arrivalTime;
							if (time > simulationTime) {
								break;
							}
							Customer customer = new Customer(arrivalTime, serviceTime, index);
							index++;
							try {
								for (int i = 1; i <= arrivalTime; i++) {
									Thread.sleep(1000);
									if (infoQueues.checkEmptyQueues(queuesList)) {
										emptyTime++;
									}
								}
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								e.printStackTrace();
							}
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									Integer indexQueue = infoQueues.theBestQueue(queuesList);
									customer.setWaitingTime(queuesList.get(indexQueue).getQueueWaitingTime());
									queuesList.get(indexQueue).addWaitingTime(customer.getWaitingTime());
									queuesList.get(indexQueue).addServiceTime(customer.getServiceTime());
									queuesList.get(indexQueue).add(customer);
									mainView.addPersonIcon(indexQueue);
									Integer numberOfCutomers = infoQueues.currentNumberOfCutomers(queuesList);
									if (numberOfCutomers > maxNumberOfCustomers) {
										peakTime = time;
										maxNumberOfCustomers = numberOfCutomers;
									}
									mainView.setEventTexArea("client " + customer.getId() + ": a ajuns la casa "
											+ (indexQueue + 1) + " timp de asteptare: " + customer.getWaitingTime()
											+ " (Timpul sosirii= " + customer.getArrivalTime()
											+ "  Timpul serviciului=  " + customer.getServiceTime() + ")\n");
								}
							});
						}

						Thread.currentThread().interrupt();
						interrupAllThreads(queuesList);
						setOutputs(queuesList, index, emptyTime);
					});
					thread.start();
				}
			}
		});

	}
	public void interrupAllThreads(List<QueueSimulator> queuesList) {
		for (QueueSimulator element : queuesList) {
			element.interruptThreadForRemove();
		}
	}
	private void setOutputs(List<QueueSimulator> queuesList,Integer index,Integer emptyTime) {
		mainView.startButtonDisable(false);
		mainView.setAvgWaitingTimeText(String.format("%.2f", infoQueues.avgWaitingTime(queuesList, index - 1)));
		mainView.setAvgServiceTimeText(String.format("%.2f", infoQueues.avgServiceTime(queuesList, index - 1)));
		mainView.setEmptyQueueTimeText(
				String.format("%.2f", (double) emptyTime / (double) numberOfQueues));
		mainView.setPeakTimeTimeText(peakTime.toString());
		mainView.displayMessageLabel("SUCCES!!", Color.GREEN);
	}

	private List<QueueSimulator> createQueues(Integer numberOfQueues) {
		List<QueueSimulator> queuesList = new ArrayList<QueueSimulator>();
		for (int i = 0; i < numberOfQueues; i++) {
			queuesList.add(new QueueSimulator(i, mainView));
		}
		return queuesList;
	}

	private void resetTextFields() {
		mainView.setAvgServiceTimeText("");
		mainView.setAvgWaitingTimeText("");
		mainView.setEmptyQueueTimeText("");
		mainView.setPeakTimeTimeText("");
		mainView.clearEventsTextArea();
	}

	private boolean initializeParameters() {
		if (mainView.getNumberOfQueuesText().length() == 0 || mainView.getSimulationTimeText().length() == 0
				|| mainView.getMinArrivalTimeText().length() == 0 || mainView.getMaxArrivalTimeText().length() == 0
				|| mainView.getMinServiceTimeText().length() == 0 || mainView.getMaxServiceTimeText().length() == 0
				|| mainView.getNumberOfQueuesText().length() == 0) {
			mainView.displayMessageLabel("Format!!", Color.RED);
			return false;
		}
		if (!mainView.getNumberOfQueuesText().matches("[1-9]([0-9]+)?")
				|| !mainView.getSimulationTimeText().matches("[1-9]([0-9]+)?")
				|| !mainView.getMinArrivalTimeText().matches("[1-9]([0-9]+)?")
				|| !mainView.getMaxArrivalTimeText().matches("[1-9]([0-9]+)?")
				|| !mainView.getMinServiceTimeText().matches("[1-9]([0-9]+)?")
				|| !mainView.getMaxServiceTimeText().matches("[1-9]([0-9]+)?")
				|| !mainView.getNumberOfQueuesText().matches("[1-9]([0-9]+)?")) {
			mainView.displayMessageLabel("Format!!", Color.RED);
			return false;
		}

		simulationTime = Integer.valueOf(mainView.getSimulationTimeText());
		minArrivalTime = Integer.valueOf(mainView.getMinArrivalTimeText());
		maxArrivalTime = Integer.valueOf(mainView.getMaxArrivalTimeText());
		minServiceTime = Integer.valueOf(mainView.getMinServiceTimeText());
		maxServiceTime = Integer.valueOf(mainView.getMaxServiceTimeText());
		numberOfQueues = Integer.valueOf(mainView.getNumberOfQueuesText());

		if (minArrivalTime > maxArrivalTime || minServiceTime > maxServiceTime) {
			mainView.displayMessageLabel("Interval!!", Color.RED);
			return false;
		}
		mainView.createQueues(numberOfQueues);
		return true;
	}
}
