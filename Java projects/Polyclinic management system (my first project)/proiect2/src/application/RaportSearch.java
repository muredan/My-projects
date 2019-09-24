package application;

public class RaportSearch {

	private Integer idRaport;
	private String dateRaport;
	private String typeRaport;
	public RaportSearch(Integer idRaport, String dateRaport, String typeRaport) {
		super();
		this.idRaport = idRaport;
		this.dateRaport = dateRaport;
		this.typeRaport = typeRaport;
	}
	public Integer getIdRaport() {
		return idRaport;
	}
	public void setIdRaport(Integer idRaport) {
		this.idRaport = idRaport;
	}
	public String getDateRaport() {
		return dateRaport;
	}
	public void setDateRaport(String dateRaport) {
		this.dateRaport = dateRaport;
	}
	public String getTypeRaport() {
		return typeRaport;
	}
	public void setTypeRaport(String typeRaport) {
		this.typeRaport = typeRaport;
	}

}
