package data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;

public class FileWriter {

	private String path;

	public FileWriter(String path) {
		super();
		this.path = path;
	}

	public void writeToFile(Order order, List<MenuItem> list, Double total) {
		try {
			java.io.FileWriter writer = new java.io.FileWriter(path, false);
			PrintWriter printWriter = new PrintWriter(writer);
			printWriter.printf("\tBILL\n");

			printWriter.printf("Name\t\tPrice\n");
			for (MenuItem menuItem : list) {
				if (menuItem instanceof BaseProduct)
					printWriter.printf("%s\t\t%.2f\n", ((BaseProduct) menuItem).getName(), menuItem.computePrice());
				else
					printWriter.printf("%s\t\t%.2f\n", ((CompositeProduct) menuItem).getName(), menuItem.computePrice());
			}
			printWriter.printf("\n");
			printWriter.printf("\t\tTOTAL\n");
			printWriter.printf("\t\t%.2f\n",total);
			printWriter.printf("OrderId: %d\n", order.getOrderId());
			printWriter.printf("Date: %s\n",order.getDate());
			printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
