package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import business.MenuItem;
import business.Restaurant;

public class RestaurantSerializator {

	public static void serializator(Restaurant restaurant) {
		try {
			FileOutputStream fileOut=new FileOutputStream("restaurant.ser");
			ObjectOutputStream out=new ObjectOutputStream(fileOut);
			out.writeObject(restaurant);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static Restaurant deserializator() {
		Restaurant restaurant=new Restaurant();
		
		try {
			FileInputStream fileIn=new FileInputStream("restaurant.ser");
			ObjectInputStream in=new ObjectInputStream(fileIn);
			restaurant=(Restaurant) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(restaurant==null) {
			return new Restaurant();
		}
		
		return restaurant;
	}
}
