package bll.validators;

import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderValidator.
 */
public class OrderValidator {
	
	/**
	 * Validate order.
	 *
	 * @param product the product
	 * @param quantity the quantity
	 * @throws ValidationException the validation exception
	 */
	public static void validateOrder(Product product,String quantity) throws ValidationException {
		
		if(quantity.length()==0) {
			throw new ValidationException("empty spaces");
		}
		
		if(!quantity.matches("[1-9]([0-9]+)?")) {
			throw new ValidationException("quantity format");
		}
		
		
		if(Integer.valueOf(product.getStock())<Integer.valueOf(quantity)) {
			throw new ValidationException("under stock");
		}
	}
	
}
