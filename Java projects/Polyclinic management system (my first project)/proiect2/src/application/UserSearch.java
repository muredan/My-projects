package application;

public class UserSearch {
	private String userCNP;
	private String userLastName;
	private String userFirstName;
	private String userFunction;

	public UserSearch(String userCNP, String userLastName, String userFirstName, String userFunction) {
		super();
		this.userCNP = userCNP;
		this.userLastName = userLastName;
		this.userFirstName = userFirstName;
		this.userFunction = userFunction;
	}

	public String getUserCNP() {
		return userCNP;
	}

	public void setUserCNP(String userCNP) {
		this.userCNP = userCNP;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserFunction() {
		return userFunction;
	}

	public void setUserFunction(String userFunction) {
		this.userFunction = userFunction;
	}

}
