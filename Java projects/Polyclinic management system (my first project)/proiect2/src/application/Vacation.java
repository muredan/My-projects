package application;

import javafx.beans.property.SimpleStringProperty;

public class Vacation {
	private SimpleStringProperty start;
	private SimpleStringProperty end;

	
	public Vacation(String start, String end) {
		super();
		this.start =new SimpleStringProperty( start);
		this.end =new SimpleStringProperty (end);
	}
	
	
	
	
	public String getStart() {
		return start.get();
	}

	public void setStart(String start) {
		this.start.set(start);;
	}
	public String getEnd() {
		return end.get();
	}
	public void setEnd(String end) {
		this.end.set(end);;
	}
	
}
