package application;



import javafx.beans.property.SimpleStringProperty;

public class TimeTable {
	private SimpleStringProperty day;
	private  SimpleStringProperty start;
	private   SimpleStringProperty end;
	private  SimpleStringProperty medicalUnit;
	
public TimeTable(String day, String start, String end,String medicalUnit) {
	
		super();
		this.day =new SimpleStringProperty(day);
		this.start =new SimpleStringProperty( start);
		this.end =new SimpleStringProperty( end);
		this.medicalUnit=new SimpleStringProperty( medicalUnit);
	}	
	
	public String getDay() {
	
		return day.get();
	}
	public void setDay(String day) {
		this.day.set(day);;
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
	
	public String getMedicalUnit() {
		return medicalUnit.get();
	}
	public void setMedicalUnit(String medicalUnit) {
		this.medicalUnit.set(medicalUnit);;
	}

}
