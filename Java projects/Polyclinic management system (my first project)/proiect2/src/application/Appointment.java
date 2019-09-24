package application;

import javafx.scene.control.CheckBox;

public class Appointment {
	private String doctorLastName;
	private String doctorFirstName;
	private String patientLastName;
	private String patientFirstName;
	private String service;
	private String medicalUnit;
	private String date;
	private String start;
	private String CNP;
	private String state;
	private String time;

	public Appointment(String doctorLastName, String doctorFirstName, String patientLastName, String patientFirstName,
			String service, String medicalUnit, String date, String start, String cNP, String state, String time) {
		super();
		this.doctorLastName = doctorLastName;
		this.doctorFirstName = doctorFirstName;
		this.patientLastName = patientLastName;
		this.patientFirstName = patientFirstName;
		this.service = service;
		this.medicalUnit = medicalUnit;
		this.date = date;
		this.start = start;
		CNP = cNP;
		this.state = state;
		this.time = time;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMedicalUnit() {
		return medicalUnit;
	}

	public void setMedicalUnit(String medicalUnit) {
		this.medicalUnit = medicalUnit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getCNP() {
		return CNP;
	}

	public void setCNP(String cNP) {
		CNP = cNP;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
