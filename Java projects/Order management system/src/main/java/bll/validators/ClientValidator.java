package bll.validators;

import java.util.List;

import model.Client;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientValidator.
 */
public class ClientValidator {
	
	/**
	 * Validate client.
	 *
	 * @param list the list
	 * @return the client
	 * @throws ValidationException the validation exception
	 */
	public static Client validateClient(List<String> list) throws ValidationException {
		
		for(String element:list) {
			if(element.length()==0) {
				throw new ValidationException("empty spaces");
			}
		}
		if(!list.get(3).matches("[0-9]+")) {
			throw new ValidationException("phone error format");
		}
		
		return new Client(0, list.get(0), list.get(1), list.get(2), list.get(3));
	}

}
