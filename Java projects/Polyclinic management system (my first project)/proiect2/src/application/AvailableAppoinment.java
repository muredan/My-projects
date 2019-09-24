package application;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class AvailableAppoinment {

	private String lastName;
	private String firstName;
	private String medicalUnit;
	private ObservableList<String> startList;
	private String medicCNP;

	public AvailableAppoinment(String lastName, String firstName, String medicalUnit, ObservableList<String> startList,
			String medicCNP) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.medicalUnit = medicalUnit;
		this.startList = startList;
		this.medicCNP = medicCNP;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMedicalUnit() {
		return medicalUnit;
	}

	public void setMedicalUnit(String medicalUnit) {
		this.medicalUnit = medicalUnit;
	}

	public ObservableList<String> getStartList() {
		return startList;
	}

	public void setStartList(ObservableList<String> startList) {
		this.startList = startList;
	}

	public String getMedicCNP() {
		return medicCNP;
	}

	public void setMedicCNP(String medicCNP) {
		this.medicCNP = medicCNP;
	}
	

	

	
	
}
