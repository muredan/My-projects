package model;

import java.util.ArrayList;
import java.util.List;

import exception.DivisionException;

public class PolinomyalOperations {
	
	//implementarile operatiilor
	public static Polynomial addition(Polynomial polynomial1, Polynomial polynomial2) {
		Polynomial result = new Polynomial();
		for (Monomial monomial : polynomial1.getPolynomialMap().values()) {
			result.addMonomial(new Monomial(monomial.getConstant(), monomial.getExponent()));
		}
		for (Monomial monomial : polynomial2.getPolynomialMap().values()) {
			result.addMonomial(new Monomial(monomial.getConstant(), monomial.getExponent()));
		}
		return result;
	}

	public static Polynomial subtraction(Polynomial polynomial1, Polynomial polynomial2) {
		Polynomial result = new Polynomial();
		for (Monomial monomial : polynomial1.getPolynomialMap().values()) {
			result.addMonomial(new Monomial(monomial.getConstant(), monomial.getExponent()));
		}
		for (Monomial monomial : polynomial2.getPolynomialMap().values()) {
			result.addMonomial(new Monomial(-monomial.getConstant(), monomial.getExponent()));
		}
		return result;
	}

	public static Polynomial multiplication(Polynomial polynomial1, Polynomial polynomial2) {
		Polynomial result = new Polynomial();

		for (Monomial monomialpolynomial1 : polynomial1.getPolynomialMap().values()) {
			for (Monomial monomialpolynomial2 : polynomial2.getPolynomialMap().values()) {
				Double constant = monomialpolynomial1.getConstant() * monomialpolynomial2.getConstant();
				Integer exponent = monomialpolynomial1.getExponent() + monomialpolynomial2.getExponent();

				result.addMonomial(new Monomial(constant, exponent));
			}
		}
		return result;
	}

	public static List<Polynomial> division(Polynomial polynomial1, Polynomial polynomial2) throws DivisionException {
		Polynomial result = new Polynomial();
		Polynomial remainder=polynomial1;
		List<Polynomial> resultList = new ArrayList<Polynomial>();
		if (polynomial2.getFirstMonomial().getConstant() == 0) {
			throw new DivisionException("Împărțire cu 0");
		}
		if(remainder.getFirstMonomial().getConstant()==0) {
			result=remainder;
		}
		else {
			while (remainder.getFirstMonomial().getExponent() >= polynomial2.getFirstMonomial().getExponent()) {

				Polynomial polynomial = new Polynomial();
				Monomial monomial = new Monomial(
						remainder.getFirstMonomial().getConstant() / polynomial2.getFirstMonomial().getConstant(),
						remainder.getFirstMonomial().getExponent() - polynomial2.getFirstMonomial().getExponent());
				result.addMonomial(monomial);
				polynomial.addMonomial(monomial);
				polynomial = multiplication(polynomial2, polynomial);
				remainder = subtraction(remainder, polynomial);
				if (result.getPolynomialMap().size()==polynomial1.getPolynomialMap().size()) {
					break;
				}
			}
		}
		resultList.add(result);
		resultList.add(remainder);
		return resultList;
	}

	public static Polynomial derivation(Polynomial polynomial) {
		Polynomial result = new Polynomial();
		Monomial newMonomial;
		for (Monomial monomial : polynomial.getPolynomialMap().values()) {
			if (monomial.getExponent() == 0) {
				newMonomial=new Monomial((double) 0, monomial.getExponent());
			} else {
				newMonomial=new Monomial(monomial.getConstant() * (double) monomial.getExponent(), monomial.getExponent() - 1);
			}
			result.addMonomial(newMonomial);
		}
		return result;
	}

	public static Polynomial integration(Polynomial polynomial) {
		Polynomial result = new Polynomial();
		for (Monomial monomial : polynomial.getPolynomialMap().values()) {

			result.addMonomial(new Monomial(monomial.getConstant() / ((double) (monomial.getExponent() + 1)), monomial.getExponent() + 1));
		}
		return result;
	}

}
