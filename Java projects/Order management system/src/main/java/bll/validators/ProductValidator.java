package bll.validators;

import java.util.List;

import model.Product;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductValidator.
 */
public class ProductValidator {
	
	/**
	 * Validate product.
	 *
	 * @param list the list
	 * @return the product
	 * @throws ValidationException the validation exception
	 */
	public static Product validateProduct(List<String> list) throws ValidationException {
		for(String element:list) {
			if(element.length()==0) {
				throw new ValidationException("empty spaces");
			}
		}
		if(!list.get(2).matches("[0-9]+")) {
			throw new ValidationException("stock format error");
		}
		if(!list.get(3).matches("[1-9]([0-9]+)?(.[0-9]+)?")) {
			throw new ValidationException("price format error");
		}
		return new Product(0, list.get(0), list.get(1), list.get(2), list.get(3));
	}

}
