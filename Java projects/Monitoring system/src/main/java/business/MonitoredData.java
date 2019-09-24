package business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MonitoredData {
	private String startTime;
	private String endTime;
	private String activityName;
	
	public MonitoredData(String startTime, String endTime, String activityName) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityName = activityName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDate() {
		String [] part=startTime.split(" ");
		return part[0].toString();
	}
	public Long getDuration() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(this.getStartTime(), formatter);
		LocalDateTime end = LocalDateTime.parse(this.getEndTime(), formatter);
		Long minutes = ChronoUnit.MINUTES.between(start, end);
		return minutes;
	}
	
	
	
	

}
