package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Orders.
 */
public class Orders {

	/** The id. */
	private Integer id;
	
	/** The id client. */
	private Integer idClient;
	
	/** The first name client. */
	private String firstNameClient;
	
	/** The last name client. */
	private String lastNameClient;
	
	/** The id product. */
	private Integer idProduct;
	
	/** The product name. */
	private String productName;
	
	/** The producer. */
	private String producer;
	
	/** The quantity. */
	private Integer quantity;
	
	/** The total. */
	private Double total;

	/**
	 * Instantiates a new orders.
	 */
	public Orders() {

	}

	/**
	 * Instantiates a new orders.
	 *
	 * @param id the id
	 * @param idClient the id client
	 * @param firstNameClient the first name client
	 * @param lastNameClient the last name client
	 * @param idProduct the id product
	 * @param productName the product name
	 * @param producer the producer
	 * @param quantity the quantity
	 * @param total the total
	 */
	public Orders(Integer id, Integer idClient, String firstNameClient, String lastNameClient, Integer idProduct,
			String productName, String producer, Integer quantity, Double total) {
		super();
		this.id = id;
		this.idClient = idClient;
		this.firstNameClient = firstNameClient;
		this.lastNameClient = lastNameClient;
		this.idProduct = idProduct;
		this.productName = productName;
		this.producer = producer;
		this.quantity = quantity;
		this.total = total;
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
	 * Gets the id client.
	 *
	 * @return the id client
	 */
	public Integer getIdClient() {
		return idClient;
	}

	/**
	 * Sets the id client.
	 *
	 * @param idClient the new id client
	 */
	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	/**
	 * Gets the first name client.
	 *
	 * @return the first name client
	 */
	public String getFirstNameClient() {
		return firstNameClient;
	}

	/**
	 * Sets the first name client.
	 *
	 * @param firstNameClient the new first name client
	 */
	public void setFirstNameClient(String firstNameClient) {
		this.firstNameClient = firstNameClient;
	}

	/**
	 * Gets the last name client.
	 *
	 * @return the last name client
	 */
	public String getLastNameClient() {
		return lastNameClient;
	}

	/**
	 * Sets the last name client.
	 *
	 * @param lastNameClient the new last name client
	 */
	public void setLastNameClient(String lastNameClient) {
		this.lastNameClient = lastNameClient;
	}

	/**
	 * Gets the id product.
	 *
	 * @return the id product
	 */
	public Integer getIdProduct() {
		return idProduct;
	}

	/**
	 * Sets the id product.
	 *
	 * @param idProduct the new id product
	 */
	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Gets the producer.
	 *
	 * @return the producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * Sets the producer.
	 *
	 * @param producer the new producer
	 */
	public void setProducer(String producer) {
		this.producer = producer;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

}
