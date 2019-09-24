package application;

public class UnfinishedReport {

	private Integer idRaport;
	private String patientCNP;
	private String patientLastName;
	private String patientFirstName;
	public UnfinishedReport(Integer idRaport, String patientCNP, String patientLastName, String patientFirstName) {
		super();
		this.idRaport = idRaport;
		this.patientCNP = patientCNP;
		this.patientLastName = patientLastName;
		this.patientFirstName = patientFirstName;
	}
	public Integer getIdRaport() {
		return idRaport;
	}
	public void setIdRaport(Integer idRaport) {
		this.idRaport = idRaport;
	}
	public String getPatientCNP() {
		return patientCNP;
	}
	public void setPatientCNP(String patientCNP) {
		this.patientCNP = patientCNP;
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

	
}
