package model;

import java.util.List;

public class InfoQueues {



	public Integer theBestQueue(List<QueueSimulator> queuesList) {
		Integer theBestServiceTime = queuesList.get(0).getQueueWaitingTime();
		Integer queueIndex = 0;
		Integer index = 0;
		for (QueueSimulator element : queuesList) {
			if (element.getQueueWaitingTime() < theBestServiceTime) {
				theBestServiceTime = element.getQueueWaitingTime();
				queueIndex = index;
			}
			index++;
		}
		return queueIndex;
	}

	public Double avgWaitingTime(List<QueueSimulator> queuesList, Integer numberOfCustomers) {
		Double total = 0.0;
		for (QueueSimulator element : queuesList) {
			total += Double.valueOf(element.getTotalWaitingTime());
		}
		return total / (double) numberOfCustomers;
	}

	public Double avgServiceTime(List<QueueSimulator> queuesList, Integer numberOfCustomers) {
		Double total = 0.0;
		for (QueueSimulator element : queuesList) {
			total += Double.valueOf(element.getTotalServiceTime());
		}
		return total / (double) numberOfCustomers;
	}

	public boolean checkEmptyQueues(List<QueueSimulator> queuesList) {

		for (QueueSimulator element : queuesList) {
			if (element.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public Integer currentNumberOfCutomers(List<QueueSimulator> queuesList) {
		Integer total = 0;
		for (QueueSimulator element : queuesList) {
			total += element.size();
		}
		return total;
	}

}
