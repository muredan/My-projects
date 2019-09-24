package ro.tuc.pt.assig1;

import exception.DivisionException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.PolinomyalOperations;
import model.Polynomial;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws DivisionException
	 */
	public void testApp() {
		Polynomial polynomial1 = new Polynomial();
		Polynomial polynomial2 = new Polynomial();

		assertTrue(polynomial1.polynomialConverter("x^2+1"));
		assertFalse(polynomial2.polynomialConverter("x^2-x2+1"));
		assertTrue(polynomial2.polynomialConverter("x+1+2x^3"));
		assertTrue(polynomial2.getPolynomialString().equals("2x^3+x+1"));

		assertEquals(PolinomyalOperations.addition(polynomial1, polynomial2).getPolynomialString(), "2x^3+x^2+x+2");

		assertEquals(PolinomyalOperations.subtraction(polynomial1, polynomial2).getPolynomialString(), "-2x^3+x^2-x");

		assertEquals(PolinomyalOperations.multiplication(polynomial1, polynomial2).getPolynomialString(),
				"2x^5+3x^3+x^2+x+1");

		try {
			assertEquals(PolinomyalOperations.division(polynomial1, polynomial2).get(0).getPolynomialString(), "");
			assertEquals(PolinomyalOperations.division(polynomial1, polynomial2).get(1).getPolynomialString(), "x^2+1");
			polynomial2.polynomialConverter("0");
			PolinomyalOperations.division(polynomial1, polynomial2);
		} catch (DivisionException e) {
			assertEquals(e.getMessage(), "Împărțire cu 0");
		}

		assertEquals(PolinomyalOperations.multiplication(polynomial1, polynomial2).getPolynomialString(), "0");

		polynomial1.polynomialConverter("2x^2+1");

		assertEquals(PolinomyalOperations.derivation(polynomial1).getPolynomialString(), "4x");
		assertEquals(PolinomyalOperations.integration(polynomial1).getPolynomialString(), "0.67x^3+x");
		assertEquals(PolinomyalOperations.addition(polynomial1, polynomial2).getPolynomialString(), "2x^2+1");
		assertEquals(PolinomyalOperations.multiplication(polynomial1, polynomial2).getPolynomialString(), "0");

	}
}
