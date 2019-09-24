package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {
	
	/** The id. */
	private Integer id;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The address. */
	private String address;
	
	/** The phone. */
	private String phone;
	
	/**
	 * Instantiates a new client.
	 */
	public Client() {
		
	}
	
	/**
	 * Instantiates a new client.
	 *
	 * @param id the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param address the address
	 * @param phone the phone
	 */
	public Client(Integer id, String firstName, String lastName, String address, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	/**
	 * Prints the.
	 */
	public void print() {
		System.out.println(this.id+" "+this.firstName+" "+this.lastName+" "+this.address+" "+this.phone);
	}
	
	/**
	 * Sets the string flield.
	 *
	 * @param nameField the name field
	 * @param str the str
	 */
	public void setStringFlield(String nameField,String str) {
		
		if(nameField.equals("firstName")) {
			this.firstName=str;
		}else if(nameField.equals("lastName")) {
			this.lastName=str;
		}else if(nameField.equals("address")) {
			this.address=str;
		}else if(nameField.equals("phone")) {
			this.phone=str;
		}
		
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
}
