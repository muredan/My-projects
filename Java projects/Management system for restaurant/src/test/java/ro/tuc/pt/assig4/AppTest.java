package ro.tuc.pt.assig4;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import data.FileWriter;
import data.RestaurantSerializator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

	private void printMenu(MenuItem menuItem) {
		if (menuItem instanceof BaseProduct) {
			System.out.println(((BaseProduct) menuItem).getName() + " ");
		}
		if (menuItem instanceof CompositeProduct) {
			System.out.println(((CompositeProduct) menuItem).getName() + ": ");
			for (MenuItem element : ((CompositeProduct) menuItem).getList()) {
				printMenu(element);
			}
		}

	}
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	//Format dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	
    	//System.out.println(dateFormat.format(new Date()));
    	
    	/*
    	BaseProduct b1 = new BaseProduct("cartofi", 12, 10.0);
		BaseProduct b2 = new BaseProduct("cartofi2", 11, 12.0);
    	Restaurant restaurant=new Restaurant();
    	restaurant.createNewMenuItem(b1);
    	restaurant.createNewMenuItem(b2);
    	
		CompositeProduct compositeProduct = new CompositeProduct("meniu 1");
		compositeProduct.addMenuItem(b1);
		compositeProduct.addMenuItem(b2);
		CompositeProduct caca = new CompositeProduct("caca");
		caca.addMenuItem(b1);
		caca.addMenuItem(b2);
		compositeProduct.addMenuItem(caca);
		
		restaurant.createNewMenuItem(compositeProduct);
    	
    	RestaurantSerializator.serializator(restaurant);
    	
    	
    	
    	Restaurant restaurant2= RestaurantSerializator.deserializator();
    	
    	for(MenuItem element: restaurant2.getMenuList()) {
    		printMenu(element);
    	}
    	
    	System.out.println();*/
    	assertTrue( true );
    }
}
