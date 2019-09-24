package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Product.
 */
public class Product {

	/** The id. */
	private Integer id;
	
	/** The name. */
	private String name;
	
	/** The producer. */
	private String producer;
	
	/** The stock. */
	private String stock;
	
	/** The price. */
	private String price;

	/**
	 * Instantiates a new product.
	 */
	public Product() {

	}

	/**
	 * Instantiates a new product.
	 *
	 * @param id the id
	 * @param name the name
	 * @param producer the producer
	 * @param stock the stock
	 * @param price the price
	 */
	public Product(Integer id, String name, String producer, String stock, String price) {
		super();
		this.id = id;
		this.name = name;
		this.producer = producer;
		this.stock = stock;
		this.price = price;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets the stock.
	 *
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}

	/**
	 * Sets the stock.
	 *
	 * @param stock the new stock
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * Sets the string flield.
	 *
	 * @param nameField the name field
	 * @param str the str
	 */
	public void setStringFlield(String nameField, String str) {
			
		if(nameField.equals("name")) {
			this.name=str;
		}else if(nameField.equals("producer"))  {
			this.producer=str;
		}else if(nameField.equals("stock")) {
			this.stock=str;
		}else if(nameField.equals("price")) {
			this.price=str;
		}
	}

}
