package business;

public class CountPerDay {
	
	private String activityName;
	private String date;
	private Long count;
	
	public CountPerDay(String activityName, String date, Long count) {
		super();
		this.activityName = activityName;
		this.date = date;
		this.count = count;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	

	
	
	


}
