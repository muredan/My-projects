package bll;

import dao.ClientDAO;
import javafx.collections.ObservableList;
import model.Client;
import model.Orders;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientBLL.
 */
public class ClientBLL {
	
	/** The client DAO. */
	private static ClientDAO clientDAO = new ClientDAO();
	
	/**
	 * Gets the clients.
	 *
	 * @return the clients
	 */
	public static ObservableList<Client> getClients(){
		return clientDAO.findByFilters("", "", "");
	}
	
	/**
	 * Adds the client.
	 *
	 * @param client the client
	 */
	public static void addClient(Client client) {
		clientDAO.inserObject(client);
	}

	/**
	 * Removes the client.
	 *
	 * @param client the client
	 */
	public static void removeClient(Client client) {
		for(Orders order:OrderBLL.getOrders()) {
			if(order.getIdClient()==client.getId()) {
				OrderBLL.removeOrder(order);
			}
		}
		clientDAO.deleteObject(client.getId());
	}
	
	/**
	 * Update clients.
	 *
	 * @param list the list
	 */
	public static void updateClients(ObservableList<Client> list) {
		clientDAO.updateTable(list);
	}
	
	/**
	 * Filter clients.
	 *
	 * @param filter1 the filter 1
	 * @param filter2 the filter 2
	 * @param filter3 the filter 3
	 * @return the observable list
	 */
	public static ObservableList<Client> filterClients(String filter1,String filter2,String filter3){
		return clientDAO.findByFilters(filter1, filter2, filter3);	
	}
}
